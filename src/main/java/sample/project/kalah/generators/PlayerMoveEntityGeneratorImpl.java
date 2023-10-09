package sample.project.kalah.generators;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.generators.interfaces.DefaultGenerator;
import sample.project.kalah.generators.interfaces.PlayerMoveEntityGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.utils.GameUtil;

@Component("playerMoveEntityGenerator")
public class PlayerMoveEntityGeneratorImpl implements PlayerMoveEntityGenerator
{
    private final DefaultGenerator<UUID> idGenerator;

    private final GameEntityService gameEntityService;

    @Autowired
    public PlayerMoveEntityGeneratorImpl(final DefaultGenerator<UUID> idGenerator,
                                         final GameEntityService gameEntityService)
    {
        this.idGenerator = idGenerator;
        this.gameEntityService = gameEntityService;
    }

    public PlayerMoveEntity generate(final PlayerMoveData nextMove)
    {
        PlayerMoveEntity moveEntity = new PlayerMoveEntity();

        GameEntity gameEntity = gameEntityService.getGame(nextMove.getGameId());

        Optional<PlayerMoveEntity> lastMoveOPT = GameUtil.getLastMove(gameEntity.getMoves());
        final int moveNumber = lastMoveOPT
                .map(playerMoveEntity -> playerMoveEntity.getMoveNumber() + 1)
                .orElse(1);

        moveEntity.setId(idGenerator.generate());
        moveEntity.setGame(gameEntity);
        moveEntity.setMoveNumber(moveNumber);
        moveEntity.setPlayer(nextMove.getPlayer());
        moveEntity.setStartsOnPlayerSide(nextMove.isStartsOnPlayerSide());
        moveEntity.setStartingPit(nextMove.getStartingPit());
        moveEntity.setEndsOnPlayerSide(nextMove.isEndsOnPlayerSide());
        moveEntity.setEndingPit(nextMove.getEndingPit());
        moveEntity.setEndsInPlayerKalah(nextMove.isEndsInPlayerKalah());

        return moveEntity;
    }
}
