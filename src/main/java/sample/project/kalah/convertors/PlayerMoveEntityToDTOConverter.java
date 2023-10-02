package sample.project.kalah.convertors;

import java.util.UUID;

import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.PlayerMoveResponse;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

@Component("playerMoveEntityToDTOConverter")
public class PlayerMoveEntityToDTOConverter implements Converter<PlayerMoveEntity, PlayerMoveResponse>
{
    @Override
    public PlayerMoveResponse convert(final PlayerMoveEntity source)
    {
        final UUID gameId = source.getGame() != null ? source.getGame().getId() : null;

        return PlayerMoveResponse.builder()
                .id(source.getId())
                .gameId(gameId)
                .moveNumber(source.getMoveNumber())
                .player(source.getPlayer())
                .startingPit(source.getStartingPit())
                .startsInPlayerKalah(source.isStartsInPlayerKalah())
                .isPlayerSide(source.isPlayerSide())
                .endingPit(source.getEndingPit())
                .endsInPlayerKalah(source.isEndsInPlayerKalah())
                .build();
    }
}
