package sample.project.kalah.services;

import java.util.List;

import org.springframework.stereotype.Service;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.services.interfaces.GameBarDataService;

@Service("gameBarDataService")
public class GameBarDataServiceImpl implements GameBarDataService
{
    @Override
    public GameBarData updateGameBarData(final PlayerMoveData nextMove, final GameBarData oldStateGameBarData)
    {
        List<Integer> playerBar = oldStateGameBarData.getPlayerBar();
        Integer playerKalah = oldStateGameBarData.getPlayerKalah();
        List<Integer> oppositePlayerBar = oldStateGameBarData.getOppositePlayerBar();

        int startingPit = nextMove.getStartingPit();
        if (nextMove.isStartsOnPlayerSide())
        {
            Integer stones = playerBar.get(startingPit);
            int startPitForUpdate = startingPit + 1;
            while (stones > 0)
            {
                for (int i = startPitForUpdate; i < playerBar.size() && stones > 0; i++)
                {
                    Integer temp = playerBar.get(i) + 1;
                    playerBar.set(i, temp);
                    stones--;
                    updatePlayerMoveDataIfMoveFinished(stones, nextMove, Boolean.FALSE, i, Boolean.FALSE);
                }
                startPitForUpdate = 0;

                if (stones > 0)
                {
                    playerKalah += 1;
                    stones--;
                    updatePlayerMoveDataIfMoveFinished(stones, nextMove, Boolean.TRUE, null, Boolean.TRUE);
                }
                for (int i = 0; i < oppositePlayerBar.size() && stones > 0; i++)
                {
                    Integer temp = oppositePlayerBar.get(i) + 1;
                    oppositePlayerBar.set(i, temp);
                    stones--;
                    updatePlayerMoveDataIfMoveFinished(stones, nextMove, Boolean.TRUE, i, Boolean.FALSE);
                }
            }
        }
        else
        {
            playerKalah += oppositePlayerBar.get(startingPit);
            oppositePlayerBar.set(startingPit, 0);
            nextMove.setEndsInPlayerKalah(Boolean.TRUE);
            updatePlayerMoveData(nextMove, Boolean.TRUE, null, Boolean.TRUE);
        }
        return GameBarData.builder()
                .playerBar(playerBar)
                .playerKalah(playerKalah)
                .oppositePlayerBar(oppositePlayerBar)
                .build();
    }

    private void updatePlayerMoveDataIfMoveFinished(Integer stones, PlayerMoveData nextMove, boolean endsOnPlayerSide, Integer endingPit, boolean endsInPlayerKalah)
    {
        if (stones == 0)
        {
            updatePlayerMoveData(nextMove, endsOnPlayerSide, endingPit, endsInPlayerKalah);
        }
    }

    private void updatePlayerMoveData(PlayerMoveData nextMove, boolean endsOnPlayerSide, Integer endingPit, boolean endsInPlayerKalah)
    {
        nextMove.setEndsOnPlayerSide(endsOnPlayerSide);
        nextMove.setEndingPit(endingPit);
        nextMove.setEndsInPlayerKalah(endsInPlayerKalah);
    }
}
