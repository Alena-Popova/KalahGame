package sample.project.kalah.exceptions;

/**
 * Exception thrown when a move is not allowed in the game from the specified position or for specified player.
 */
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
