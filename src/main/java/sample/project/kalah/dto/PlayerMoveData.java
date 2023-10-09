package sample.project.kalah.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.Player;

@Data
@Builder
@JsonDeserialize(builder = PlayerMoveData.PlayerMoveDataBuilder.class)
public class PlayerMoveData
{
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("game_id")
    private UUID gameId;

    @JsonProperty("move_number")
    private Integer moveNumber;

    @JsonProperty("player")
    private Player player;

    @JsonProperty("starts_on_player_side")
    private boolean startsOnPlayerSide;

    @JsonProperty("starting_pit")
    private Integer startingPit;

    @JsonProperty("ends_on_player_side")
    private boolean endsOnPlayerSide;

    @JsonProperty("ending_pit")
    private Integer endingPit;

    @JsonProperty("ends_in_player_kalah")
    private boolean endsInPlayerKalah;
}
