package sample.project.kalah.convertors;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.dto.PlayerMoveResponse;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

@Component("gameEntityToDTOConverter")
public class GameEntityToDTOConverter implements Converter<GameEntity, GameConditionResponse>
{
    private final Converter<PlayerMoveEntity, PlayerMoveResponse> playerMoveEntityToDTOConverter;

    @Autowired
    public GameEntityToDTOConverter(final Converter<PlayerMoveEntity, PlayerMoveResponse> playerMoveEntityToDTOConverter)
    {
        this.playerMoveEntityToDTOConverter = playerMoveEntityToDTOConverter;
    }

    @Override
    public GameConditionResponse convert(final GameEntity source)
    {
        return GameConditionResponse.builder()
                .id(source.getId())
                .status(source.getStatus())
                .firstPlayerStones(toList(source.getFirstPlayerStones()))
                .firstPlayerKalah(source.getFirstPlayerKalah())
                .secondPlayerStones(toList(source.getSecondPlayerStones()))
                .secondPlayerKalah(source.getSecondPlayerKalah())
                .activePlayers(convertPlayers(source.getActivePlayers()))
                .moves(convertMoves(source.getMoves()))
                .victoriousPlayer(source.getVictoriousPlayer())
                .build();
    }

    private List<PlayerMoveResponse> convertMoves(List<PlayerMoveEntity> moves)
    {
        return moves != null
                ? moves.stream()
                .map(playerMoveEntityToDTOConverter::convert)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private List<Integer> toList(Integer[] array)
    {
        return array != null ? Arrays.asList(array) : Collections.emptyList();
    }

    private List<Player> convertPlayers(String[] array)
    {
        List<String> players = array != null ? Arrays.asList(array) : Collections.emptyList();
        return players
                .stream()
                .map(Player::valueOf)
                .collect(Collectors.toList());
    }

}
