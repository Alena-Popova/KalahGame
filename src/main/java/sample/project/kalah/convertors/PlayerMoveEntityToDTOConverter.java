package sample.project.kalah.convertors;

import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.PlayerMoveDTO;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

@Component("playerMoveEntityToDTOConverter")
public class PlayerMoveEntityToDTOConverter implements Converter<PlayerMoveEntity, PlayerMoveDTO>
{
    @Override
    public PlayerMoveDTO convert(final PlayerMoveEntity source)
    {
        return PlayerMoveDTO.builder()
                .id(source.getId())
                .gameId(source.getGame() != null ? source.getGame().getId() : null)
                .moveNumber(source.getMoveNumber())
                .player(source.getPlayer())
                .startingPit(source.getStartingPit())
                .startsInKalah(source.isStartsInKalah())
                .isPlayerSide(source.isPlayerSide())
                .endingPit(source.getEndingPit())
                .endsInKalah(source.isEndsInKalah())
                .build();
    }
}
