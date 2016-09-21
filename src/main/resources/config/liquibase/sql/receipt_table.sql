CREATE TABLE receipt (
  id                 BIGSERIAL PRIMARY KEY,
  date               TIMESTAMP                             NOT NULL,
  amount             NUMERIC(10, 2)                        NOT NULL,
  receipt_number     BIGINT UNIQUE                         NOT NULL,
  received_by        VARCHAR(100)                          NOT NULL,
  remarks            VARCHAR(250),
  received_from_id   BIGINT REFERENCES customer (id)       NOT NULL,

  version            BIGINT                                NOT NULL,
  created_date       TIMESTAMP                             NOT NULL,
  last_modified_date TIMESTAMP                             NOT NULL,
  created_by         BIGINT REFERENCES users (id)          NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)          NOT NULL
);