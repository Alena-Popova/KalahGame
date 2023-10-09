package sample.project.kalah.convertors;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class GameEntityToDataConverterTest extends InitialUnitTest
{
    @Mock
    private Converter<PlayerMoveEntity, PlayerMoveData> playerMoveEntityToDataConverter;

    @InjectMocks
    private GameEntityToDataConverter converter;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void convert_shouldThrowsIllegalArgumentException_whenSourceIsNull()
    {
        assertThrows(IllegalArgumentException.class, () -> converter.convert(null));
    }

    @Test
    void convert_shouldConvertGameEntityToData()
    {
        GameEntity gameEntity = TestData.getDefaultMockedGameEntity();
        PlayerMoveData playerMoveData = TestData.getDefaultMockedPlayerMoveData();
        List<PlayerMoveData> moves = Collections.singletonList(playerMoveData);

        when(playerMoveEntityToDataConverter.convert(any())).thenReturn(playerMoveData);

        GameConditionData result = converter.convert(gameEntity);

        assertEquals(TestData.getDefaultGameUUID(), result.getId());
        assertEquals(TestData.getInitStatus(), result.getStatus());
        assertEquals(TestData.getFullStonesBar(), result.getFirstPlayerStones());
        assertEquals(TestData.get10Stones(), result.getFirstPlayerKalah());
        assertEquals(TestData.getFullStonesBar(), result.getSecondPlayerStones());
        assertEquals(TestData.get10Stones(), result.getSecondPlayerKalah());
        assertEquals(TestData.getActivePlayersList(), result.getActivePlayers());
        assertEquals(moves, result.getMoves());
        assertEquals(TestData.getFirstPlayer(), result.getWinner());
    }

}