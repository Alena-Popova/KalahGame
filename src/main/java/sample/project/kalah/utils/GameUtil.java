package sample.project.kalah.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.PlayerMoveEntity;

public class GameUtil
{
    public static Optional<PlayerMoveEntity> getLastMove(List<PlayerMoveEntity> moves)
    {
        return moves == null || moves.isEmpty()
                ? Optional.empty()
                : moves.stream().max(Comparator.comparing(PlayerMoveEntity::getMoveNumber));
    }

    public static List<Integer> getList(Integer[] values)
    {
        return Objects.isNull(values)
                ? Collections.emptyList()
                : Arrays.asList(values);
    }

    public static Integer[] getArray(List<Integer> values)
    {
        return Objects.isNull(values)
                ? new Integer[0]
                : values.toArray(Integer[]::new);
    }

    public static List<Player> getPlayerList(String[] values)
    {
        return Objects.isNull(values)
                ? Collections.emptyList()
                : Arrays.stream(values)
                .map(Player::valueOf)
                .collect(Collectors.toList());
    }

    public static String[] getPlayerArray(List<Player> values)
    {
        return Objects.isNull(values)
                ? new String[0]
                : values.stream()
                .map(Enum::toString)
                .toArray(String[]::new);
    }

}
