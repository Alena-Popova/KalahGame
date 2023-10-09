package sample.project.kalah.generators.interfaces;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

/**
 * This interface represents a generator for {@code PlayerMoveEntity}.
 */
public interface PlayerMoveEntityGenerator
{

    /**
     * Generates a PlayerMoveEntity based on the provided game state and move request.
     *
     * @param nextMove The player's move data.
     * @return A PlayerMoveEntity representing the generated move.
     */
    PlayerMoveEntity generate(PlayerMoveData nextMove);
}
