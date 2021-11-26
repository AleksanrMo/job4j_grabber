create table if not exists posts(
    id serial primary key,
    name varchar(255),
    description text,
    link varchr(255) UNIQUE,
    created timestamp
);