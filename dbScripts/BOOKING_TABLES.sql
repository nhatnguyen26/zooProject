-- THIS FILE WILL RE-CREATE TABLE, ONLY USE FOR DEV, EXECUTE WITH CAUTION
DROP TABLE IF EXISTS FIELD_BOOKINGS;

CREATE TABLE FIELD_BOOKINGS (
    id      SERIAL PRIMARY KEY,
    court_id INT,
    field_type VARCHAR(20),
    field_id INT,
    time_in  BIGINT NOT NULL,
    time_out BIGINT NOT NULL,
    status  VARCHAR(20),
    booker_user_id  INT,
    booker_name  VARCHAR(64),
    booker_email VARCHAR(254),
    booker_phone VARCHAR(15),
    price_amount   NUMERIC(19,4),
    currency_id    VARCHAR(8),
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE FIELD_BOOKINGS TO zoodb;
GRANT ALL PRIVILEGES ON TABLE field_bookings_id_seq TO zoodb;
