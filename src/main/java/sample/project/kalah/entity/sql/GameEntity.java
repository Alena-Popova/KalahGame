package sample.project.kalah.entity.sql;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sample.project.kalah.entity.GameStatus;
import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.usertype.IntegerArrayType;
import sample.project.kalah.entity.usertype.StringArrayType;

@Entity
@Table(name = "kalah_games_data")
@TypeDefs({
        @TypeDef(name = "integer-array", typeClass = IntegerArrayType.class),
        @TypeDef(name = "string-array", typeClass = StringArrayType.class)
})
@NoArgsConstructor
@Getter
@Setter
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

    @Type(type = "string-array")
    @Column(name = "active_players", columnDefinition = "varchar[]")
    private String[] activePlayers; //todo change to Player[]

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<PlayerMoveEntity> moves;

    @Column(name = "victorious_player")
    @Enumerated(EnumType.STRING)
    private Player victoriousPlayer;

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final GameEntity that = (GameEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }

    @Override
    public String toString()
    {
        return "GameEntity{" +
                "id=" + id +
                ", status=" + status +
                ", firstPlayerStones=" + Arrays.toString(firstPlayerStones) +
                ", firstPlayerKalah=" + firstPlayerKalah +
                ", secondPlayerStones=" + Arrays.toString(secondPlayerStones) +
                ", secondPlayerKalah=" + secondPlayerKalah +
                ", activePlayers=" + Arrays.toString(activePlayers) +
                ", moves=" + moves +
                ", victoriousPlayer=" + victoriousPlayer +
                '}';
    }
}
