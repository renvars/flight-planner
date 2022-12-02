--liquibase formatted sql

--changeset renars:2

CREATE TABLE Airports
(
    airport varchar(255) unique not null primary key,
    country varchar(255)        not null,
    city    varchar(255)        not null

);
CREATE TABLE Flights
(
    id             serial PRIMARY KEY,
    from_airport   varchar(10)  not null,
    to_airport     varchar(10)  not null,
    carrier        varchar(255) not null,
    departure_time TIMESTAMP    not null,
    arrival_time   TIMESTAMP    not null,
    CONSTRAINT flights_from_airport_fkey FOREIGN KEY (from_airport) references Airports (airport),
    CONSTRAINT flights_to_airport_fkey FOREIGN KEY (to_airport) references Airports (airport)

);