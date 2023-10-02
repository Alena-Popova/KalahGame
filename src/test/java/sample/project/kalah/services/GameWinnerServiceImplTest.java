package sample.project.kalah.services;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sample.project.kalah.GameMainConfig;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameWinnerServiceImplTest
{
    @Mock
    private GameConfigurationService gameConfigurationService;
    @InjectMocks
    private GameWinnerServiceImpl gameWinnerService;
    private final Integer[] filledStonesPits = new Integer[]{6, 6, 6, 6, 6, 6};
    private final Integer[] emptyStonesPits = new Integer[]{0, 0, 0, 0, 0, 0};

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void determineWinner_shouldReturnFirstPlayer_whenFirstPlayerExceedsWinningThreshold()
    {
        GameEntity game = mock(GameEntity.class);

        when(game.getFirstPlayerKalah()).thenReturn(37);
        when(game.getFirstPlayerStones()).thenReturn(filledStonesPits);
        when(game.getSecondPlayerStones()).thenReturn(filledStonesPits);
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(6);
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);

        Optional<Player> winner = gameWinnerService.determineWinner(game);

        assertTrue(winner.isPresent());
        assertEquals(Player.FIRST_PLAYER, winner.get());
    }

    @Test
    void determineWinner_shouldReturnFirstPlayer_whenFirstPlayerNotExceedsWinningThreshold()
    {
        GameEntity game = mock(GameEntity.class);

        when(game.getFirstPlayerKalah()).thenReturn(36);
        when(game.getFirstPlayerStones()).thenReturn(filledStonesPits);
        when(game.getSecondPlayerStones()).thenReturn(filledStonesPits);
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(6);
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);

        Optional<Player> winner = gameWinnerService.determineWinner(game);

        assertFalse(winner.isPresent());
    }

    @Test
    void determineWinner_shouldReturnEmpty_whenNoPlayerExceedsWinningThreshold()
    {
        GameEntity game = mock(GameEntity.class);

        when(game.getFirstPlayerKalah()).thenReturn(0);
        when(game.getSecondPlayerKalah()).thenReturn(0);
        when(game.getFirstPlayerStones()).thenReturn(filledStonesPits);
        when(game.getSecondPlayerStones()).thenReturn(filledStonesPits);
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(6);
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);

        Optional<Player> winner = gameWinnerService.determineWinner(game);

        assertFalse(winner.isPresent());
    }

    @Test
    void determineWinner_shouldReturnFirstPlayer_whenFirstPlayerHasMoreStones()
    {
        GameEntity game = mock(GameEntity.class);

        when(game.getFirstPlayerKalah()).thenReturn(40);
        when(game.getSecondPlayerKalah()).thenReturn(20);
        when(game.getFirstPlayerStones()).thenReturn(emptyStonesPits);
        when(game.getSecondPlayerStones()).thenReturn(new Integer[]{2, 2, 2, 2, 2, 2});
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(6);
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);

        Optional<Player> winner = gameWinnerService.determineWinner(game);

        assertTrue(winner.isPresent());
        assertEquals(Player.FIRST_PLAYER, winner.get());
    }

    @Test
    void determineWinner_shouldReturnFirstPlayer_whenSecondPlayerHasMoreStones()
    {
        GameEntity game = mock(GameEntity.class);

        when(game.getFirstPlayerKalah()).thenReturn(20);
        when(game.getSecondPlayerKalah()).thenReturn(40);
        when(game.getFirstPlayerStones()).thenReturn(new Integer[]{2, 2, 2, 2, 2, 2});
        when(game.getSecondPlayerStones()).thenReturn(emptyStonesPits);
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(6);
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);

        Optional<Player> winner = gameWinnerService.determineWinner(game);

        assertTrue(winner.isPresent());
        assertEquals(Player.SECOND_PLAYER, winner.get());
    }
}