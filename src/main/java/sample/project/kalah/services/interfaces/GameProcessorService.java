package sample.project.kalah.services.interfaces;

import java.util.UUID;

import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameJoinException;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.exceptions.MoveNotAllowedException;

/**
 * Interface representing a service for processing game-related actions.
 */
public interface GameProcessorService
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
     * @throws GameNotFoundException If the specified game is not found.
     * @throws GameJoinException     If the player cannot join the game.
     */
    GameDTO joinGame(UUID gameId, String player) throws GameNotFoundException, GameJoinException;

    /**
     * Finishes the game.
     *
     * @param gameId The unique identifier of the game.
     * @param player The player finishing the game.
     * @return The updated {@link GameDTO} object representing the game after it has been finished.
     * @throws GameNotFoundException If the specified game is not found.
     */
    GameDTO finishGame(UUID gameId, String player) throws GameNotFoundException;

    /**
     * Makes a move in the specified game.
     *
     * @param gameId   The unique identifier of the game.
     * @param player   The player making the move.
     * @param position The position from which the player is making the move.
     * @return The updated {@link GameEntity} object representing the game after the move.
     * @throws GameNotFoundException   If the specified game is not found.
     * @throws MoveNotAllowedException If the move is not allowed in the game from the specified position.
     */
    GameDTO makeMove(UUID gameId, Player player, Integer position) throws GameNotFoundException, MoveNotAllowedException;

}
