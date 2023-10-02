package sample.project.kalah.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.dto.PlayerMoveRequest;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.exceptions.MoveNotAllowedException;
import sample.project.kalah.generators.PlayerMoveEntityGenerator;
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

    private final PlayerMoveEntityGenerator playerMoveEntityGenerator;

    private final Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter;

    @Autowired
    public MoveHandlerServiceImpl(final List<RuleChecker> rulesCheckers, final GameWinnerService gameWinnerService, final GameEntityService gameEntityService, final PlayerMoveEntityGenerator playerMoveEntityGenerator, final Converter<GameEntity, GameConditionResponse> gameEntityToDTOConverter)
    {
        this.rulesCheckers = rulesCheckers;
        this.gameWinnerService = gameWinnerService;
        this.gameEntityService = gameEntityService;
        this.playerMoveEntityGenerator = playerMoveEntityGenerator;
        this.gameEntityToDTOConverter = gameEntityToDTOConverter;
    }

    @Override
    public GameConditionResponse makeMove(final UUID gameId, final PlayerMoveRequest nextMove) throws MoveNotAllowedException
    {
        GameEntity gameEntity = gameEntityService.getGame(gameId);

        if (!isNextMoveAllowed(gameEntity, nextMove))
        {
            throw new MoveNotAllowedException("This pit is not allowed for the player's move");
        }

        gameEntity = addNewMove(gameEntity, nextMove);
        gameEntity = updateGameStatusIfFinished(gameEntity);

        return gameEntityToDTOConverter.convert(gameEntity);
    }

    private boolean isNextMoveAllowed(GameEntity gameEntity, PlayerMoveRequest nextMove)
    {
        return rulesCheckers.stream().anyMatch(rule -> rule.apply(gameEntity, nextMove));
    }

    private GameEntity addNewMove(GameEntity game, PlayerMoveRequest nextMove)
    {
        PlayerMoveEntity playerMoveEntity = playerMoveEntityGenerator.generate(game, nextMove);

        List<PlayerMoveEntity> moves = new ArrayList<>(game.getMoves());
        moves.add(playerMoveEntity);
        game.setMoves(moves);

        return gameEntityService.saveAndFlush(game);
    }

    private GameEntity updateGameStatusIfFinished(GameEntity gameEntity)
    {
        Optional<Player> winnerOPT = gameWinnerService.determineWinner(gameEntity);
        if (winnerOPT.isEmpty())
        {
            return gameEntity;
        }
        gameEntity.setStatus(GameStatus.FINISHED);
        gameEntity.setVictoriousPlayer(winnerOPT.get());
        return gameEntityService.saveAndFlush(gameEntity);
    }
}
