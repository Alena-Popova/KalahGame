package sample.project.kalah.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.exceptions.GameJoinException;
import sample.project.kalah.exceptions.GameNotFoundException;
import sample.project.kalah.exceptions.MoveNotAllowedException;
import sample.project.kalah.generators.interfaces.EntityGenerator;
import sample.project.kalah.repositories.interfaces.GameRepository;
import sample.project.kalah.services.interfaces.GameProcessorService;

@Service("gameProcessorService")
public class GameProcessorServiceImpl implements GameProcessorService
{
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private EntityGenerator<GameEntity> gameEntityGenerator;

    @Autowired
    private Converter<GameEntity, GameDTO> gameEntityToDTOConverter;

    @Override
    public GameDTO createGame()
    {
        GameEntity newGameEntity = gameEntityGenerator.generateDefaultEntity();
        GameEntity savedGameEntity = gameRepository.saveGame(newGameEntity);
        return gameEntityToDTOConverter.convert(savedGameEntity);
    }

    @Override
    public GameDTO getGame(final UUID gameId) throws GameNotFoundException
    {
        return gameRepository.getGameById(gameId)
                .map(gameEntityToDTOConverter::convert)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id '%s' not found", gameId)));
    }

    @Override
    public GameDTO joinGame(final UUID gameId, final String player) throws GameNotFoundException, GameJoinException
    {
        GameEntity gameEntity = gameRepository.getGameById(gameId)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id '%s' not found", gameId)));

        //TODO join user
        return gameEntityToDTOConverter.convert(gameEntity);
    }

    @Override
    public GameDTO finishGame(final UUID gameId, final String player) throws GameNotFoundException
    {
        GameEntity gameEntity = gameRepository.getGameById(gameId)
                .orElseThrow(() -> new GameNotFoundException(String.format("Game with id '%s' not found", gameId)));

        //TODO finish game
        return gameEntityToDTOConverter.convert(gameEntity);
    }

    @Override
    public GameDTO makeMove(final UUID gameId, final Player player, final Integer position) throws GameNotFoundException, MoveNotAllowedException
    {
        return null;
    }
}
