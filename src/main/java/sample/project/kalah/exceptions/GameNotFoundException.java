package sample.project.kalah.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a game is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException
{
    public GameNotFoundException()
    {
    }

    public GameNotFoundException(String message)
    {
        super(message);
    }

}
