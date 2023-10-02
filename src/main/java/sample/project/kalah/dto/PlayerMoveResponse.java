package sample.project.kalah.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.Player;

@Data
@Builder
@JsonDeserialize(builder = PlayerMoveResponse.PlayerMoveResponseBuilder.class)
public class PlayerMoveResponse
{

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("game_id")
    private UUID gameId;

    @JsonProperty("move_number")
    private Integer moveNumber;

    @JsonProperty("player")
    private Player player;

    @JsonProperty("starting_pit")
    private Integer startingPit;

    @JsonProperty("starts_in_player_kalah")
    private boolean startsInPlayerKalah;

    @JsonProperty("is_player_side")
    private boolean isPlayerSide;

    @JsonProperty("ending_pit")
    private Integer endingPit;

    @JsonProperty("ends_in_player_kalah")
    private boolean endsInPlayerKalah;
}
