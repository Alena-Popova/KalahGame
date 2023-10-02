package sample.project.kalah.services.rules;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.utils.GameUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SamePlayerKalahRuleCheckerTest
{

    @Mock
    private GameUtil gameUtil;

    @InjectMocks
    private SamePlayerKalahRuleChecker ruleChecker;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }


    @Test
    void apply_shouldReturnFalse_whenLastMoveIsEmpty()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);

        when(gameUtil.getLastMove(any())).thenReturn(Optional.empty());

        boolean result = ruleChecker.apply(game, nextMove);
        assertFalse(result);
    }

    @Test
    void apply_shouldReturnFalse_whenDifferentPlayerMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);
        PlayerMoveEntity lastMove = mock(PlayerMoveEntity.class);

        when(nextMove.getPlayer()).thenReturn(Player.SECOND_PLAYER);
        when(nextMove.isPlayerSide()).thenReturn(Boolean.TRUE);

        when(lastMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);

        when(gameUtil.getLastMove(any())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(game, nextMove);
        assertFalse(result);
    }

    @Test
    void apply_shouldReturnTrue_whenSamePlayerMoveAndKalahEnd()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);
        PlayerMoveEntity lastMove = mock(PlayerMoveEntity.class);

        when(nextMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(nextMove.isPlayerSide()).thenReturn(Boolean.TRUE);

        when(lastMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(lastMove.isEndsInPlayerKalah()).thenReturn(Boolean.TRUE);

        when(gameUtil.getLastMove(any())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(game, nextMove);
        assertTrue(result);
    }

}