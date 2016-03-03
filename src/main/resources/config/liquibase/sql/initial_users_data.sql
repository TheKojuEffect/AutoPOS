INSERT INTO authority VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority VALUES (2, 'ROLE_USER');


ALTER SEQUENCE authority_id_seq RESTART WITH 3;


INSERT INTO users VALUES
    (1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System',
        'system@localhost', TRUE, 'en', NULL, NULL, '2016-03-02 08:48:42.087114', now(),
     now(), 1, 1);
INSERT INTO users VALUES
    (2, 'anonymousUser', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User',
        'anonymous@localhost', TRUE, 'en', NULL, NULL, '2016-03-02 08:48:42.087114', now(),
     now(), 1, 1);
INSERT INTO users VALUES
    (3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator',
        'admin@localhost', TRUE, 'en', NULL, NULL, '2016-03-02 08:48:42.087114', now(),
     now(), 1, 1);
INSERT INTO users VALUES
    (4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', TRUE,
        'en', NULL, NULL, '2016-03-02 08:48:42.087114', now(), now(), 1, 1);


ALTER SEQUENCE users_id_seq RESTART WITH 5;


INSERT INTO user_authority VALUES (1, 1);
INSERT INTO user_authority VALUES (1, 2);
INSERT INTO user_authority VALUES (3, 1);
INSERT INTO user_authority VALUES (3, 2);
INSERT INTO user_authority VALUES (4, 2);
