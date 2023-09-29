package sample.project.kalah.exceptions;

/**
 * Exception thrown when a player is unable to join a game due to invalid game conditions or game state.
 */
public class GameJoinException extends RuntimeException
{
    public GameJoinException()
    {
    }

    public GameJoinException(String message)
    {
        super(message);
    }

}
