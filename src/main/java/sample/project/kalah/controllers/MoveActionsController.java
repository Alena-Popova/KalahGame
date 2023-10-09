package sample.project.kalah.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.project.kalah.dto.GameConditionData;
import sample.project.kalah.dto.PlayerMoveData;
import sample.project.kalah.services.interfaces.MoveActionService;

@RestController
@RequestMapping("/api/game")
public class MoveActionsController
{
    private final MoveActionService moveActionService;

    @Autowired
    public MoveActionsController(final MoveActionService moveActionService)
    {
        this.moveActionService = moveActionService;
    }

    @PostMapping("/{id}/move")
    public ResponseEntity<GameConditionData> makeMove(@PathVariable("id") UUID gameId, @RequestBody PlayerMoveData nextMove)
    {
        return new ResponseEntity<>(moveActionService.makeMove(gameId, nextMove), HttpStatus.CREATED);
    }
}
