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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class FirstMoveRuleCheckerTest
{

    @Mock
    private GameUtil gameUtil;

    @InjectMocks
    private FirstMoveRuleChecker ruleChecker;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void apply_shouldReturnTrueForFirstPlayerFirstMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);

        when(nextMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(nextMove.isPlayerSide()).thenReturn(Boolean.TRUE);

        when(gameUtil.getLastMove(game.getMoves())).thenReturn(Optional.empty());

        boolean result = ruleChecker.apply(game, nextMove);

        assertTrue(result);
    }

    @Test
    void apply_shouldReturnForNotFirstMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);

        when(nextMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(nextMove.isPlayerSide()).thenReturn(Boolean.TRUE);

        when(gameUtil.getLastMove(game.getMoves())).thenReturn(Optional.of(new PlayerMoveEntity()));

        boolean result = ruleChecker.apply(game, nextMove);

        assertFalse(result);
    }

    @Test
    void apply_shouldReturnFalseForNonFirstPlayerFirstMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mock(PlayerMoveRequest.class);

        when(nextMove.getPlayer()).thenReturn(Player.SECOND_PLAYER);
        when(nextMove.isPlayerSide()).thenReturn(Boolean.TRUE);

        when(gameUtil.getLastMove(game.getMoves())).thenReturn(Optional.empty());

        boolean result = ruleChecker.apply(game, nextMove);

        assertFalse(result);
    }
}