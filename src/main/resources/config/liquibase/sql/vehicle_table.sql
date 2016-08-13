CREATE TABLE vehicle (
  id                 BIGSERIAL PRIMARY KEY,
  number             CHARACTER VARYING(20) UNIQUE  NOT NULL CHECK (length(number) >= 1),
  remarks            CHARACTER VARYING(250),
  owner_id           BIGINT REFERENCES customer (id),

  version           BIGINT                        NOT NULL,
  created_date       TIMESTAMP                     NOT NULL,
  last_modified_date TIMESTAMP                     NOT NULL,
  created_by         BIGINT REFERENCES users (id)  NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)  NOT NULL
);