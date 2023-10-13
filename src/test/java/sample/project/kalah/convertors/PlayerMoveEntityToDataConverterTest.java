package sample.project.kalah.convertors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import sample.project.kalah.InitialUnitTest;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

class PlayerMoveEntityToDataConverterTest extends InitialUnitTest
{

    @InjectMocks
    private PlayerMoveEntityToDataConverter converter;

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
    void convert_shouldConvertPlayerMoveEntityToData()
    {
        PlayerMoveEntity playerMoveEntity = TestData.getDefaultMockedPlayerMoveEntity();

        PlayerMoveData result = converter.convert(playerMoveEntity);

        assertEquals(TestData.getDefaultMoveUUID(), result.getId());
        assertEquals(TestData.getDefaultGameUUID(), result.getGameId());
        assertEquals(TestData.getDefaultMoveNumber(), result.getMoveNumber());
        assertEquals(Player.FIRST_PLAYER, result.getPlayer());
        assertEquals(Boolean.TRUE, result.isStartsOnPlayerSide());
        assertEquals(TestData.getDefaultStartingPit(), result.getStartingPit());
        assertEquals(Boolean.TRUE, result.isEndsOnPlayerSide());
        assertEquals(TestData.getDefaultEndingPit(), result.getEndingPit());
        assertEquals(Boolean.FALSE, result.isEndsInPlayerKalah());
    }

}