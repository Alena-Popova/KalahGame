package sample.project.kalah.services.interfaces;

import java.util.Optional;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.MoveNotAllowedException;

/**
 * This interface defines the actions related to player moves in a game.
 */
public interface MoveActionService
{
    /**
     * Makes a move in the game.
     *
     * @param move The move to be made.
     * @param game The current game state.
     * @return The updated game state after the move.
     * @throws MoveNotAllowedException If the move is not allowed in the current game state.
     */
    GameEntity makeMove(PlayerMoveEntity move, GameEntity game) throws MoveNotAllowedException;

    /**
     * Checks if the given move is allowed in the current game state.
     *
     * @param move The move to be checked.
     * @param game The current game state.
     * @return True if the move is allowed, false otherwise.
     * @throws MoveNotAllowedException If the move is not allowed in the current game state.
     */
    boolean isMoveAllowed(PlayerMoveEntity move, GameEntity game) throws MoveNotAllowedException;

    /**
     * Determines the winner of the game, if any.
     *
     * @param game The current game state.
     * @return Optional of the winning player, if a winner is determined.
     */
    Optional<Player> determineWinner(GameEntity game);
}
