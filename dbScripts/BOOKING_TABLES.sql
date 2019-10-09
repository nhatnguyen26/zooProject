-- THIS FILE WILL RE-CREATE TABLE, ONLY USE FOR DEV, EXECUTE WITH CAUTION

DROP TABLE IF EXISTS FIELD_BOOKINGS;
CREATE TABLE FIELD_BOOKINGS (
    id      SERIAL PRIMARY KEY,
    parent_booking_id INT,
    booking_number VARCHAR(32),
    court_id INT,
    main_field_type INT,
    field_type_id INT,
    field_id INT,
    time_in  BIGINT,
    time_out BIGINT,
	actual_time_in  BIGINT,
    actual_time_out BIGINT,
    admin_note TEXT,
    status  INT,
    booker_user_id  INT,
    regular_booker BOOLEAN,
    booker_name  VARCHAR(64),
    booker_email VARCHAR(254),
    booker_phone VARCHAR(15),
    price_amount   NUMERIC(19,4),
    deposit_amount NUMERIC(19,4),
    actual_charged_amount NUMERIC(19,4),
    currency_id    VARCHAR(8),
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL,
    UNIQUE (booking_number)
);

CREATE INDEX IX_FIELD_BOOKINGS_PARENT_BOOKING_ID ON FIELD_BOOKINGS(parent_booking_id);
CREATE INDEX IX_FIELD_BOOKINGS_TIME_INT ON FIELD_BOOKINGS(time_in);
CREATE INDEX IX_FIELD_BOOKINGS_BOOKING_NUMBER ON FIELD_BOOKINGS(booking_number);
CREATE INDEX IX_FIELD_BOOKINGS_COURT_ID ON FIELD_BOOKINGS(court_id);
CREATE INDEX IX_FIELD_BOOKINGS_MAIN_FIELD_TYPE ON FIELD_BOOKINGS(main_field_type);
CREATE INDEX IX_FIELD_BOOKINGS_FIELD_ID ON FIELD_BOOKINGS(field_id);
CREATE INDEX IX_FIELD_BOOKINGS_FIELD_TYPE_ID ON FIELD_BOOKINGS(field_type_id);
CREATE INDEX IX_FIELD_BOOKINGS_BOOKER_PHONE ON FIELD_BOOKINGS(booker_phone);
CREATE INDEX IX_FIELD_BOOKINGS_BOOKER_EMAIL ON FIELD_BOOKINGS(booker_email);
