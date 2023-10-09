package sample.project.kalah.generators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.generators.interfaces.DefaultGenerator;
import sample.project.kalah.services.GameConfigurationService;

@Component("gameEntityGenerator")
public class GameEntityGeneratorImpl implements DefaultGenerator<GameEntity>
{
    private final DefaultGenerator<UUID> idGenerator;

    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public GameEntityGeneratorImpl(final DefaultGenerator<UUID> idGenerator,
                                   final GameConfigurationService gameConfigurationService)
    {
        this.idGenerator = idGenerator;
        this.gameConfigurationService = gameConfigurationService;
    }

    @Override
    public GameEntity generate()
    {
        GameEntity gameEntity = new GameEntity();

        gameEntity.setId(idGenerator.generate());
        gameEntity.setStatus(GameStatus.INIT);
        gameEntity.setFirstPlayerStonesList(getDefaultPlayerStones());
        gameEntity.setFirstPlayerKalah(0);
        gameEntity.setSecondPlayerStonesList(getDefaultPlayerStones());
        gameEntity.setSecondPlayerKalah(0);
        gameEntity.setActivePlayersList(Collections.emptyList());
        gameEntity.setMoves(Collections.emptyList());

        return gameEntity;
    }

    public List<Integer> getDefaultPlayerStones()
    {
        return new ArrayList<>(Collections.nCopies(gameConfigurationService.getNumberOfHolesForEachParticipant(), gameConfigurationService.getNumberOfStonesInEachHole()));
    }
}
