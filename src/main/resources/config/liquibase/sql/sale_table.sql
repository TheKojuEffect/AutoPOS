CREATE TABLE sale (
  id                 BIGSERIAL PRIMARY KEY,
  date               TIMESTAMP                            NOT NULL,
  buyer              VARCHAR(250),
  discount           NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  vehicle_id         BIGINT REFERENCES vehicle (id),
  status             VARCHAR(15)                          NOT NULL,
  invoice_number     VARCHAR(50) UNIQUE,
  remarks            VARCHAR(250),

  version            BIGINT                               NOT NULL,
  created_date       TIMESTAMP                            NOT NULL,
  last_modified_date TIMESTAMP                            NOT NULL,
  created_by         BIGINT REFERENCES users (id)         NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)         NOT NULL
);
