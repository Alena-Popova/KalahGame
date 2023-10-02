package sample.project.kalah.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameActionNotAllowedException;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.generators.interfaces.DefaultEntityGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.utils.GameUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GameActionServiceImplTest
{

    @Mock
    private GameUtil gameUtil;

    @Mock
    private GameEntityService gameEntityService;

    @Mock
    private DefaultEntityGenerator<GameEntity> gameDefaultEntityGenerator;

    @Mock
    private Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter;

    @InjectMocks
    private GameActionServiceImpl gameActionService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createGame_shouldReturnValidGameConditionResponse()
    {
        GameEntity gameEntity = mock(GameEntity.class);
        GameConditionResponse gameConditionResponse = mock(GameConditionResponse.class);

        when(gameDefaultEntityGenerator.generate()).thenReturn(gameEntity);
        when(gameEntityService.saveAndFlush(gameEntity)).thenReturn(gameEntity);
        when(gameEntityToDTOConverter.convert(gameEntity)).thenReturn(gameConditionResponse);

        GameConditionResponse result = gameActionService.createGame();

        assertNotNull(result);
        verify(gameDefaultEntityGenerator, times(1)).generate();
        verify(gameEntityService, times(1)).saveAndFlush(gameEntity);
        verify(gameEntityToDTOConverter, times(1)).convert(gameEntity);
        assertEquals(gameConditionResponse, result);
    }

    @Test
    void getGame_shouldReturnValidGameConditionResponse() throws GameNotFoundException
    {
        UUID gameId = UUID.randomUUID();
        GameEntity gameEntity = mock(GameEntity.class);
        GameConditionResponse gameConditionResponse = mock(GameConditionResponse.class);

        when(gameEntityService.getGame(gameId)).thenReturn(gameEntity);
        when(gameEntityToDTOConverter.convert(gameEntity)).thenReturn(gameConditionResponse);

        GameConditionResponse result = gameActionService.getGame(gameId);

        assertNotNull(result);
        verify(gameEntityService, times(1)).getGame(gameId);
        verify(gameEntityToDTOConverter, times(1)).convert(gameEntity);
        assertEquals(gameConditionResponse, result);
    }

    @Test
    void joinGame_shouldReturnValidGameConditionResponse() throws GameNotFoundException, GameActionNotAllowedException
    {
        UUID gameId = UUID.randomUUID();
        Player joinPlayer = Player.FIRST_PLAYER;

        GameEntity gameEntity = mock(GameEntity.class);
        GameConditionResponse gameConditionResponse = mock(GameConditionResponse.class);

        List<Player> activePlayers = new ArrayList<>()
        {{
            add(Player.SECOND_PLAYER);
        }};
        String[] activePlayersArray = {Player.SECOND_PLAYER.toString()};

        when(gameEntity.getStatus()).thenReturn(GameStatus.INIT);
        when(gameEntity.getActivePlayers()).thenReturn(activePlayersArray);

        when(gameUtil.convertStringArrayToPlayerList(gameEntity.getActivePlayers())).thenReturn(activePlayers);
        when(gameUtil.convertPlayerListToStringArray(activePlayers)).thenReturn(activePlayersArray);
        when(gameEntityService.getGame(gameId)).thenReturn(gameEntity);
        when(gameEntityService.saveAndFlush(gameEntity)).thenReturn(gameEntity);
        when(gameEntityToDTOConverter.convert(gameEntity)).thenReturn(gameConditionResponse);

        GameConditionResponse result = gameActionService.joinGame(gameId, joinPlayer.toString());

        assertNotNull(result);
        assertEquals(gameConditionResponse, result);
    }

    @Test
    void joinGame_shouldThrowGameActionNotAllowedException_whenJoinSamePlayer() throws GameNotFoundException, GameActionNotAllowedException
    {
        UUID gameId = UUID.randomUUID();

        GameEntity gameEntity = mock(GameEntity.class);
        GameConditionResponse gameConditionResponse = mock(GameConditionResponse.class);

        List<Player> activePlayers = new ArrayList<>()
        {{
            add(Player.FIRST_PLAYER);
        }};
        String[] activePlayersArray = {Player.FIRST_PLAYER.toString()};

        when(gameEntity.getStatus()).thenReturn(GameStatus.INIT);
        when(gameEntity.getActivePlayers()).thenReturn(activePlayersArray);

        when(gameUtil.convertStringArrayToPlayerList(gameEntity.getActivePlayers())).thenReturn(activePlayers);
        when(gameUtil.convertPlayerListToStringArray(activePlayers)).thenReturn(activePlayersArray);
        when(gameEntityService.getGame(gameId)).thenReturn(gameEntity);
        when(gameEntityService.saveAndFlush(gameEntity)).thenReturn(gameEntity);
        when(gameEntityToDTOConverter.convert(gameEntity)).thenReturn(gameConditionResponse);

        assertThrows(GameActionNotAllowedException.class, () -> gameActionService.joinGame(gameId, Player.FIRST_PLAYER.toString()));

    }

    @Test
    void joinGame_shouldThrowGameActionNotAllowedException_whenGameIsFinished() throws GameNotFoundException
    {
        UUID gameId = UUID.randomUUID();
        GameEntity gameEntity = mock(GameEntity.class);

        when(gameEntityService.getGame(gameId)).thenReturn(gameEntity);
        when(gameEntity.getStatus()).thenReturn(GameStatus.FINISHED);

        assertThrows(GameActionNotAllowedException.class, () -> gameActionService.joinGame(gameId, Player.FIRST_PLAYER.toString()));
    }

    @Test
    void finishGame_shouldReturnValidGameConditionResponse() throws GameNotFoundException
    {
        UUID gameId = UUID.randomUUID();
        GameEntity gameEntity = mock(GameEntity.class);
        GameConditionResponse gameConditionResponse = mock(GameConditionResponse.class);

        when(gameEntity.getStatus()).thenReturn(GameStatus.INIT);

        when(gameEntityService.getGame(gameId)).thenReturn(gameEntity);
        when(gameEntityService.saveAndFlush(gameEntity)).thenReturn(gameEntity);
        when(gameEntityToDTOConverter.convert(gameEntity)).thenReturn(gameConditionResponse);

        GameConditionResponse result = gameActionService.finishGame(gameId);

        assertNotNull(result);
        assertEquals(gameConditionResponse, result);
        verify(gameEntity, times(1)).setStatus(GameStatus.FINISHED);
        verify(gameEntityService, times(1)).saveAndFlush(gameEntity);
    }
}