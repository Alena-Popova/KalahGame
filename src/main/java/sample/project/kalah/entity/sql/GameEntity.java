package sample.project.kalah.entity.sql;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.Builder;
import lombok.Data;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.usertype.IntegerArrayType;

@Entity
@Table(name = "kalah_games_data")
@TypeDef(
        name = "integer-array",
        typeClass = IntegerArrayType.class
)
@Data
@Builder
public class GameEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private GameStatus status;

    @Type(type = "integer-array")
    @Column(name = "firstPlayerStones", columnDefinition = "integer[]")
    private Integer[] firstPlayerStones;

    @Column(name = "first_player_kalah")
    private Integer firstPlayerKalah;

    @Type(type = "integer-array")
    @Column(name = "second_player_stones", columnDefinition = "integer[]")
    private Integer[] secondPlayerStones;

    @Column(name = "second_player_kalah")
    private Integer secondPlayerKalah;

}
