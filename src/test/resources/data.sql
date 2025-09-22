-- Drop table if exists.
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS todos;
DROP TABLE IF EXISTS user_authorities;
DROP TABLE IF EXISTS todos_seq;
DROP TABLE IF EXISTS users_seq;

create table users (
    created_at datetime(6),
    id bigint not null,
    updated_at datetime(6),
    email varchar(100) not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    primary key (id));

create table todos (complete bit not null,
                    priority integer not null,
                    id bigint not null,
                    owner_id bigint not null,
                    description varchar(255) not null,
                    title varchar(255) not null,
                    primary key (id));

create table user_authorities (
    user_id bigint not null,
    authority varchar(255));

create table todos_seq (next_val bigint);

create table users_seq (next_val bigint);

-- Inserting data

    insert into users_seq(next_val) values (1);
    insert into todos_seq(next_val) values (1);





