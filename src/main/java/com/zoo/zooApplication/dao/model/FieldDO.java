package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import com.zoo.zooApplication.dao.util.IdSetToStringAttributeConverter;
import com.zoo.zooApplication.dao.util.MainFieldTypeEnumConverter;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "fields")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
@SelectBeforeUpdate(false)
public class FieldDO {

    // use sequence since Field has mainly bulk support so want to leverage batch
    @Id
    @SequenceGenerator(name = "fields_id_seq", sequenceName = "fields_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fields_id_seq")
    private Long id;

    // name is SQL keyword
    @Column(name = "field_name")
    private String name;

    @Column
    @Convert(converter = MainFieldTypeEnumConverter.class)
    private MainFieldTypeEnum mainFieldType;

    @Column
    private Long fieldTypeId;

    @Column
    @Convert(converter = IdSetToStringAttributeConverter.class)
    private Set<Long> subFieldIds = new LinkedHashSet<>();

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @ManyToOne(targetEntity = CourtDO.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", updatable = false)
    private CourtDO court;


}
