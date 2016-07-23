CREATE TABLE sale (
  id                 BIGSERIAL PRIMARY KEY,
  date               TIMESTAMP                            NOT NULL,
  buyer              VARCHAR(250)                         NOT NULL,
  sub_total          NUMERIC(10, 2)                       NOT NULL,
  discount           NUMERIC(10, 2) DEFAULT 0             NOT NULL,
  grand_total        NUMERIC(10, 2)                       NOT NULL,
  vehicle_id         BIGINT REFERENCES vehicle (id),
  status             VARCHAR(15)                          NOT NULL,
  remarks            VARCHAR(250),

  opt_lock           BIGINT                               NOT NULL,
  created_date       TIMESTAMP                            NOT NULL,
  last_modified_date TIMESTAMP                            NOT NULL,
  created_by         BIGINT REFERENCES users (id)         NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)         NOT NULL
);
