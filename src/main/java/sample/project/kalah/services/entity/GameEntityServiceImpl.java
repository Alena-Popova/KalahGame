package sample.project.kalah.services.entity;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.repositories.interfaces.GameEntityRepository;
import sample.project.kalah.services.entity.interfaces.GameEntityService;

@Service("gameEntityService")
public class GameEntityServiceImpl implements GameEntityService
{
    @Autowired
    private GameEntityRepository gameEntityRepository;

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
}
