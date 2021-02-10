package exceptions;

public class MovieYetToBeReleasedException extends RuntimeException {
    public MovieYetToBeReleasedException(String movieName) {
        super("'" + movieName + "' Movie yet to be released");
    }
}
