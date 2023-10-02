package sample.project.kalah.services.rules;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.GameMainConfig;
import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.GameConfigurationService;
import sample.project.kalah.utils.GameUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class SamePlayerEmptyPitRuleCheckerTest
{

    @Mock
    private GameUtil gameUtil;
    @Mock
    private GameConfigurationService gameConfigurationService;

    @InjectMocks
    private SamePlayerEmptyPitRuleChecker ruleChecker;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void apply_samePlayerEmptyPit_returnsFalse_whenLastMoveIsNull()
    {
        GameEntity gameEntity = mock(GameEntity.class);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.FIRST_PLAYER, Boolean.FALSE, 7);

        when(gameUtil.getLastMove(gameEntity.getMoves())).thenReturn(Optional.empty());

        boolean result = ruleChecker.apply(gameEntity, nextMove);

        assertFalse(result);
    }

    @Test
    void apply_samePlayerEmptyPit_returnsTrue()
    {
        Integer[] firstPlayerStones = new Integer[]{1, 0, 2, 3, 4, 5};
        PlayerMoveEntity lastMove = mockPlayerMoveEntity(Player.FIRST_PLAYER, 0);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.FIRST_PLAYER, Boolean.FALSE, 5);
        GameEntity gameEntity = mockGameEntity(firstPlayerStones, firstPlayerStones, Collections.singletonList(lastMove));

        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);
        when(gameEntity.getFirstPlayerStones()).thenReturn(firstPlayerStones);
        when(gameUtil.getLastMove(gameEntity.getMoves())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(gameEntity, nextMove);

        assertTrue(result);
    }

    @Test
    void apply_samePlayerEmptyPit_returnsFalse_whenPitNextMoveIsNotOppositeLastMove()
    {
        Integer[] firstPlayerStones = new Integer[]{1, 0, 2, 3, 4, 5};
        PlayerMoveEntity lastMove = mockPlayerMoveEntity(Player.FIRST_PLAYER, 0);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.FIRST_PLAYER, Boolean.FALSE, 4);
        GameEntity gameEntity = mockGameEntity(firstPlayerStones, firstPlayerStones, Collections.singletonList(lastMove));

        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);
        when(gameEntity.getFirstPlayerStones()).thenReturn(firstPlayerStones);
        when(gameUtil.getLastMove(gameEntity.getMoves())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(gameEntity, nextMove);

        assertFalse(result);
    }

    @Test
    void apply_samePlayerEmptyPit_returnsFalse_whenIsSamePlayerSide()
    {
        Integer[] firstPlayerStones = new Integer[]{1, 0, 2, 3, 4, 5};
        PlayerMoveEntity lastMove = mockPlayerMoveEntity(Player.FIRST_PLAYER, 0);
        PlayerMoveRequest nextMove = mockPlayerMoveRequest(Player.FIRST_PLAYER, Boolean.TRUE, 5);
        GameEntity gameEntity = mockGameEntity(firstPlayerStones, firstPlayerStones, Collections.singletonList(lastMove));

        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(6);
        when(gameEntity.getFirstPlayerStones()).thenReturn(firstPlayerStones);
        when(gameUtil.getLastMove(gameEntity.getMoves())).thenReturn(Optional.of(lastMove));

        boolean result = ruleChecker.apply(gameEntity, nextMove);

        assertFalse(result);
    }


    private GameEntity mockGameEntity(Integer[] firstPlayerStones,
                                      Integer[] secondPlayerStones,
                                      List<PlayerMoveEntity> moves)
    {
        GameEntity gameEntity = mock(GameEntity.class);
        when(gameEntity.getStatus()).thenReturn(GameStatus.INIT);
        when(gameEntity.getFirstPlayerStones()).thenReturn(firstPlayerStones);
        when(gameEntity.getSecondPlayerStones()).thenReturn(secondPlayerStones);
        when(gameEntity.getMoves()).thenReturn(moves);
        return gameEntity;
    }

    private PlayerMoveEntity mockPlayerMoveEntity(Player player, int endingPit)
    {
        PlayerMoveEntity moveEntity = mock(PlayerMoveEntity.class);
        when(moveEntity.getPlayer()).thenReturn(player);
        when(moveEntity.getEndingPit()).thenReturn(endingPit);
        return moveEntity;
    }

    private PlayerMoveRequest mockPlayerMoveRequest(Player player, boolean playerSide, int startingPit)
    {
        PlayerMoveRequest playerMoveRequest = mock(PlayerMoveRequest.class);
        when(playerMoveRequest.getPlayer()).thenReturn(player);
        when(playerMoveRequest.isPlayerSide()).thenReturn(playerSide);
        when(playerMoveRequest.getStartingPit()).thenReturn(startingPit);
        return playerMoveRequest;
    }
}
