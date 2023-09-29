--liquibase formatted sql
--changeset helezpopova:0001

CREATE TABLE kalah_games_data (
    id UUID PRIMARY KEY,
    status VARCHAR(20) CHECK (status IN ('INIT', 'RUNNING', 'FINISHED')),
    first_player_stones INTEGER[],
    first_player_kalah INTEGER,
    second_player_stones INTEGER[],
    second_player_kalah INTEGER,
    active_players VARCHAR[] DEFAULT '{}' NOT NULL
);