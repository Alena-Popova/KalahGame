package sample.project.kalah.services.rules.interfaces;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.GameEntity;

/**
 * Interface representing a rule checker for a game move.
 * Rule checkers are used to determine if a move is allowed based on the game state and the next move to be made.
 */
public interface RuleChecker
{
    /**
     * Checks if a move is allowed based on the current game state and the next move.
     *
     * @param game The current game entity.
     * @param nextMove The move requested by the player.
     * @return True if the move is allowed, false otherwise.
     */
    boolean apply(GameEntity game, PlayerMoveData nextMove);
}
