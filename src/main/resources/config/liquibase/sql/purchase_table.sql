CREATE TABLE purchase (
  id                 BIGSERIAL PRIMARY KEY,
  date               TIMESTAMP                            NOT NULL,
  discount           NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  vendor_id          BIGINT REFERENCES vendor (id),
  invoice_number     VARCHAR(50),
  remarks            VARCHAR(250),

  version            BIGINT                               NOT NULL,
  created_date       TIMESTAMP                            NOT NULL,
  last_modified_date TIMESTAMP                            NOT NULL,
  created_by         BIGINT REFERENCES users (id)         NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)         NOT NULL
);
