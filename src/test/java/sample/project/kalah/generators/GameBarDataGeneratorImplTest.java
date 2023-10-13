package sample.project.kalah.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameBarDataGeneratorImplTest extends InitialUnitTest
{

    @InjectMocks
    private GameBarDataGeneratorImpl generator;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void getGameBarData_shouldGenerateGameBarDataForFirstPlayer()
    {
        Player player = Player.FIRST_PLAYER;
        GameEntity gameEntity = mock(GameEntity.class);

        when(gameEntity.getFirstPlayerStonesList()).thenReturn(TestData.getFullStonesBar());
        when(gameEntity.getFirstPlayerKalah()).thenReturn(TestData.getTenStones());
        when(gameEntity.getSecondPlayerStonesList()).thenReturn(TestData.getEmptyStonesGar());

        GameBarData result = generator.generate(player, gameEntity);

        assertEquals(TestData.getFullStonesBar(), result.getPlayerBar());
        assertEquals(TestData.getTenStones(), result.getPlayerKalah());
        assertEquals(TestData.getEmptyStonesGar(), result.getOppositePlayerBar());
    }

    @Test
    void getGameBarData_shouldGenerateGameBarDataForSecondPlayer()
    {
        Player player = Player.SECOND_PLAYER;
        GameEntity gameEntity = mock(GameEntity.class);

        when(gameEntity.getSecondPlayerStonesList()).thenReturn(TestData.getFullStonesBar());
        when(gameEntity.getSecondPlayerKalah()).thenReturn(TestData.getTenStones());
        when(gameEntity.getFirstPlayerStonesList()).thenReturn(TestData.getEmptyStonesGar());

        GameBarData result = generator.generate(player, gameEntity);

        assertEquals(TestData.getFullStonesBar(), result.getPlayerBar());
        assertEquals(TestData.getTenStones(), result.getPlayerKalah());
        assertEquals(TestData.getEmptyStonesGar(), result.getOppositePlayerBar());
    }


}