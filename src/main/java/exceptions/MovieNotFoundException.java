package exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String movieName) {
        super("Provided movie '" + movieName + "' doesn't exist");
    }
}
