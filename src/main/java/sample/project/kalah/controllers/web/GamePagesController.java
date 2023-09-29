package sample.project.kalah.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GamePagesController
{
    @GetMapping(value = {"/", "/start"})
    public String startGame()
    {
        return "game";
    }

    @GetMapping("/{id}/play")
    public String playGame(@PathVariable("id") String gameId, Model model)
    {
        model.addAttribute("game_id", gameId);
        return "play";
    }
}
