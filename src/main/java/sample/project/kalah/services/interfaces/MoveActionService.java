package sample.project.kalah.services.interfaces;

import java.util.UUID;

import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.exceptions.MoveNotAllowedException;

/**
 * This interface defines the actions related to player moves in a game.
 */
public interface MoveActionService
{

    /**
     * Makes a move in the game based on the provided move and game identifier.
     *
     * @param gameId   The unique identifier of the game.
     * @param nextMove The move to be made.
     * @return The updated {@link GameConditionData} game state after the move.
     * @throws MoveNotAllowedException If the move is not allowed for the player.
     */
    GameConditionData makeMove(UUID gameId, PlayerMoveData nextMove) throws MoveNotAllowedException;
}
