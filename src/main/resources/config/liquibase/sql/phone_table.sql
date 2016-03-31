CREATE TABLE phone
(
  id                 BIGSERIAL PRIMARY KEY,
  number             CHARACTER VARYING(10) UNIQUE    NOT NULL CHECK (length(number) >= 7),

  opt_lock           BIGINT                          NOT NULL,
  created_date       TIMESTAMP                       NOT NULL,
  last_modified_date TIMESTAMP                       NOT NULL,
  created_by         BIGINT REFERENCES users (id)    NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)    NOT NULL
);

