package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courts")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
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

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = FieldDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "court")
    private final List<FieldDO> fields = new ArrayList<>();

    public CourtDO addField(FieldDO fieldDO) {
        fields.add(fieldDO);
        fieldDO.setCourt(this);
        return this;
    }

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(targetEntity = FieldTypeDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "court")
    private final List<FieldTypeDO> fieldTypes = new ArrayList<>();

    public CourtDO addFieldType(FieldTypeDO fieldTypeDO){
        fieldTypes.add(fieldTypeDO);
        fieldTypeDO.setCourt(this);
        return this;
    }
}
