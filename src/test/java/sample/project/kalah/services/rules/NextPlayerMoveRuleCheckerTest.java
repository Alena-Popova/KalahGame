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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class NextPlayerMoveRuleCheckerTest
{
    @Mock
    private GameUtil gameUtil;

    @InjectMocks
    private NextPlayerMoveRuleChecker ruleChecker;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void apply_shouldReturnTrueForNextPlayerMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.SECOND_PLAYER, Boolean.TRUE, Boolean.FALSE);
        PlayerMoveEntity lastMove = mock(PlayerMoveEntity.class);

        when(lastMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(gameUtil.getLastMove(game.getMoves())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(game, nextMove);

        assertTrue(result);
    }

    @Test
    void apply_shouldReturnFalseForSamePlayerMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.FIRST_PLAYER, Boolean.TRUE, Boolean.FALSE);
        PlayerMoveEntity lastMove = mock(PlayerMoveEntity.class);

        when(lastMove.getPlayer()).thenReturn(Player.FIRST_PLAYER);
        when(gameUtil.getLastMove(game.getMoves())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(game, nextMove);

        assertFalse(result);
    }

    @Test
    void apply_shouldReturnFalseForNoLastMove()
    {
        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.SECOND_PLAYER, Boolean.TRUE, Boolean.FALSE);

        when(gameUtil.getLastMove(any())).thenReturn(Optional.empty());

        boolean result = ruleChecker.apply(game, nextMove);

        assertFalse(result);
    }

    private PlayerMoveRequest mockPlayerMoveRequest(Player player, boolean isPlayerSide, boolean isStartsInPlayerKalah)
    {
        PlayerMoveRequest playerMoveRequest = mock(PlayerMoveRequest.class);
        when(playerMoveRequest.getPlayer()).thenReturn(player);
        when(playerMoveRequest.isPlayerSide()).thenReturn(isPlayerSide);
        when(playerMoveRequest.isStartsInPlayerKalah()).thenReturn(isStartsInPlayerKalah);
        return playerMoveRequest;
    }
}