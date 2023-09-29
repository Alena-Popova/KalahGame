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

import sample.project.kalah.dto.GameDTO;
import sample.project.kalah.services.interfaces.GameProcessorService;

@RestController
@RequestMapping("/api/game")
public class GameActionsController
{
    @Autowired
    private GameProcessorService gameProcessorService;

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> getGame(@PathVariable("id") UUID gameId)
    {
        return new ResponseEntity<>(gameProcessorService.getGame(gameId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<GameDTO> createGame()
    {
        return new ResponseEntity<>(gameProcessorService.createGame(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<GameDTO> joinGame(@PathVariable("id") UUID gameId, @RequestParam("player") String player)
    {
        return new ResponseEntity<>(gameProcessorService.joinGame(gameId, player), HttpStatus.OK);
    }

    @PutMapping("/{id}/finish")
    public ResponseEntity<GameDTO> finishGame(@PathVariable("id") UUID gameId, @RequestParam("player") String player)
    {
        return new ResponseEntity<>(gameProcessorService.finishGame(gameId, player), HttpStatus.OK);
    }
}
