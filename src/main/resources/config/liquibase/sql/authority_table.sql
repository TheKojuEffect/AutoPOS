CREATE TABLE authority (
    id        BIGSERIAL PRIMARY KEY          NOT NULL,
    role_name VARCHAR(40) UNIQUE             NOT NULL
);
