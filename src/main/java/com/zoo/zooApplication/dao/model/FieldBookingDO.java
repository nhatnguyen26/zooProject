package com.zoo.zooApplication.dao.model;

import com.zoo.zooApplication.dao.util.BookingStatusEnumConverter;
import com.zoo.zooApplication.dao.util.DOTimestampConverter;
import com.zoo.zooApplication.dao.util.MainFieldTypeEnumConverter;
import com.zoo.zooApplication.type.BookingStatusEnum;
import com.zoo.zooApplication.type.MainFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
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
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "field_bookings")
@Getter
@Setter
@Builder
@AllArgsConstructor // require for @Builder to work correctly
@NoArgsConstructor // required for hibernate mapping
@DynamicUpdate
@SelectBeforeUpdate(false)
public class FieldBookingDO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(updatable = false, unique = true)
	private String bookingNumber;

	@Column(updatable = false)
	private Long courtId;

	@Column(updatable = false)
    @Convert(converter = MainFieldTypeEnumConverter.class)
	private MainFieldTypeEnum mainFieldType;

	@Column
	private Long fieldTypeId;

	@Column
	private Long fieldId;

	@Column
	@Convert(converter = DOTimestampConverter.class)
	private ZonedDateTime timeIn;

	@Column
	@Convert(converter = DOTimestampConverter.class)
	private ZonedDateTime timeOut;

	@Convert(converter = DOTimestampConverter.class)
	private ZonedDateTime actualTimeIn;

	@Convert(converter = DOTimestampConverter.class)
	private ZonedDateTime actualTimeOut;

	@Column
	@Convert(converter = BookingStatusEnumConverter.class)
	private BookingStatusEnum status;

	@Column
	private String adminNote;

	@Column
	private Boolean regularBooker;

	@Column
	private Long bookerUserId;

	@Column
	private String bookerName;

	@Column
	private String bookerEmail;

	@Column
	private String bookerPhone;

	@Column(updatable = false)
	private Double priceAmount;

	@Column
	private Double actualChargedAmount;

	@Column
	private Double depositAmount;

	@Column
	private String currencyId;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@CreationTimestamp
	private ZonedDateTime createdAt;

	@Column(nullable = false)
	@Convert(converter = DOTimestampConverter.class)
	@UpdateTimestamp
	private ZonedDateTime updatedAt;

	@ManyToOne(targetEntity = FieldBookingDO.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parentBookingId")
	private FieldBookingDO parent;

    @OneToMany(targetEntity = FieldBookingDO.class, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "parent", fetch = FetchType.LAZY)
    private final List<FieldBookingDO> subBookings = new ArrayList<>();

    public FieldBookingDO addSubBooking(FieldBookingDO fieldBookingDO) {
        getSubBookings().add(fieldBookingDO);
        fieldBookingDO.setParent(this);
        return this;
    }
}
