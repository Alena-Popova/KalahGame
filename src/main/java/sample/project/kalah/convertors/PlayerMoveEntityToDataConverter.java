package sample.project.kalah.convertors;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

@Component("playerMoveEntityToDataConverter")
public class PlayerMoveEntityToDataConverter implements Converter<PlayerMoveEntity, PlayerMoveData>
{
    @Override
    public PlayerMoveData convert(final PlayerMoveEntity source)
    {
        Assert.notNull(source, "source cannot be null");

        final UUID gameId = Objects.isNull(source.getGame())
                ? null
                : source.getGame().getId();

        return PlayerMoveData.builder()
                .id(source.getId())
                .gameId(gameId)
                .moveNumber(source.getMoveNumber())
                .player(source.getPlayer())
                .startsOnPlayerSide(source.isStartsOnPlayerSide())
                .startingPit(source.getStartingPit())
                .endsOnPlayerSide(source.isEndsOnPlayerSide())
                .endingPit(source.getEndingPit())
                .endsInPlayerKalah(source.isEndsInPlayerKalah())
                .build();
    }
}
