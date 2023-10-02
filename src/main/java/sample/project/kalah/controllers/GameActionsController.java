package sample.project.kalah.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sample.project.kalah.dto.GameConditionResponse;
import sample.project.kalah.services.interfaces.GameActionService;

@RestController
@RequestMapping("/api/game")
public class GameActionsController
{
    private final GameActionService gameActionService;

    @Autowired
    public GameActionsController(final GameActionService gameActionService)
    {
        this.gameActionService = gameActionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameConditionResponse> getGame(@PathVariable("id") UUID gameId)
    {
        return new ResponseEntity<>(gameActionService.getGame(gameId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<GameConditionResponse> createGame()
    {
        return new ResponseEntity<>(gameActionService.createGame(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<GameConditionResponse> joinGame(@PathVariable("id") UUID gameId, @RequestParam("player") String player)
    {
        return new ResponseEntity<>(gameActionService.joinGame(gameId, player), HttpStatus.OK);
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<GameConditionResponse> finishGame(@PathVariable("id") UUID gameId)
    {
        return new ResponseEntity<>(gameActionService.finishGame(gameId), HttpStatus.OK);
    }
}
