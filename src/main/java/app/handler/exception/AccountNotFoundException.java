package app.handler.exception;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
