package sample.project.kalah.repositories.interfaces;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import sample.project.kalah.entity.sql.GameEntity;

@Repository("gameEntityRepository")
public interface GameEntityRepository extends JpaRepository<GameEntity, UUID>
{
    @NonNull
    @Override
    Optional<GameEntity> findById(@NonNull UUID id);

    @NonNull
    @Override
    GameEntity saveAndFlush(@NonNull GameEntity entity);

}
