package exceptions;

public class MultipleReviewsNotAllowedException extends RuntimeException {
    public MultipleReviewsNotAllowedException() {
        super("Multiple reviews not allowed");
    }
}
