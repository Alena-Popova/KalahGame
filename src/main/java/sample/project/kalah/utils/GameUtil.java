package sample.project.kalah.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import sample.project.kalah.entity.Player;

@Component("GameUtil")
public class GameUtil
{
    public String[] convertPlayerListToStringArray(List<Player> playersList)
    {
        return playersList.stream()
                .map(Player::name)
                .toArray(String[]::new);
    }

    public List<Player> convertStringArrayToPlayerList(String[] array)
    {
        List<String> players = array != null ? Arrays.asList(array) : Collections.emptyList();
        return players
                .stream()
                .map(Player::valueOf)
                .collect(Collectors.toList());
    }

}
