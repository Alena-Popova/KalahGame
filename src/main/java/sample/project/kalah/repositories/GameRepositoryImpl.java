package sample.project.kalah.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.repositories.interfaces.GameRepository;

@Repository("gameRepository")
public class GameRepositoryImpl implements GameRepository
{
    @Override
    public Optional<GameEntity> getGameById(final UUID gameId)
    {
        return Optional.empty();
    }

    @Override
    public GameEntity updateGame(final UUID gameId)
    {
        return null;
    }

    @Override
    public GameEntity saveGame(final GameEntity gameEntity)
    {
        return null;
    }
}
