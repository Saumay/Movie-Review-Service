package exceptions;

public class InvalidMovieDescriptionException extends RuntimeException {

    public InvalidMovieDescriptionException() {
        super("Invalid movie description. Doesn't match the regex");
    }
}
