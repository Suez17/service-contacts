CREATE TABLE contact
(
    id           uuid    NOT NULL,
    first_name   varchar NOT NULL,
    last_name    varchar NOT NULL,
    birth_date   date    NOT NULL,
    address      varchar NOT NULL,
    email        varchar NOT NULL,
    phone_number varchar NOT NULL,
    CONSTRAINT contact_pk PRIMARY KEY (id)
);
