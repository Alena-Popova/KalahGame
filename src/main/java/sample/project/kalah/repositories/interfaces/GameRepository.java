package sample.project.kalah.repositories.interfaces;

import java.util.Optional;
import java.util.UUID;

import sample.project.kalah.entity.sql.GameEntity;

/**
 * Repository interface for managing game data.
 */
public interface GameRepository
{

    /**
     * Retrieves a game by its unique identifier.
     *
     * @param gameId The unique identifier of the game.
     * @return An {@link Optional} containing the {@link GameEntity} object representing the game,
     * or empty if the game with the specified identifier is not found.
     */
    Optional<GameEntity> getGameById(UUID gameId);

    /**
     * Updates an existing game with the specified game identifier.
     *
     * @param gameId The unique identifier of the game to update.
     * @return The updated {@link GameEntity} object representing the game after the update.
     */
    GameEntity updateGame(UUID gameId);

    /**
     * Saves the given game entity.
     *
     * @param gameEntity The game entity to be saved.
     * @return The saved {@link GameEntity} object.
     */
    GameEntity saveGame(GameEntity gameEntity);

}
