package sample.project.kalah.convertors;

import org.springframework.stereotype.Component;

import sample.project.kalah.convertors.interfaces.Converter;
import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.entity.sql.GameEntity;

@Component("gameEntityToDTOConverter")
public class GameEntityToDTOConverter implements Converter<GameEntity, GameDTO>
{
    @Override
    public GameDTO convert(final GameEntity source)
    {
        return null;
    }
}
