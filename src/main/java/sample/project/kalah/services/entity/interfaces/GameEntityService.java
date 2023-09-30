package sample.project.kalah.services.entity.interfaces;

import java.util.UUID;

import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameNotFoundException;

/**
 * Service interface for managing game entities.
 */
public interface GameEntityService
{

    /**
     * Retrieves an existing game by its unique identifier.
     *
     * @param gameId The unique identifier of the game.
     * @return The {@link GameEntity} representing the existing game.
     * @throws GameNotFoundException If the specified game is not found.
     */
    GameEntity getGame(UUID gameId) throws GameNotFoundException;

    /**
     * Saves the provided {@link GameEntity} and flushes the changes to the underlying data store.
     *
     * @param gameEntity The {@link GameEntity} to be saved.
     * @return The saved {@link GameEntity}.
     */
    GameEntity saveAndFlush(GameEntity gameEntity);
}

