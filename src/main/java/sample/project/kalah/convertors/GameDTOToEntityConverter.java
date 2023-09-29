package sample.project.kalah.convertors;

import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.sql.GameEntity;

@Component("gameDTOToEntityConverter")
public class GameDTOToEntityConverter implements Converter<GameDTO, GameEntity>
{
    @Override
    public GameEntity convert(final GameDTO source)
    {
        return null;
    }
}
