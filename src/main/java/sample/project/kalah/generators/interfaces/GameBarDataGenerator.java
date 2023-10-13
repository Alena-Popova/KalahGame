package sample.project.kalah.generators.interfaces;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;

/**
 * This interface represents a generator for {@link GameBarData}.
 */
public interface GameBarDataGenerator
{

    /**
     * Generates a GameBarData based on the provided player and game entity.
     *
     * @param player The player for whom the game bar data is requested.
     * @param game   The game entity associated with the data request.
     * @return The game bar data for the specified player and game entity.
     */
    GameBarData generate(final Player player, final GameEntity game);
}
