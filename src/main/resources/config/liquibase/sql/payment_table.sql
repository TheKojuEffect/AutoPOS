CREATE TABLE payment (
  id                 BIGSERIAL PRIMARY KEY,
  date               TIMESTAMP                     NOT NULL,
  amount             NUMERIC(10, 2)                NOT NULL,
  receipt_number     VARCHAR(10),
  paid_by            VARCHAR(100)                  NOT NULL,
  remarks            VARCHAR(250),
  paid_to_id         BIGINT REFERENCES vendor (id) NOT NULL,

  version            BIGINT                        NOT NULL,
  created_date       TIMESTAMP                     NOT NULL,
  last_modified_date TIMESTAMP                     NOT NULL,
  created_by         BIGINT REFERENCES users (id)  NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)  NOT NULL
);