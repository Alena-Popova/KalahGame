--liquibase formatted sql
--changeset helezpopova:0001

CREATE TABLE kalah_games_data (
    id UUID PRIMARY KEY,
    status VARCHAR(20) CHECK (status IN ('INIT', 'RUNNING', 'FINISHED')),
    first_player_stones INTEGER[],
    first_player_kalah INTEGER,
    second_player_stones INTEGER[],
    second_player_kalah INTEGER,
    active_players VARCHAR[],
    winner VARCHAR
);

CREATE TABLE player_moves (
    id UUID PRIMARY KEY,
    game_id UUID,
    move_number INTEGER,
    player VARCHAR,
    starts_on_player_side BOOLEAN,
    starting_pit INTEGER,
    ends_on_player_side BOOLEAN,
    ending_pit INTEGER,
    ends_in_player_kalah BOOLEAN,
    FOREIGN KEY (game_id) REFERENCES kalah_games_data(id)
);