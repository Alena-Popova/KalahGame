package sample.project.kalah.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.exceptions.MoveNotAllowedException;
import sample.project.kalah.services.interfaces.MoveActionService;

@Service("moveActionService")
public class MoveActionServiceImpl implements MoveActionService
{
    @Value("${kalah.bar.numberOfStonesInEachHole}")
    private Integer numberOfStones;

    @Value("${kalah.bar.numberOfHolesForEachParticipant}")
    private Integer numberOfHoles;


    @Override
    public GameEntity makeMove(final PlayerMoveEntity move, final GameEntity game) throws MoveNotAllowedException
    {
        //todo implementation
        return null;
    }

    @Override
    public boolean isMoveAllowed(final PlayerMoveEntity move, final GameEntity game) throws MoveNotAllowedException
    {
        //todo implementation
        return false;
    }

    @Override
    public Optional<Player> determineWinner(final GameEntity game)
    {
        int winningThreshold = (numberOfStones * numberOfHoles) + 1;

        if (game.getFirstPlayerKalah() >= winningThreshold)
        {
            return Optional.of(Player.FIRST_PLAYER);
        }

        if (game.getSecondPlayerKalah() >= winningThreshold)
        {
            return Optional.of(Player.SECOND_PLAYER);
        }

        int firstPlayerSumStones = sumStones(game.getFirstPlayerStones());
        int secondPlayerSumStones = sumStones(game.getSecondPlayerStones());

        if (firstPlayerSumStones == 0 || secondPlayerSumStones == 0)
        {
            int firstPlayerResult = game.getFirstPlayerKalah() + firstPlayerSumStones;
            int secondPlayerResult = game.getSecondPlayerKalah() + secondPlayerSumStones;
            return Optional.of(firstPlayerResult > secondPlayerResult ? Player.FIRST_PLAYER : Player.SECOND_PLAYER);
        }

        return Optional.empty();
    }

    private int sumStones(Integer[] stones)
    {
        int sum = 0;
        for (Integer stone : stones)
        {
            if (stone != null)
            {
                sum += stone;
            }
        }
        return sum;
    }
}
