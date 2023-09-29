package sample.project.kalah.convertors.interfaces;

/**
 * A functional interface for converting an object of type S to an object of type R.
 *
 * @param <S> The source type to be converted from.
 * @param <R> The result type to be converted to.
 */
public interface Converter<S, R>
{
    /**
     * Converts an object of type S to an object of type R.
     *
     * @param source The source object to be converted.
     * @return The converted object of type R.
     */
    R convert(S source);
}
