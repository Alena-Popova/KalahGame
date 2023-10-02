package sample.project.kalah.dto;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;

@Data
@Builder
@JsonDeserialize(builder = GameConditionResponse.GameConditionResponseBuilder.class)
public class GameConditionResponse
{

    @JsonProperty
    private UUID id;

    @JsonProperty
    private GameStatus status;

    @JsonProperty("first_player_stones")
    private List<Integer> firstPlayerStones;

    @JsonProperty("first_player_kalah")
    private Integer firstPlayerKalah;

    @JsonProperty("second_player_stones")
    private List<Integer> secondPlayerStones;

    @JsonProperty("second_player_kalah")
    private Integer secondPlayerKalah;

    @JsonProperty("active_players")
    private List<Player> activePlayers;

    @JsonProperty("moves")
    private List<PlayerMoveResponse> moves;

    @JsonProperty("victorious_player")
    private Player victoriousPlayer;
}
