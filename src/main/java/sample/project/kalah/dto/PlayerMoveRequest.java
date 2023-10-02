package sample.project.kalah.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.Player;

@Data
@Builder
@JsonDeserialize(builder = PlayerMoveRequest.PlayerMoveRequestBuilder.class)
public class PlayerMoveRequest
{
    @JsonProperty("player")
    private Player player;

    @JsonProperty("starting_pit")
    private Integer startingPit;

    @JsonProperty("starts_in_player_kalah")
    private boolean startsInPlayerKalah;

    @JsonProperty("is_player_side")
    private boolean isPlayerSide;
}
