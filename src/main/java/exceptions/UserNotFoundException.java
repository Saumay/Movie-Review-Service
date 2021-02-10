package exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userName) {
        super("Provided user '" + userName + "' doesn't exist");
    }
}
