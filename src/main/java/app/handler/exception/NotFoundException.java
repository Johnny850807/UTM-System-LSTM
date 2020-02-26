package app.handler.exception;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
