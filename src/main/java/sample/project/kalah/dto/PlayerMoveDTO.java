package sample.project.kalah.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.Player;

/**
 * Data Transfer Object (DTO) representing a player move.
 */
@Data
@Builder
@JsonDeserialize(builder = PlayerMoveDTO.PlayerMoveDTOBuilder.class)
public class PlayerMoveDTO
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

    @JsonProperty("starts_in_kalah")
    private boolean startsInKalah;

    @JsonProperty("is_player_side")
    private boolean isPlayerSide;

    @JsonProperty("ending_pit")
    private Integer endingPit;

    @JsonProperty("ends_in_kalah")
    private boolean endsInKalah;
}
