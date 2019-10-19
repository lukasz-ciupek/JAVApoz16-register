package pl.sda.register.exception;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
