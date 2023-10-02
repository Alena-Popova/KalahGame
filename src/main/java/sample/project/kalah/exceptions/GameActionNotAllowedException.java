package sample.project.kalah.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown to indicate that a game action is not allowed.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class GameActionNotAllowedException extends RuntimeException
{
    public GameActionNotAllowedException()
    {
    }

    public GameActionNotAllowedException(String message)
    {
        super(message);
    }

}

