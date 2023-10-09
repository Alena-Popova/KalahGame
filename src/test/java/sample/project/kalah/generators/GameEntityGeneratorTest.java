package sample.project.kalah.generators;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.utils.GameUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

class GameEntityGeneratorTest
{

    @InjectMocks
    private GameEntityGeneratorImpl entityGenerator;

    @BeforeEach
    void setUp()
    {
        initMocks(this);
    }

    @Test
    void generate_shouldCreateGameEntityWithDefaultStonesArray()
    {
        int numberOfHoles = 6;
        Integer[] defaultPlayerStonesArray = new Integer[numberOfHoles];


        GameEntity result = entityGenerator.generate();

        assertNotNull(result);
        // assertEquals(numberOfHoles, result.getFirstPlayerStones().length);
        // assertEquals(numberOfHoles, result.getSecondPlayerStones().length);
        assertEquals(GameStatus.INIT, result.getStatus());
        assertEquals(0, result.getFirstPlayerKalah());
        assertEquals(0, result.getSecondPlayerKalah());
    }
}