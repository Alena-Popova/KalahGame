package sample.project.kalah.generators;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.generators.interfaces.DefaultGenerator;
import sample.project.kalah.services.GameConfigurationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameEntityGeneratorImplTest extends InitialUnitTest
{
    @Mock
    private DefaultGenerator<UUID> idGenerator;

    @Mock
    private GameConfigurationService gameConfigurationService;

    @InjectMocks
    private GameEntityGeneratorImpl generator;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void generate_shouldGenerateGameEntity()
    {
        when(idGenerator.generate()).thenReturn(TestData.getDefaultGameUUID());
        when(gameConfigurationService.getNumberOfHolesForEachParticipant()).thenReturn(TestData.getNumberOfHolesForEachParticipant());
        when(gameConfigurationService.getNumberOfStonesInEachHole()).thenReturn(TestData.getNumberOfStonesInEachHole());

        GameEntity result = generator.generate();

        assertEquals(TestData.getDefaultGameUUID(), result.getId());
        assertEquals(TestData.getInitStatus(), result.getStatus());
        assertEquals(TestData.getFullStonesBar(), result.getFirstPlayerStonesList());
        assertEquals(TestData.getZeroStones(), result.getFirstPlayerKalah());
        assertEquals(TestData.getFullStonesBar(), result.getSecondPlayerStonesList());
        assertEquals(TestData.getZeroStones(), result.getSecondPlayerKalah());
        assertTrue(result.getActivePlayersList().isEmpty());
        assertTrue(result.getMoves().isEmpty());
    }

}