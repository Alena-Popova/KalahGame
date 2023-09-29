package sample.project.kalah.generators;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sample.project.kalah.entity.sql.GameEntity;
import sample.project.kalah.generators.interfaces.EntityGenerator;

@Component("gameEntityGenerator")
public class GameEntityGenerator implements EntityGenerator<GameEntity>
{
    @Value("${kalah.bar.numberOfStonesInEachHole}")
    private Integer numberOfStones;

    @Value("${kalah.bar.numberOfHolesForEachParticipant}")
    private Integer numberOfHoles;

    @Override
    public GameEntity generateDefaultEntity()
    {
        final UUID id = new UUID(System.currentTimeMillis(), System.currentTimeMillis());

        Integer[] firstPlayerStones = new Integer[numberOfHoles];
        Integer[] secondPlayerStones = new Integer[numberOfHoles];
        Arrays.fill(firstPlayerStones, numberOfStones);
        Arrays.fill(secondPlayerStones, numberOfStones);

        return GameEntity.builder().id(id)
                .firstPlayerStones(firstPlayerStones)
                .secondPlayerStones(secondPlayerStones)
                .firstPlayerKalah(0)
                .secondPlayerKalah(0)
                .build();
    }
}
