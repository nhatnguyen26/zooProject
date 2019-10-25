package com.zoo.zooApplication.converter;

import com.zoo.zooApplication.dao.model.FieldDO;
import com.zoo.zooApplication.response.Field;
import com.zoo.zooApplication.response.FieldResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FieldDOToResponseConverter {
    public Field convert(@NotNull final FieldDO fieldDO) {
        Objects.requireNonNull(fieldDO);

        Map<Long, Set<Long>> partOfFieldsMap = new HashMap<>();
        Map<Long, Set<Long>> coBlockingFieldsMap = new HashMap<>();
        if (fieldDO.getCourt() != null) {
            buildRelationMap(fieldDO.getCourt().getFields(), partOfFieldsMap, coBlockingFieldsMap);
        }

        return convertField(fieldDO, partOfFieldsMap, coBlockingFieldsMap);
    }

    public List<Field> convert(@NotNull final Collection<FieldDO> fieldDOCollection) {
        Objects.requireNonNull(fieldDOCollection);

        Map<Long, Set<Long>> partOfFieldsMap = new HashMap<>();
        Map<Long, Set<Long>> coBlockingFieldsMap = new HashMap<>();
        buildRelationMap(fieldDOCollection, partOfFieldsMap, coBlockingFieldsMap);

        return fieldDOCollection
            .stream()
            .map(fieldDO -> convertField(fieldDO, partOfFieldsMap, coBlockingFieldsMap))
            .collect(Collectors.toList());
    }

    public FieldResponse convertToResponse(@NotNull final List<FieldDO> fieldDOList) {
        Objects.requireNonNull(fieldDOList);

        return FieldResponse
            .builder()
            .fields(convert(fieldDOList))
            .build();
    }

    private Field convertField(FieldDO fieldDO, Map<Long, Set<Long>> partOfFieldsMap, Map<Long, Set<Long>> coBlockingFieldsMap) {
        return Field
            .builder()
            .id(fieldDO.getId())
            .name(fieldDO.getName())
            .mainFieldType(fieldDO.getMainFieldType())
            .fieldTypeId(fieldDO.getFieldTypeId())
            .subFieldIds(new ArrayList<>(fieldDO.getSubFieldIds()))
            .partOfFieldIds(partOfFieldsMap.containsKey(fieldDO.getId()) ? new ArrayList<>(partOfFieldsMap.get(fieldDO.getId())) : null)
            .coBlockingFieldIds(coBlockingFieldsMap.containsKey(fieldDO.getId()) ? new ArrayList<>(coBlockingFieldsMap.get(fieldDO.getId())) : null)
            .build();
    }

    private void buildRelationMap(Collection<FieldDO> fieldDOList, Map<Long, Set<Long>> partOfFieldsMap, Map<Long, Set<Long>> coBlockingFieldsMap) {
        Map<Long, Set<Long>> subFieldMap = buildSubFieldIdMap(fieldDOList);
        buildPartOfFieldIdMap(subFieldMap, partOfFieldsMap);
        buildCoBlockingMap(fieldDOList, subFieldMap, partOfFieldsMap, coBlockingFieldsMap);

    }

    private Map<Long, Set<Long>> buildSubFieldIdMap(Collection<FieldDO> fieldDOList) {
        Map<Long, Set<Long>> subFieldsMap = new HashMap<>();
        for (FieldDO fieldDO : fieldDOList) {
            if (CollectionUtils.isNotEmpty(fieldDO.getSubFieldIds())) {
                subFieldsMap.put(fieldDO.getId(), fieldDO.getSubFieldIds());
            }
        }
        return subFieldsMap;
    }

    private void buildPartOfFieldIdMap(Map<Long, Set<Long>> subFieldsMap, Map<Long, Set<Long>> partOfFieldsMap) {
        // populate map of id -> all field that id is part of (that field's subfields)
        for (Map.Entry<Long, Set<Long>> subFieldEntry : subFieldsMap.entrySet()) {
            Long fieldId = subFieldEntry.getKey();
            for (Long subFieldId : subFieldEntry.getValue()) {
                if (!partOfFieldsMap.containsKey(subFieldId)) {
                    partOfFieldsMap.put(subFieldId, new HashSet<>());
                }
                partOfFieldsMap.get(subFieldId).add(fieldId);
            }
        }
    }

    private void buildCoBlockingMap(Collection<FieldDO> fieldDOList, Map<Long, Set<Long>> subFieldsMap , Map<Long, Set<Long>> partOfFieldsMap, Map<Long, Set<Long>> coBlockingFieldsMap) {
        // build a grand parent map to speed up build block map
        Map<Long, Set<Long>> grandPartOfMap = findAllFieldReachable(partOfFieldsMap);

        Map<Long, Set<Long>> grandSubFieldMap = findAllFieldReachable(subFieldsMap);

        // populate coblocking map
        for (FieldDO fieldDO : fieldDOList) {
            Set<Long> coBlocking = findCoBlocking(fieldDO.getId(), grandSubFieldMap, grandPartOfMap);
            coBlockingFieldsMap.put(fieldDO.getId(), coBlocking);
        }
    }

    /**
     * Using the reachable map (either subField or partOf direction) find all the grand child in that direction (which is all grand sub or all grand part)
     * If there is ever a cycle situation every field will have itself as grand child and all member of that cycle will share exactly all grand children
     * For example: 1->2, 1->4, 2->3, 3->1. The cycle here is 1-2-3, beside 1 has direct edge to 4, all other part of cycle still can get to 4 through 1
     * Therefore grand children of all 1,2 and 3 will be the same as (1,2,3,4) with 4 won't have any child
     * This apply for any cycle that share edge with another cycle. In another word, all connected cycle will have all member as grand children
     * @param reachableMap
     * @return
     */
    private Map<Long, Set<Long>> findAllFieldReachable(Map<Long, Set<Long>> reachableMap) {
        Map<Long, Set<Long>> grandChildrenMap = new HashMap<>();
        for (Long fieldId : reachableMap.keySet()) {
            Set<Long> grandPartOf = dfs(fieldId, reachableMap, new HashSet<>(), grandChildrenMap);
            grandChildrenMap.put(fieldId, grandPartOf);
        }
        return grandChildrenMap;
    }
    private Set<Long> dfs(Long fieldId, Map<Long, Set<Long>> childrenMap, HashSet<Long> explored, Map<Long, Set<Long>> grandChildrenMap) {
        // if already figured out, return
        if (grandChildrenMap.containsKey(fieldId)) {
            return grandChildrenMap.get(fieldId);
        }

        Set<Long> grandPartOf = new HashSet<>();
        if (explored.contains(fieldId)) {
            grandPartOf.add(fieldId);
            return grandPartOf;
        }
        if (CollectionUtils.isNotEmpty(childrenMap.get(fieldId))) {
            explored.add(fieldId);
            for (Long partOf : childrenMap.get(fieldId)) {
                grandPartOf.add(partOf);
                grandPartOf.addAll(dfs(partOf, childrenMap, explored, grandChildrenMap));
            }
        }
        return grandPartOf;
    }

    private Set<Long> findCoBlocking(Long fieldId, Map<Long, Set<Long>> grandSubFieldMap, Map<Long, Set<Long>> grandPartOfMap) {
        Set<Long> coBlockingSet = new HashSet<>();

        if (grandSubFieldMap.containsKey(fieldId)) {
            for (Long subFieldId : grandSubFieldMap.get(fieldId)) {
                coBlockingSet.add(subFieldId);
                if (grandPartOfMap.containsKey(subFieldId)) {
                    coBlockingSet.addAll(grandPartOfMap.get(subFieldId));
                }
            }
        }

        if (grandPartOfMap.containsKey(fieldId)) {
            coBlockingSet.addAll(grandPartOfMap.get(fieldId));
        }

        // since this id will be part of grand part of it subfield, itself will be in the set
        // which is still correct as it will block itself, but remove or else all field will have itself as blocking
        coBlockingSet.remove(fieldId);
        return coBlockingSet;
    }
}
