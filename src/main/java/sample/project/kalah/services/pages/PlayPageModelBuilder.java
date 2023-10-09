package sample.project.kalah.services.pages;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.services.GameConfigurationService;
import sample.project.kalah.services.interfaces.GameActionService;

@Service("playPageModelBuilder")
public class PlayPageModelBuilder
{
    private final GameActionService gameActionService;
    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public PlayPageModelBuilder(final GameActionService gameActionService, final GameConfigurationService gameConfigurationService)
    {
        this.gameActionService = gameActionService;
        this.gameConfigurationService = gameConfigurationService;
    }

    public void fillPlayModel(final UUID gameId, Model model)
    {
        try
        {
            GameConditionData gameDTO = gameActionService.getGame(gameId);
            model.addAttribute("game_id", gameId);
            model.addAttribute("link", generateJoinLink(gameId));

            model.addAttribute("status", gameDTO.getStatus());

            model.addAttribute("active_players", gameDTO.getActivePlayers());

            model.addAttribute("first_player_kalah", gameDTO.getFirstPlayerKalah());
            model.addAttribute("first_player_stones", gameDTO.getFirstPlayerStones());

            model.addAttribute("second_player_kalah", gameDTO.getSecondPlayerKalah());
            model.addAttribute("second_player_stones", gameDTO.getSecondPlayerStones());

            model.addAttribute("winner", gameDTO.getWinner());
        }
        catch (IllegalArgumentException e)
        {
            model.addAttribute("message", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String generateJoinLink(final UUID id)
    {
        return String.format("%s://%s:%s/%s/%s", gameConfigurationService.getServerScheme(), gameConfigurationService.getServerHost(), gameConfigurationService.getServerPort(), id, gameConfigurationService.getPlayWebPageValue());
    }
}
