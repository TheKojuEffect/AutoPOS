CREATE TABLE category
(
  id                 BIGSERIAL PRIMARY KEY        NOT NULL,
  short_name         CHARACTER VARYING(3)         NOT NULL CHECK (length(short_name) >= 2),
  name               CHARACTER VARYING(50)        NOT NULL CHECK (length(name) >= 3),

  version            BIGINT                       NOT NULL,
  created_date       TIMESTAMP                    NOT NULL,
  last_modified_date TIMESTAMP                    NOT NULL,
  created_by         BIGINT REFERENCES users (id) NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id) NOT NULL
)
