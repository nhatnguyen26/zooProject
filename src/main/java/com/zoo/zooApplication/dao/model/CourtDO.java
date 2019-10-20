package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "courts")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
@SelectBeforeUpdate(false)
public class CourtDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // name is SQL keyword
    @Column(name = "court_name")
    private String name;

    @Column
    private String addressStreet;

    @Column
    private String addressWard;

    @Column
    private String addressDistrict;

    @Column
    private String addressCity;

    @Column
    private String addressCountry;

    @Column
    private String phoneNumber;

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @OneToMany(targetEntity = FieldDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "court", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @OrderBy("id")
    private final Set<FieldDO> fields = new LinkedHashSet<>();

    public CourtDO addField(FieldDO fieldDO) {
        getFields().add(fieldDO);
        fieldDO.setCourt(this);
        return this;
    }

    public Optional<FieldDO> findFieldById(Long id) {
		return getFields()
			.stream()
			.filter(fieldDO -> fieldDO.getId().equals(id))
			.findFirst();
    }

    @OneToMany(targetEntity = FieldTypeDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "court", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @OrderBy("id")
    private final Set<FieldTypeDO> fieldTypes = new LinkedHashSet<>();

    public CourtDO addFieldType(FieldTypeDO fieldTypeDO){
        getFieldTypes().add(fieldTypeDO);
        fieldTypeDO.setCourt(this);
        return this;
    }

    public Optional<FieldTypeDO> findFieldTypeById(Long id) {
        return getFieldTypes()
            .stream()
            .filter(fieldTypeDO -> fieldTypeDO.getId().equals(id))
            .findFirst();
    }
}
