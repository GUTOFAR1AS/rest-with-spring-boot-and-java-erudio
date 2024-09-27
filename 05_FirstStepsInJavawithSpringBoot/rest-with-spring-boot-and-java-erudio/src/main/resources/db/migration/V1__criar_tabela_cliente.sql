CREATE TABLE person (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR NOT NULL,
                      phone VARCHAR,
                      email VARCHAR NOT NULL UNIQUE,
                      birthday DATE
);
