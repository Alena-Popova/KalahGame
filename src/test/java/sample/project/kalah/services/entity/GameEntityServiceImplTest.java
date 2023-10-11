package sample.project.kalah.services.entity;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.repositories.interfaces.GameEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

}