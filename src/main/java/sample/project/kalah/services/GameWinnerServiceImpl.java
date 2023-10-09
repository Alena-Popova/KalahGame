package sample.project.kalah.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.services.interfaces.GameWinnerService;

@Service("gameWinnerService")
public class GameWinnerServiceImpl implements GameWinnerService
{
    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public GameWinnerServiceImpl(final GameConfigurationService gameConfigurationService)
    {
        this.gameConfigurationService = gameConfigurationService;
    }

    @Override
    public Optional<Player> determineWinner(final GameEntity game)
    {
        int winningThreshold = (gameConfigurationService.getNumberOfStonesInEachHole() * gameConfigurationService.getNumberOfHolesForEachParticipant()) + 1;

        if (game.getFirstPlayerKalah() >= winningThreshold)
        {
            return Optional.of(Player.FIRST_PLAYER);
        }

        if (game.getSecondPlayerKalah() >= winningThreshold)
        {
            return Optional.of(Player.SECOND_PLAYER);
        }

        int firstPlayerSumStones = sumStones(game.getFirstPlayerStonesList());
        int secondPlayerSumStones = sumStones(game.getSecondPlayerStonesList());

        if (firstPlayerSumStones != 0 && secondPlayerSumStones != 0)
        {
            return Optional.empty();
        }

        return Optional.of(
                game.getFirstPlayerKalah() > game.getSecondPlayerKalah()
                        ? Player.FIRST_PLAYER
                        : Player.SECOND_PLAYER
        );
    }

    private int sumStones(List<Integer> stones)
    {
        return stones.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

}
