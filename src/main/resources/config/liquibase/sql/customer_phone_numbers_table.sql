CREATE TABLE customer_phone_numbers (
  customer_id  BIGINT                NOT NULL REFERENCES customer (id),
  phone_number CHARACTER VARYING(10) NOT NULL,
  PRIMARY KEY (customer_id, phone_number)
);