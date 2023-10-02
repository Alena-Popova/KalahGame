package sample.project.kalah.services;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.MoveNotAllowedException;
import sample.project.kalah.generators.PlayerMoveEntityGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.services.interfaces.GameWinnerService;
import sample.project.kalah.services.rules.interfaces.RuleChecker;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MoveHandlerServiceImplTest
{

    @Mock
    private RuleChecker firstMoveRuleChecker;

    @Mock
    private List<RuleChecker> rulesCheckers;

    @Mock
    private GameWinnerService gameWinnerService;

    @Mock
    private GameEntityService gameEntityService;

    @Mock
    private PlayerMoveEntityGenerator playerMoveEntityGenerator;

    @Mock
    private Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter;

    private MoveHandlerServiceImpl moveHandlerService;

    @BeforeEach
    void setUp()
    {
        rulesCheckers = Arrays.asList(firstMoveRuleChecker);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void makeMove_shouldThrowMoveNotAllowedException_whenMoveNotAllow()
    {
        UUID gameId = UUID.randomUUID();

        GameEntity game = mock(GameEntity.class);
        PlayerMoveRequest playerMoveRequest = mock(PlayerMoveRequest.class);

        when(gameEntityService.getGame(gameId)).thenReturn(game);
        when(rulesCheckers.stream().anyMatch(any())).thenReturn(Boolean.FALSE);

        assertThrows(MoveNotAllowedException.class,
                () -> moveHandlerService.makeMove(gameId, playerMoveRequest));

        verify(gameEntityService, times(0)).saveAndFlush(any());
    }

}
