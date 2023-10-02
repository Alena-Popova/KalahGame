package sample.project.kalah.controllers.web;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sample.project.kalah.services.GameConfigurationService;
import sample.project.kalah.services.pages.PlayPageModelBuilder;

@Controller
public class GamePagesController
{
    private final PlayPageModelBuilder playPageModelBuilder;

    private final GameConfigurationService gameConfigurationService;

    @Autowired
    public GamePagesController(final PlayPageModelBuilder playPageModelBuilder, final GameConfigurationService gameConfigurationService)
    {
        this.playPageModelBuilder = playPageModelBuilder;
        this.gameConfigurationService = gameConfigurationService;
    }


    @GetMapping(value = {"/", "/start"})
    public String startGame()
    {
        try
        {
            return gameConfigurationService.getStartWebPageValue();
        }
        catch (Exception e)
        {
            return gameConfigurationService.getErrorWebPageValue();
        }
    }

    @GetMapping("/{id}/play")
    public String playGame(@PathVariable("id") UUID gameId, Model model)
    {
        try
        {
            playPageModelBuilder.fillPlayModel(gameId, model);
            return gameConfigurationService.getPlayWebPageValue();
        }
        catch (Exception e)
        {
            return gameConfigurationService.getErrorWebPageValue();
        }
    }
}
