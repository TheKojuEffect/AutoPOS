CREATE TABLE day_book_entry
(
  id                 BIGSERIAL PRIMARY KEY,
  txn_date           DATE UNIQUE                  NOT NULL,
  incoming_amount    NUMERIC(10, 2)               NOT NULL CHECK (incoming_amount >= 0),
  outgoing_amount    NUMERIC(10, 2)               NOT NULL CHECK (outgoing_amount >= 0),
  misc_expenses      NUMERIC(10, 2)               NOT NULL CHECK (misc_expenses >= 0),
  remarks            VARCHAR(500),

  version            BIGINT                       NOT NULL,
  created_date       TIMESTAMP                    NOT NULL,
  last_modified_date TIMESTAMP                    NOT NULL,
  created_by         BIGINT REFERENCES users (id) NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id) NOT NULL
);