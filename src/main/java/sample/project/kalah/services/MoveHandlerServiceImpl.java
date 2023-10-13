package sample.project.kalah.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameBarData;
import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.exceptions.MoveNotAllowedException;
import sample.project.kalah.generators.interfaces.GameBarDataGenerator;
import sample.project.kalah.generators.interfaces.PlayerMoveEntityGenerator;
import sample.project.kalah.services.entity.interfaces.GameEntityService;
import sample.project.kalah.services.interfaces.GameWinnerService;
import sample.project.kalah.services.interfaces.MoveActionService;
import sample.project.kalah.services.rules.interfaces.RuleChecker;

@Service("moveActionService")
public class MoveHandlerServiceImpl implements MoveActionService
{
    private final List<RuleChecker> rulesCheckers;
    private final GameWinnerService gameWinnerService;
    private final GameEntityService gameEntityService;
    private final GameBarDataServiceImpl gameBarDataService;
    private final GameBarDataGenerator gameBarDataGenerator;
    private final PlayerMoveEntityGenerator playerMoveEntityGenerator;
    private final Converter<GameEntity, GameConditionData> gameEntityToDataConverter;

    @Autowired
    public MoveHandlerServiceImpl(final List<RuleChecker> rulesCheckers,
                                  final GameWinnerService gameWinnerService,
                                  final GameEntityService gameEntityService,
                                  final GameBarDataServiceImpl gameBarDataService,
                                  final GameBarDataGenerator gameBarDataGenerator,
                                  final PlayerMoveEntityGenerator playerMoveEntityGenerator,
                                  final Converter<GameEntity, GameConditionData> gameEntityToDataConverter)
    {
        this.rulesCheckers = rulesCheckers;
        this.gameWinnerService = gameWinnerService;
        this.gameEntityService = gameEntityService;
        this.gameBarDataService = gameBarDataService;
        this.gameBarDataGenerator = gameBarDataGenerator;
        this.playerMoveEntityGenerator = playerMoveEntityGenerator;
        this.gameEntityToDataConverter = gameEntityToDataConverter;
    }

    @Override
    public GameConditionData makeMove(final UUID gameId, final PlayerMoveData nextMove) throws MoveNotAllowedException
    {
        Assert.notNull(gameId, "gameId cannot be null");
        Assert.notNull(nextMove, "nextMove cannot be null");

        GameEntity gameEntity = gameEntityService.getGame(gameId);

        checkNextMoveAllowed(gameEntity, nextMove);

        gameEntity = addNewMove(gameEntity, nextMove);
        gameEntity = updateGameStatusIfFinished(gameEntity);

        return gameEntityToDataConverter.convert(gameEntity);
    }

    private void checkNextMoveAllowed(GameEntity gameEntity, PlayerMoveData nextMove)
    {
        boolean isNotAllowed = rulesCheckers.stream().noneMatch(rule -> rule.apply(gameEntity, nextMove));
        if (isNotAllowed)
        {
            throw new MoveNotAllowedException("This pit is not allowed for the player's move");
        }
    }

    private GameEntity addNewMove(GameEntity gameEntity, PlayerMoveData nextMoveData)
    {
        GameBarData oldStateGameBarData = gameBarDataGenerator.generate(nextMoveData.getPlayer(), gameEntity);
        GameBarData updatedStateGameBarData = gameBarDataService.updateGameBarData(nextMoveData, oldStateGameBarData);
        PlayerMoveEntity nextPlayerMoveEntity = playerMoveEntityGenerator.generate(nextMoveData);

        return gameEntityService.updateGameEntity(updatedStateGameBarData, nextPlayerMoveEntity, gameEntity);
    }


    private GameEntity updateGameStatusIfFinished(GameEntity gameEntity)
    {
        Optional<Player> winnerOPT = gameWinnerService.determineWinner(gameEntity);
        if (winnerOPT.isEmpty())
        {
            return gameEntity;
        }
        gameEntity.setStatus(GameStatus.FINISHED);
        gameEntity.setWinner(winnerOPT.get());
        return gameEntityService.saveAndFlush(gameEntity);
    }
}
