CREATE TABLE purchase_line (
  id                 BIGSERIAL PRIMARY KEY,
  purchase_id        BIGINT REFERENCES purchase (id)          NOT NULL,
  item_id            BIGINT REFERENCES item (id)              NOT NULL,
  quantity           INT                                      NOT NULL  CHECK (quantity > 0),
  rate               NUMERIC(10, 2)                           NOT NULL  CHECK (rate > 0),
  remarks            VARCHAR(250),

  version            BIGINT                                   NOT NULL,
  created_date       TIMESTAMP                                NOT NULL,
  last_modified_date TIMESTAMP                                NOT NULL,
  created_by         BIGINT REFERENCES users (id)             NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)             NOT NULL
);