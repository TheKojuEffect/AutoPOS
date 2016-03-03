CREATE TABLE users (
    id                 BIGSERIAL PRIMARY KEY                NOT NULL,
    login              VARCHAR(50)                          NOT NULL,
    password           CHAR(60),
    first_name         VARCHAR(50),
    last_name          VARCHAR(50),
    email              VARCHAR(100),
    activated          BOOLEAN                              NOT NULL,
    lang_key           VARCHAR(5),
    activation_key     VARCHAR(20),
    reset_key          VARCHAR(20),
    reset_date         TIMESTAMP,

    created_date       TIMESTAMP                            NOT NULL,
    last_modified_date TIMESTAMP                            NOT NULL,
    created_by         BIGINT REFERENCES users (id)         NOT NULL,
    last_modified_by   BIGINT REFERENCES users (id)         NOT NULL
);


CREATE TABLE user_authority (
    user_id      BIGINT REFERENCES users (id)     NOT NULL,
    authority_id BIGINT REFERENCES authority (id) NOT NULL,
    CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_id)
);
