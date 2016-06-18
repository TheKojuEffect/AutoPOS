CREATE TABLE sale_invoice (
  id             BIGSERIAL PRIMARY KEY,
  date           TIMESTAMP                            NOT NULL,
  sub_total      NUMERIC(10, 2)                       NOT NULL,
  discount       NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  tax            NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  taxable_amount NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  grand_total    NUMERIC(10, 2)                       NOT NULL,
  client         VARCHAR(100)                         NOT NULL,
  remarks        VARCHAR(250),
  issued_by      VARCHAR(100)                         NOT NULL,
  vehicle_id     BIGINT
);