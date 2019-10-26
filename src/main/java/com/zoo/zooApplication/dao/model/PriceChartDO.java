package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.DOTimestampConverter;
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

@Entity
@Table(name="price_charts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@SelectBeforeUpdate(false)
public class PriceChartDO {

    // use sequence since Field has mainly bulk support so want to leverage batch
    @Id
    @SequenceGenerator(name = "price_charts_id_seq", sequenceName = "price_charts_id_seq", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_charts_id_seq")
    private Long id;

    @Column
    private Double priceAmount;

    @Column
    private String currencyId;

    @Column(nullable = false)
    private Integer timeStart;

    @Column(nullable = false)
    private Integer timeEnd;

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    @Convert(converter = DOTimestampConverter.class)
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @ManyToOne(targetEntity = FieldTypeDO.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "field_type_id", updatable = false)
    private FieldTypeDO fieldType;

}
