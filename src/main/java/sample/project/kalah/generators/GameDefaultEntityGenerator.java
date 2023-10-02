package sample.project.kalah.generators;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.generators.interfaces.DefaultEntityGenerator;
import sample.project.kalah.utils.GameUtil;

@Component("gameDefaultEntityGenerator")
public class GameDefaultEntityGenerator implements DefaultEntityGenerator<GameEntity>
{
    private final GameUtil gameUtil;

    @Autowired
    public GameDefaultEntityGenerator(final GameUtil gameUtil)
    {
        this.gameUtil = gameUtil;
    }

    @Override
    public GameEntity generate()
    {
        final UUID id = new UUID(System.currentTimeMillis(), System.currentTimeMillis());

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(id);
        gameEntity.setStatus(GameStatus.INIT);
        gameEntity.setFirstPlayerStones(gameUtil.getDefaultPlayerStonesArray());
        gameEntity.setFirstPlayerKalah(0);
        gameEntity.setSecondPlayerStones(gameUtil.getDefaultPlayerStonesArray());
        gameEntity.setSecondPlayerKalah(0);
        return gameEntity;
    }
}
