--liquibase formatted sql
--changeset helezpopova:0001

create sequence hibernate_sequence start 1 increment 1;

CREATE TABLE game_data (
    id UUID PRIMARY KEY,
    status VARCHAR(20),
    first_player_stones INTEGER[],
    second_player_stones INTEGER[]
);