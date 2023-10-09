package sample.project.kalah.services.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.repositories.interfaces.GameEntityRepository;
import sample.project.kalah.services.entity.interfaces.GameEntityService;

@Service("gameEntityService")
public class GameEntityServiceImpl implements GameEntityService
{
    private final GameEntityRepository gameEntityRepository;

    @Autowired
    public GameEntityServiceImpl(final GameEntityRepository gameEntityRepository)
    {
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public GameEntity getGame(final UUID gameId) throws GameNotFoundException
    {
        return gameEntityRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id '%s' not found", gameId)));
    }

    @Override
    public GameEntity saveAndFlush(final GameEntity gameEntity)
    {
        return gameEntityRepository.saveAndFlush(gameEntity);
    }


    @Override
    public GameEntity updateGameEntity(final GameBarData updatedGameBarData,
                                       final PlayerMoveEntity nextPlayerMoveEntity,
                                       final GameEntity gameEntity)
    {
        if (Player.FIRST_PLAYER.equals(nextPlayerMoveEntity.getPlayer()))
        {
            gameEntity.setFirstPlayerStonesList(updatedGameBarData.getPlayerBar());
            gameEntity.setFirstPlayerKalah(updatedGameBarData.getPlayerKalah());
            gameEntity.setSecondPlayerStonesList(updatedGameBarData.getOppositePlayerBar());
        }
        else
        {
            gameEntity.setSecondPlayerStonesList(updatedGameBarData.getPlayerBar());
            gameEntity.setSecondPlayerKalah(updatedGameBarData.getPlayerKalah());
            gameEntity.setFirstPlayerStonesList(updatedGameBarData.getOppositePlayerBar());
        }

        List<PlayerMoveEntity> moves = new ArrayList<>(gameEntity.getMoves());
        moves.add(nextPlayerMoveEntity);
        gameEntity.setMoves(moves);

        return saveAndFlush(gameEntity);
    }
}
