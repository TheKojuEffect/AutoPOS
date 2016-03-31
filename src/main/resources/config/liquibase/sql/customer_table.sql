CREATE TABLE customer (
  id                 BIGSERIAL PRIMARY KEY,
  name               CHARACTER VARYING(100) UNIQUE NOT NULL CHECK (length(name) >= 2),
  remarks            CHARACTER VARYING(250),

  opt_lock           BIGINT                        NOT NULL,
  created_date       TIMESTAMP                     NOT NULL,
  last_modified_date TIMESTAMP                     NOT NULL,
  created_by         BIGINT REFERENCES users (id)  NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id)  NOT NULL
);


CREATE TABLE customer_phone (
  customer_id BIGINT REFERENCES customer (id),
  phone_id    BIGINT REFERENCES phone (id),
  CONSTRAINT customer_phone_pkey PRIMARY KEY (customer_id, phone_id)
)


