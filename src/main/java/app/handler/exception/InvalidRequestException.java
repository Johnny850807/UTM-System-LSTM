package app.handler.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class InvalidRequestException extends RuntimeException {

    private List<FieldError>  fieldErrors;

    public InvalidRequestException(List<FieldError> fieldErrors) {
        super("Invalid Request");
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError>  getErrors() {
        return fieldErrors;
    }
}
