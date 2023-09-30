package sample.project.kalah.services.interfaces;

import java.util.UUID;

import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameActionNotAllowedException;
import sample.project.kalah.exceptions.GameNotFoundException;

/**
 * Interface representing a service for processing game-related actions.
 */
public interface GameActionService
{
    /**
     * Creates a new game.
     *
     * @return The created new {@link GameEntity}.
     */
    GameDTO createGame();

    /**
     * Get an existing game by unique identifier.
     *
     * @param gameId The unique identifier of the game.
     * @return {@link GameDTO} an existing game.
     * @throws GameNotFoundException If the specified game is not found.
     */
    GameDTO getGame(UUID gameId) throws GameNotFoundException;

    /**
     * Joins an existing game.
     *
     * @param gameId The unique identifier of the game.
     * @param player The player joining the game.
     * @return The updated {@link GameEntity} object representing the game after the player has joined.
     * @throws GameNotFoundException         If the specified game is not found.
     * @throws GameActionNotAllowedException If a game action is not allowed.
     */
    GameDTO joinGame(UUID gameId, String player) throws GameNotFoundException, GameActionNotAllowedException;

    /**
     * Finishes the game.
     *
     * @param gameId The unique identifier of the game.
     * @param player The player finishing the game.
     * @return The updated {@link GameDTO} object representing the game after it has been finished.
     * @throws GameNotFoundException If the specified game is not found.
     */
    GameDTO finishGame(UUID gameId, String player) throws GameNotFoundException;
}
