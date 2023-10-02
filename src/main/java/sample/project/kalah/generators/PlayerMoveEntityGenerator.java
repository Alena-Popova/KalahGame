package sample.project.kalah.generators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.utils.GameUtil;

@Component("playerMoveEntityGenerator")
public class PlayerMoveEntityGenerator
{
    private final GameUtil gameUtil;

    @Autowired
    public PlayerMoveEntityGenerator(final GameUtil gameUtil)
    {
        this.gameUtil = gameUtil;
    }

    public PlayerMoveEntity generate(final GameEntity game, final PlayerMoveRequest nextMove)
    {
        PlayerMoveEntity moveEntity = new PlayerMoveEntity();

        Optional<PlayerMoveEntity> lastMoveOPT = gameUtil.getLastMove(game.getMoves());
        int amount = lastMoveOPT.isEmpty() ? 0 : lastMoveOPT.get().getMoveNumber();

        moveEntity.setMoveNumber(amount + 1);
        moveEntity.setPlayer(nextMove.getPlayer());
        moveEntity.setStartingPit(nextMove.getStartingPit());
        moveEntity.setStartsInPlayerKalah(nextMove.isStartsInPlayerKalah());
        moveEntity.setPlayerSide(nextMove.isPlayerSide());

        updateGameBar(game, nextMove);

        return moveEntity;
    }

    private void updateGameBar(final GameEntity game, final PlayerMoveRequest nextMove)
    {
        boolean isFirstPlayer = Player.FIRST_PLAYER.equals(nextMove.getPlayer());

        Integer[] playerStones = isFirstPlayer ? game.getFirstPlayerStones() : game.getSecondPlayerStones();
        Integer playerKalah = isFirstPlayer ? game.getFirstPlayerKalah() : game.getSecondPlayerKalah();
        Integer[] nextPlayerStones = isFirstPlayer ? game.getSecondPlayerStones() : game.getFirstPlayerStones();
        Integer nextPlayerKalah = isFirstPlayer ? game.getSecondPlayerKalah() : game.getFirstPlayerKalah();

        if (nextMove.isPlayerSide())
        {
            makeClassicMove(nextMove.getStartingPit(), playerStones, playerKalah, nextPlayerStones);
        }
        else
        {
            makeEmptyPitMove(nextMove.getStartingPit(), playerKalah, nextPlayerStones);
        }

        if (isFirstPlayer)
        {
            game.setFirstPlayerStones(playerStones);
            game.setFirstPlayerKalah(playerKalah);
            game.setSecondPlayerStones(nextPlayerStones);
            game.setSecondPlayerKalah(nextPlayerKalah);
        }
        else
        {
            game.setSecondPlayerStones(playerStones);
            game.setSecondPlayerKalah(playerKalah);
            game.setFirstPlayerStones(nextPlayerStones);
            game.setFirstPlayerKalah(nextPlayerKalah);
        }
    }

    private void makeClassicMove(Integer startPit, Integer[] playerStones, Integer playerKalah, Integer[] nextPlayerStones)
    {
        Integer countOfStones = playerStones[startPit]; //TODO ERROR!
        playerStones[startPit] = 0;
        while (countOfStones > 0)
        {
            for (int i = startPit + 1; i < playerStones.length && countOfStones > 0; i++)
            {
                int newValue = playerStones[i] + 1;
                playerStones[i] = newValue;
                countOfStones--;
            }
            if (countOfStones > 0)
            {
                playerKalah++;
                countOfStones--;
            }
            for (int i = 0; i < nextPlayerStones.length && countOfStones > 0; i++)
            {
                int newValue = nextPlayerStones[i] + 1;
                nextPlayerStones[i] = newValue;
                countOfStones--;
            }
        }
    }

    private void makeEmptyPitMove(Integer startPit, Integer playerKalah, Integer[] nextPlayerStones)
    {
        Integer countOfStones = nextPlayerStones[startPit];
        nextPlayerStones[startPit] = 0;
        playerKalah += countOfStones;
    }
}
