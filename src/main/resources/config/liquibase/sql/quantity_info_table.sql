CREATE TABLE quantity_info (
    id                 BIGSERIAL PRIMARY KEY,
    quantity           INT DEFAULT 0                     NOT NULL  CHECK (quantity >= 0),

    opt_lock           BIGINT                            NOT NULL,
    created_date       TIMESTAMP                         NOT NULL,
    last_modified_date TIMESTAMP                         NOT NULL,
    created_by         BIGINT REFERENCES users (id)      NOT NULL,
    last_modified_by   BIGINT REFERENCES users (id)      NOT NULL
)
