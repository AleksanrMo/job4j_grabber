create table if not exists posts(
    id serial primary key,
    post_id int,
    name varchar(255),
    link text,
    description text,
    created timestamp
);