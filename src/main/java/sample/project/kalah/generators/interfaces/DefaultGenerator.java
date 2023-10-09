package sample.project.kalah.generators.interfaces;

/**
 * A functional interface for generating new entities of type T with default parameters.
 *
 * @param <T> The type of entity to be generated.
 */
public interface DefaultGenerator<T>
{
    T generate();
}
