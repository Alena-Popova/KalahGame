package sample.project.kalah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitialUnitTest
{

    public static class TestData
    {

        public static UUID getDefaultGameUUID()
        {
            return UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        }

        public static UUID getDefaultMoveUUID()
        {
            return UUID.fromString("250e8401-e29b-41d4-a716-446655440000");
        }

        public static GameStatus getInitStatus()
        {
            return GameStatus.INIT;
        }

        public static List<Integer> getFullStonesBar()
        {
            return new ArrayList<>(Collections.nCopies(getNumberOfHolesForEachParticipant(), getNumberOfStonesInEachHole()));
        }

        public static List<Integer> getEmptyStonesGar()
        {
            return new ArrayList<>(Collections.nCopies(getNumberOfHolesForEachParticipant(), 0));
        }

        public static Integer getTenStones()
        {
            return 10;
        }

        public static Integer getZeroStones()
        {
            return 0;
        }


        public static List<Player> getActivePlayersList()
        {
            return new ArrayList<>()
            {{
                add(Player.FIRST_PLAYER);
                add(Player.SECOND_PLAYER);
            }};
        }

        public static Integer getNumberOfHolesForEachParticipant()
        {
            return 6;
        }

        public static Integer getNumberOfStonesInEachHole()
        {
            return 6;
        }

        public static Integer getDefaultMoveNumber()
        {
            return 1;
        }

        public static Integer getDefaultStartingPit()
        {
            return 2;
        }

        public static Integer getDefaultEndingPit()
        {
            return 3;
        }


        public static GameEntity getDefaultMockedGameEntity()
        {
            GameEntity gameEntity = mock(GameEntity.class);
            List<PlayerMoveEntity> moves = Collections.singletonList(getDefaultMockedPlayerMoveEntity());

            when(gameEntity.getId()).thenReturn(getDefaultGameUUID());
            when(gameEntity.getStatus()).thenReturn(getInitStatus());
            when(gameEntity.getFirstPlayerStonesList()).thenReturn(getFullStonesBar());
            when(gameEntity.getFirstPlayerKalah()).thenReturn(getTenStones());
            when(gameEntity.getSecondPlayerStonesList()).thenReturn(getFullStonesBar());
            when(gameEntity.getSecondPlayerKalah()).thenReturn(getTenStones());
            when(gameEntity.getActivePlayersList()).thenReturn(getActivePlayersList());
            when(gameEntity.getMoves()).thenReturn(moves);
            when(gameEntity.getWinner()).thenReturn(Player.FIRST_PLAYER);

            return gameEntity;
        }

        public static GameBarData getDefaultMockedGameBarData()
        {
            GameBarData gameBarData = mock(GameBarData.class);

            when(gameBarData.getPlayerBar()).thenReturn(getFullStonesBar());
            when(gameBarData.getPlayerKalah()).thenReturn(getTenStones());
            when(gameBarData.getOppositePlayerBar()).thenReturn(getFullStonesBar());

            return gameBarData;
        }

        public static PlayerMoveEntity getDefaultMockedPlayerMoveEntity()
        {
            GameEntity gameEntity = mock(GameEntity.class);
            when(gameEntity.getId()).thenReturn(getDefaultGameUUID());

            PlayerMoveEntity playerMoveEntity = mock(PlayerMoveEntity.class);

            when(playerMoveEntity.getId()).thenReturn(getDefaultMoveUUID());
            when(playerMoveEntity.getGame()).thenReturn(gameEntity);
            when(playerMoveEntity.getMoveNumber()).thenReturn(getDefaultMoveNumber());
            when(playerMoveEntity.getPlayer()).thenReturn(Player.FIRST_PLAYER);
            when(playerMoveEntity.isStartsOnPlayerSide()).thenReturn(Boolean.TRUE);
            when(playerMoveEntity.getStartingPit()).thenReturn(getDefaultStartingPit());
            when(playerMoveEntity.isEndsOnPlayerSide()).thenReturn(Boolean.TRUE);
            when(playerMoveEntity.getEndingPit()).thenReturn(getDefaultEndingPit());
            when(playerMoveEntity.isEndsInPlayerKalah()).thenReturn(Boolean.FALSE);

            return playerMoveEntity;
        }

        public static PlayerMoveData getDefaultMockedPlayerMoveData()
        {
            PlayerMoveData playerMoveData = mock(PlayerMoveData.class);

            when(playerMoveData.getId()).thenReturn(getDefaultMoveUUID());
            when(playerMoveData.getGameId()).thenReturn(getDefaultGameUUID());
            when(playerMoveData.getMoveNumber()).thenReturn(getDefaultMoveNumber());
            when(playerMoveData.getPlayer()).thenReturn(Player.FIRST_PLAYER);
            when(playerMoveData.isStartsOnPlayerSide()).thenReturn(Boolean.TRUE);
            when(playerMoveData.getStartingPit()).thenReturn(getDefaultStartingPit());
            when(playerMoveData.isEndsOnPlayerSide()).thenReturn(Boolean.TRUE);
            when(playerMoveData.getEndingPit()).thenReturn(getDefaultEndingPit());
            when(playerMoveData.isEndsInPlayerKalah()).thenReturn(Boolean.FALSE);

            return playerMoveData;
        }

        public static GameEntity getMockedGameEntity(final UUID id,
                                                     final GameStatus status,
                                                     final List<Integer> firstPlayerStonesList,
                                                     final Integer firstPlayerKalah,
                                                     final List<Integer> secondPlayerStonesList,
                                                     final Integer secondPlayerKalah,
                                                     final List<Player> activePlayers,
                                                     final List<PlayerMoveEntity> movesEntity,
                                                     final Player winner)
        {
            GameEntity gameEntity = mock(GameEntity.class);

            when(gameEntity.getId()).thenReturn(id);
            when(gameEntity.getStatus()).thenReturn(status);
            when(gameEntity.getFirstPlayerStonesList()).thenReturn(firstPlayerStonesList);
            when(gameEntity.getFirstPlayerKalah()).thenReturn(firstPlayerKalah);
            when(gameEntity.getSecondPlayerStonesList()).thenReturn(secondPlayerStonesList);
            when(gameEntity.getSecondPlayerKalah()).thenReturn(secondPlayerKalah);
            when(gameEntity.getActivePlayersList()).thenReturn(activePlayers);
            when(gameEntity.getMoves()).thenReturn(movesEntity);
            when(gameEntity.getWinner()).thenReturn(winner);

            return gameEntity;
        }
    }
}
