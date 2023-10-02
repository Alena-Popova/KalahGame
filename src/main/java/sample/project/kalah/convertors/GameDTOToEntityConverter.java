package sample.project.kalah.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.utils.GameUtil;

@Component("gameDTOToEntityConverter")
public class GameDTOToEntityConverter implements Converter<GameConditionResponse, GameEntity>
{
    private final GameUtil gameUtil;

    @Autowired
    public GameDTOToEntityConverter(final GameUtil gameUtil)
    {
        this.gameUtil = gameUtil;
    }

    @Override
    public GameEntity convert(final GameConditionResponse source)
    {
        String[] activePlayers = gameUtil.convertPlayerListToStringArray(source.getActivePlayers());
        Integer[] firstPlayerStones = gameUtil.convertListToArrayForPlayerStones(source.getFirstPlayerStones());
        Integer[] secondPlayerStones = gameUtil.convertListToArrayForPlayerStones(source.getSecondPlayerStones());

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(source.getId());
        gameEntity.setStatus(source.getStatus());
        gameEntity.setFirstPlayerStones(firstPlayerStones);
        gameEntity.setFirstPlayerKalah(source.getFirstPlayerKalah());
        gameEntity.setSecondPlayerStones(secondPlayerStones);
        gameEntity.setSecondPlayerKalah(source.getSecondPlayerKalah());
        gameEntity.setActivePlayers(activePlayers);

        return gameEntity;
    }
}
