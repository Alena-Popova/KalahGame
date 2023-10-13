package sample.project.kalah.generators;

import org.springframework.stereotype.Component;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.generators.interfaces.GameBarDataGenerator;

@Component("gameBarDataGenerator")
public class GameBarDataGeneratorImpl implements GameBarDataGenerator
{
    @Override
    public GameBarData generate(final Player player, final GameEntity game)
    {
        if (Player.FIRST_PLAYER.equals(player))
        {
            return GameBarData.builder()
                    .playerBar(game.getFirstPlayerStonesList())
                    .playerKalah(game.getFirstPlayerKalah())
                    .oppositePlayerBar(game.getSecondPlayerStonesList())
                    .build();
        }
        else
        {
            return GameBarData.builder()
                    .playerBar(game.getSecondPlayerStonesList())
                    .playerKalah(game.getSecondPlayerKalah())
                    .oppositePlayerBar(game.getFirstPlayerStonesList())
                    .build();
        }
    }
}
