package sample.project.kalah.generators;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.generators.interfaces.DefaultGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerMoveEntityGeneratorImplTest extends InitialUnitTest
{

    @Mock
    private DefaultGenerator<UUID> idGenerator;

    @Mock
    private GameEntityService gameEntityService;

    @InjectMocks
    private PlayerMoveEntityGeneratorImpl generator;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }


    @Test
    void generate_shouldGenerateGameEntity()
    {
        GameEntity gameEntity = TestData.getDefaultMockedGameEntity();

        PlayerMoveData playerMoveData = TestData.getDefaultMockedPlayerMoveData();

        when(idGenerator.generate()).thenReturn(TestData.getDefaultMoveUUID());
        when(gameEntityService.getGame(TestData.getDefaultGameUUID())).thenReturn(gameEntity);

        PlayerMoveEntity result = generator.generate(playerMoveData);

        assertEquals(TestData.getDefaultMoveUUID(), result.getId());
        assertEquals(TestData.getDefaultMoveNumber() + 1, result.getMoveNumber());
        assertEquals(Player.FIRST_PLAYER, result.getPlayer());
        assertEquals(Boolean.TRUE, result.isStartsOnPlayerSide());
        assertEquals(TestData.getDefaultStartingPit(), result.getStartingPit());
        assertEquals(Boolean.TRUE, result.isEndsOnPlayerSide());
        assertEquals(TestData.getDefaultEndingPit(), result.getEndingPit());
        assertEquals(Boolean.FALSE, result.isEndsInPlayerKalah());
    }

}