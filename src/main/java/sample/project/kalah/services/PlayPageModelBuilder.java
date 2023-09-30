package sample.project.kalah.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.services.interfaces.GameActionService;

@Service("playPageModelBuilder")
public class PlayPageModelBuilder
{
    @Value("${server.scheme}")
    private String scheme;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private String port;

    @Value("${server.startPageName}")
    private String startPageName;

    @Autowired
    private GameActionService gameActionService;

    public void fillPlayModel(final String gameId, Model model)
    {
        try
        {
            GameDTO gameDTO = gameActionService.getGame(UUID.fromString(gameId));
            model.addAttribute("game_id", gameId);
            model.addAttribute("link", generateJoinLink(gameId));

            model.addAttribute("status", gameDTO.getStatus());

            model.addAttribute("active_players", gameDTO.getActivePlayers());

            model.addAttribute("first_player_kalah", gameDTO.getFirstPlayerKalah());
            model.addAttribute("first_player_stones", gameDTO.getFirstPlayerStones());

            model.addAttribute("second_player_kalah", gameDTO.getSecondPlayerKalah());
            model.addAttribute("second_player_stones", gameDTO.getSecondPlayerStones());

            model.addAttribute("victorious_player", gameDTO.getVictoriousPlayer());
        }
        catch (IllegalArgumentException e)
        {
            model.addAttribute("message", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String generateJoinLink(final String id)
    {
        return String.format("%s://%s:%s/%s/%s", scheme, host, port, id, startPageName);
    }
}
