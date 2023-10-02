package sample.project.kalah.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sample.project.kalah.entity.Player;
import sample.project.kalah.entity.sql.PlayerMoveEntity;
import sample.project.kalah.services.GameConfigurationService;

@Repository("gameUtil")
public class GameUtil
{
    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public GameUtil(final GameConfigurationService gameConfigurationService)
    {
        this.gameConfigurationService = gameConfigurationService;
    }

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

    public Optional<PlayerMoveEntity> getLastMove(List<PlayerMoveEntity> moves)
    {
        return moves == null || moves.isEmpty()
                ? Optional.empty()
                : moves.stream().max(Comparator.comparing(PlayerMoveEntity::getMoveNumber));
    }

    public List<Integer> convertArrayToListForPlayerStones(Integer[] playerStones)
    {
        return playerStones != null
                ? Arrays.asList(playerStones)
                : getDefaultEmptyBar();
    }

    public Integer[] convertListToArrayForPlayerStones(List<Integer> playerStones)
    {
        return playerStones != null
                ? playerStones.toArray(new Integer[gameConfigurationService.getNumberOfHolesForEachParticipant()])
                : getDefaultPlayerStonesArray();
    }

    public Integer[] getDefaultPlayerStonesArray()
    {
        Integer[] playerStones = new Integer[gameConfigurationService.getNumberOfHolesForEachParticipant()];
        Arrays.fill(playerStones, gameConfigurationService.getNumberOfStonesInEachHole());
        return playerStones;
    }

    private List<Integer> getDefaultEmptyBar()
    {
        return new ArrayList<>(Collections.nCopies(gameConfigurationService.getNumberOfHolesForEachParticipant(), 0));
    }

}
