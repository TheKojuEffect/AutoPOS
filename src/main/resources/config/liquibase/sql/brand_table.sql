CREATE TABLE brand
(
    id                 BIGSERIAL PRIMARY KEY        NOT NULL,
    name               CHARACTER VARYING(50)        NOT NULL CHECK (length(name) >= 2),

    created_date       TIMESTAMP                    NOT NULL,
    last_modified_date TIMESTAMP                    NOT NULL,
    created_by         BIGINT REFERENCES users (id) NOT NULL,
    last_modified_by   BIGINT REFERENCES users (id) NOT NULL
)
