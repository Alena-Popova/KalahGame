package sample.project.kalah.services.interfaces;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;

/**
 * This interface defines methods for retrieving and updating game bar data.
 */
public interface GameBarDataService
{

    /**
     * Updates the game bar data based on the next player move and the current game bar data.
     *
     * @param nextMove    The next move made by the player according to which it will be updated {@link GameBarData}
     * @param gameBarData The current game bar data to be updated.
     * @return Updated game bar data.
     */
    GameBarData updateGameBarData(PlayerMoveData nextMove, GameBarData gameBarData);
}
