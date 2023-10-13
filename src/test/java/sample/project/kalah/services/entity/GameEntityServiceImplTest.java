package sample.project.kalah.services.entity;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.repositories.interfaces.GameEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameEntityServiceImplTest extends InitialUnitTest
{
    @Mock
    private GameEntityRepository gameEntityRepository;

    @InjectMocks
    private GameEntityServiceImpl service;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }


    @Test
    void getGame_shouldGetGameById()
    {
        UUID id = TestData.getDefaultGameUUID();
        GameEntity expectedGameEntity = TestData.getDefaultMockedGameEntity();

        when(gameEntityRepository.findById(id)).thenReturn(Optional.of(expectedGameEntity));

        GameEntity actualGameEntity = service.getGame(id);

        assertEquals(expectedGameEntity, actualGameEntity);
    }

    @Test
    void getGame_shouldThrowGameNotFoundException_ifGameNotFound()
    {
        UUID id = TestData.getDefaultGameUUID();

        when(gameEntityRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.getGame(id));
    }

    @Test
    void saveAndFlush_shouldSaveAndFlushGameEntity()
    {
        GameEntity notSavedGameEntity = mock(GameEntity.class);
        GameEntity expectedGameEntity = TestData.getDefaultMockedGameEntity();

        when(gameEntityRepository.saveAndFlush(notSavedGameEntity)).thenReturn(expectedGameEntity);

        GameEntity actualGameEntity = service.saveAndFlush(notSavedGameEntity);

        assertEquals(expectedGameEntity, actualGameEntity);
    }

    @Test
    void updateGameEntity_shouldUpdateGameEntityForFirstPlayer()
    {
        GameBarData updatedGameBarData = mock(GameBarData.class);
        PlayerMoveEntity nextPlayerMoveEntity = mock(PlayerMoveEntity.class);
        GameEntity gameEntity = TestData.getDefaultMockedGameEntity();

        when(updatedGameBarData.getPlayerBar()).thenReturn(TestData.getFullStonesBar());
        when(updatedGameBarData.getPlayerKalah()).thenReturn(TestData.getTenStones());
        when(updatedGameBarData.getOppositePlayerBar()).thenReturn(TestData.getEmptyStonesGar());
        when(nextPlayerMoveEntity.getPlayer()).thenReturn(Player.FIRST_PLAYER);

        service.updateGameEntity(updatedGameBarData, nextPlayerMoveEntity, gameEntity);

        verify(gameEntity, Mockito.times(1)).setFirstPlayerStonesList(TestData.getFullStonesBar());
        verify(gameEntity, Mockito.times(1)).setFirstPlayerKalah(TestData.getTenStones());
        verify(gameEntity, Mockito.times(1)).setSecondPlayerStonesList(TestData.getEmptyStonesGar());
        verify(gameEntity, Mockito.times(1)).setMoves(any());
        verify(gameEntityRepository, Mockito.times(1)).saveAndFlush(gameEntity);
    }

    @Test
    void updateGameEntity_shouldUpdateGameEntityForSecondPlayer()
    {
        GameBarData updatedGameBarData = mock(GameBarData.class);
        PlayerMoveEntity nextPlayerMoveEntity = mock(PlayerMoveEntity.class);
        GameEntity gameEntity = TestData.getDefaultMockedGameEntity();

        when(updatedGameBarData.getPlayerBar()).thenReturn(TestData.getFullStonesBar());
        when(updatedGameBarData.getPlayerKalah()).thenReturn(TestData.getTenStones());
        when(updatedGameBarData.getOppositePlayerBar()).thenReturn(TestData.getEmptyStonesGar());
        when(nextPlayerMoveEntity.getPlayer()).thenReturn(Player.SECOND_PLAYER);

        service.updateGameEntity(updatedGameBarData, nextPlayerMoveEntity, gameEntity);

        verify(gameEntity, Mockito.times(1)).setSecondPlayerStonesList(TestData.getFullStonesBar());
        verify(gameEntity, Mockito.times(1)).setSecondPlayerKalah(TestData.getTenStones());
        verify(gameEntity, Mockito.times(1)).setFirstPlayerStonesList(TestData.getEmptyStonesGar());
        verify(gameEntity, Mockito.times(1)).setMoves(any());
        verify(gameEntityRepository, Mockito.times(1)).saveAndFlush(gameEntity);
    }

}