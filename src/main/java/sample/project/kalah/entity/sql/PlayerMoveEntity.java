package sample.project.kalah.entity.sql;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sample.project.kalah.entity.Player;

@Entity
@Table(name = "player_moves")
@NoArgsConstructor
@Getter
@Setter
public class PlayerMoveEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @Column(name = "move_number")
    private Integer moveNumber;

    @Column(name = "player")
    @Enumerated(EnumType.STRING)
    private Player player;

    @Column(name = "starting_pit")
    private Integer startingPit;

    @Column(name = "starts_in_player_kalah")
    private boolean startsInPlayerKalah;

    @Column(name = "is_player_side")
    private boolean isPlayerSide;

    @Column(name = "ending_pit")
    private Integer endingPit;

    @Column(name = "ends_in_player_kalah")
    private boolean endsInPlayerKalah;

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlayerMoveEntity that = (PlayerMoveEntity) o;
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
        return "PlayerMoveEntity{" +
                "id=" + id +
                ", game=" + (game != null ? game.getId().toString() : null) +
                ", moveNumber=" + moveNumber +
                ", player=" + player +
                ", startingPit=" + startingPit +
                ", startsInPlayerKalah=" + startsInPlayerKalah +
                ", isPlayerSide=" + isPlayerSide +
                ", endingPit=" + endingPit +
                ", endsInPlayerKalah=" + endsInPlayerKalah +
                '}';
    }
}
