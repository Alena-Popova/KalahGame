package sample.project.kalah.exceptions;

/**
 * This exception is thrown to indicate that a game action is not allowed.
 */
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

