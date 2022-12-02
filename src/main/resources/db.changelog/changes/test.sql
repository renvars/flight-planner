--liquibase formatted sql

--changeset renars:1

CREATE TABLE some_data
(
    data_id   INT PRIMARY KEY,
    data_name varchar(255) not null

);