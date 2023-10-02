package sample.project.kalah.services.interfaces;

import java.util.Optional;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;

/**
 * Service responsible for determining the winner of the game based on the current game state.
 */
public interface GameWinnerService
{
    /**
     * Determines the winner of the game based on the current game state.
     *
     * @param game The current game state.
     * @return An optional containing the winner if present, empty otherwise.
     */
    Optional<Player> determineWinner(GameEntity game);
}
