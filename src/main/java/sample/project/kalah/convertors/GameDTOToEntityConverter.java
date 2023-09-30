package sample.project.kalah.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.utils.GameUtil;

@Component("gameDTOToEntityConverter")
public class GameDTOToEntityConverter implements Converter<GameDTO, GameEntity>
{
    @Value("${kalah.bar.numberOfHolesForEachParticipant}")
    private Integer numberOfHoles;

    @Autowired
    private GameUtil gameUtil;

    @Override
    public GameEntity convert(final GameDTO source)
    {
        String[] activePlayers = gameUtil.convertPlayerListToStringArray(source.getActivePlayers());

        GameEntity gameEntity = new GameEntity();
        gameEntity.setId(source.getId());
        gameEntity.setStatus(source.getStatus());
        gameEntity.setFirstPlayerStones(source.getFirstPlayerStones().toArray(new Integer[numberOfHoles]));
        gameEntity.setFirstPlayerKalah(source.getFirstPlayerKalah());
        gameEntity.setFirstPlayerStones(source.getFirstPlayerStones().toArray(new Integer[numberOfHoles]));
        gameEntity.setSecondPlayerKalah(source.getSecondPlayerKalah());
        gameEntity.setActivePlayers(activePlayers);
        return gameEntity;
    }
}
