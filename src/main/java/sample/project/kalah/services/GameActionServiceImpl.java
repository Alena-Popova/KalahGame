package sample.project.kalah.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameActionNotAllowedException;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.generators.interfaces.DefaultEntityGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.services.interfaces.GameActionService;
import sample.project.kalah.utils.GameUtil;

@Service("gameActionService")
public class GameActionServiceImpl implements GameActionService
{
    private static final Logger logger = LoggerFactory.getLogger(GameActionServiceImpl.class);

    private final GameUtil gameUtil;
    private final GameEntityService gameEntityService;

    private final DefaultEntityGenerator<GameEntity> gameDefaultEntityGenerator;

    private final Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter;

    public GameActionServiceImpl(final GameUtil gameUtil, final GameEntityService gameEntityService, final DefaultEntityGenerator<GameEntity> gameDefaultEntityGenerator, final Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter)
    {
        this.gameUtil = gameUtil;
        this.gameEntityService = gameEntityService;
        this.gameDefaultEntityGenerator = gameDefaultEntityGenerator;
        this.gameEntityToDTOConverter = gameEntityToDTOConverter;
    }

    @Autowired


    @Override
    public GameConditionResponse createGame()
    {
        GameEntity gameEntity = gameDefaultEntityGenerator.generate();
        gameEntity = gameEntityService.saveAndFlush(gameEntity);
        return gameEntityToDTOConverter.convert(gameEntity);
    }

    @Override
    public GameConditionResponse getGame(final UUID gameId) throws GameNotFoundException
    {
        GameEntity gameEntity = gameEntityService.getGame(gameId);
        return gameEntityToDTOConverter.convert(gameEntity);
    }

    @Override
    public GameConditionResponse joinGame(final UUID gameId, final String playerIdentifier) throws GameNotFoundException, GameActionNotAllowedException
    {
        GameEntity gameEntity = gameEntityService.getGame(gameId);

        checkGameStatusNotFinished(gameEntity);

        Player player = Player.valueOf(playerIdentifier);
        List<Player> activePlayers = gameUtil.convertStringArrayToPlayerList(gameEntity.getActivePlayers());

        checkAddNewActivePlayer(player, activePlayers, gameId);

        activePlayers.add(player);
        gameEntity.setActivePlayers(gameUtil.convertPlayerListToStringArray(activePlayers));
        gameEntity = gameEntityService.saveAndFlush(gameEntity);
        return gameEntityToDTOConverter.convert(gameEntity);
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
    public GameConditionResponse finishGame(final UUID gameId) throws GameNotFoundException
    {
        GameEntity gameEntity = gameEntityService.getGame(gameId);
        if (!GameStatus.FINISHED.equals(gameEntity.getStatus()))
        {
            gameEntity.setStatus(GameStatus.FINISHED);
            gameEntity = gameEntityService.saveAndFlush(gameEntity);
        }
        return gameEntityToDTOConverter.convert(gameEntity);
    }
}
