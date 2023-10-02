package sample.project.kalah.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a move is not allowed in the game from the specified position or for specified player.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class MoveNotAllowedException extends RuntimeException
{
    public MoveNotAllowedException()
    {
    }

    public MoveNotAllowedException(String message)
    {
        super(message);
    }

}
