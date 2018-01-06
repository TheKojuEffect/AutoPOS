CREATE TABLE stock_book_entry (
  id                 BIGSERIAL PRIMARY KEY,

  item_id            BIGINT REFERENCES item (id)  NOT NULL UNIQUE,
  cost_price         DECIMAL(10, 2)               NOT NULL CHECK (cost_price > 0),
  quantity           INT                          NOT NULL CHECK (quantity > 0),
  remarks            VARCHAR(250),

  version            BIGINT                       NOT NULL,
  created_date       TIMESTAMP                    NOT NULL,
  last_modified_date TIMESTAMP                    NOT NULL,
  created_by         BIGINT REFERENCES users (id) NOT NULL,
  last_modified_by   BIGINT REFERENCES users (id) NOT NULL

);