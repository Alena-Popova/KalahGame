package sample.project.kalah.convertors;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

@Component("gameEntityToDataConverter")
public class GameEntityToDataConverter implements Converter<GameEntity, GameConditionData>
{
    private final Converter<PlayerMoveEntity, PlayerMoveData> playerMoveEntityToDataConverter;

    @Autowired
    public GameEntityToDataConverter(final Converter<PlayerMoveEntity, PlayerMoveData> playerMoveEntityToDataConverter)
    {
        this.playerMoveEntityToDataConverter = playerMoveEntityToDataConverter;
    }

    @Override
    public GameConditionData convert(final GameEntity source)
    {
        Assert.notNull(source, "source cannot be null");

        return GameConditionData.builder()
                .id(source.getId())
                .status(source.getStatus())
                .firstPlayerStones(source.getFirstPlayerStonesList())
                .firstPlayerKalah(source.getFirstPlayerKalah())
                .secondPlayerStones(source.getSecondPlayerStonesList())
                .secondPlayerKalah(source.getSecondPlayerKalah())
                .activePlayers(source.getActivePlayersList())
                .moves(convertMoves(source.getMoves()))
                .winner(source.getWinner())
                .build();
    }

    private List<PlayerMoveData> convertMoves(List<PlayerMoveEntity> moves)
    {
        return Objects.isNull(moves)
                ? Collections.emptyList()
                : moves.stream()
                .map(playerMoveEntityToDataConverter::convert)
                .collect(Collectors.toList());
    }

}
