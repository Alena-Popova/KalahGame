package sample.project.kalah.controllers.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import sample.project.kalah.services.PlayPageModelBuilder;

@Controller
public class GamePagesController
{
    @Value("${server.playPageName}")
    private String playPageName;

    @Value("${server.startPageName}")
    private String startPageName;

    @Autowired
    private PlayPageModelBuilder playPageModelBuilder;

    @GetMapping(value = {"/", "/start"})
    public String startGame()
    {
        return startPageName;
    }

    @GetMapping("/{id}/play")
    public String playGame(@PathVariable("id") String gameId, Model model)
    {
        try
        {
            playPageModelBuilder.fillPlayModel(gameId, model);
            return playPageName;
        }
        catch (Exception e)
        {
            return "error";
        }
    }
}
