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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "field_types")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
@SelectBeforeUpdate(false)
public class FieldTypeDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // name is SQL keyword
    @Column(name = "field_type_name")
    private String name;

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

    @OneToMany(targetEntity = PriceChartDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "fieldType", fetch = FetchType.LAZY)
    @BatchSize(size = 100)
    @OrderBy("id")
    private final List<PriceChartDO> priceCharts = new ArrayList<>();

    public FieldTypeDO addPriceChart(PriceChartDO priceChartDO){
        getPriceCharts().add(priceChartDO);
        priceChartDO.setFieldType(this);
        return this;
    }
}
