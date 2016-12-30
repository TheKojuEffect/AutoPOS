CREATE TABLE vendor_phone_numbers (
  vendor_id    BIGINT                NOT NULL REFERENCES vendor (id),
  phone_number CHARACTER VARYING(10) NOT NULL,
  PRIMARY KEY (vendor_id, phone_number)
);