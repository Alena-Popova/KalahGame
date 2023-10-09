package sample.project.kalah.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameActionNotAllowedException;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.generators.interfaces.DefaultGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.services.interfaces.GameActionService;

@Service("gameActionService")
public class GameActionServiceImpl implements GameActionService
{
    private static final Logger logger = LoggerFactory.getLogger(GameActionServiceImpl.class);

    private final GameEntityService gameEntityService;

    private final DefaultGenerator<GameEntity> gameEntityGenerator;

    private final Converter<GameEntity, GameConditionData> gameEntityToDataConverter;

    @Autowired
    public GameActionServiceImpl(final GameEntityService gameEntityService,
                                 final DefaultGenerator<GameEntity> gameEntityGenerator,
                                 final Converter<GameEntity, GameConditionData> gameEntityToDataConverter)
    {
        this.gameEntityService = gameEntityService;
        this.gameEntityGenerator = gameEntityGenerator;
        this.gameEntityToDataConverter = gameEntityToDataConverter;
    }

    @Autowired


    @Override
    public GameConditionData createGame()
    {
        GameEntity gameEntity = gameEntityGenerator.generate();
        gameEntity = gameEntityService.saveAndFlush(gameEntity);
        return gameEntityToDataConverter.convert(gameEntity);
    }

    @Override
    public GameConditionData getGame(final UUID gameId) throws GameNotFoundException
    {
        Assert.notNull(gameId, "gameId cannot be null");

        GameEntity gameEntity = gameEntityService.getGame(gameId);
        return gameEntityToDataConverter.convert(gameEntity);
    }

    @Override
    public GameConditionData joinGame(final UUID gameId, final String playerIdentifier) throws GameNotFoundException, GameActionNotAllowedException
    {
        Assert.notNull(gameId, "gameId cannot be null");
        Assert.notNull(playerIdentifier, "playerIdentifier cannot be null");

        GameEntity gameEntity = gameEntityService.getGame(gameId);

        checkGameStatusNotFinished(gameEntity);

        Player player = Player.valueOf(playerIdentifier);
        List<Player> activePlayers = gameEntity.getActivePlayersList();

        checkAddNewActivePlayer(player, activePlayers, gameId);

        activePlayers.add(player);
        gameEntity.setActivePlayersList(activePlayers);
        gameEntity = gameEntityService.saveAndFlush(gameEntity);
        return gameEntityToDataConverter.convert(gameEntity);
    }

    private void checkGameStatusNotFinished(GameEntity gameEntity)
    {
        if (GameStatus.FINISHED.equals(gameEntity.getStatus()))
        {
            logger.error("Unable to join a game that has finished, game id '{}'", gameEntity.getId());
            throw new GameActionNotAllowedException("Unable to join a game that has finished");
        }
    }

    private void checkAddNewActivePlayer(Player player, List<Player> activePlayers, UUID gameId)
    {
        if (activePlayers.contains(player))
        {
            logger.error("This player '{}' has already joined the game '{}'", player, gameId);
            throw new GameActionNotAllowedException("This player has already joined the game");
        }
    }

    @Override
    public GameConditionData finishGame(final UUID gameId) throws GameNotFoundException
    {
        Assert.notNull(gameId, "gameId cannot be null");

        GameEntity gameEntity = gameEntityService.getGame(gameId);
        if (!GameStatus.FINISHED.equals(gameEntity.getStatus()))
        {
            gameEntity.setStatus(GameStatus.FINISHED);
            gameEntity = gameEntityService.saveAndFlush(gameEntity);
        }
        return gameEntityToDataConverter.convert(gameEntity);
    }
}
