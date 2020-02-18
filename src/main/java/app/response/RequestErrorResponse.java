package app.response;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class RequestErrorResponse {

    private FieldError fieldError;
    private String message;

    public RequestErrorResponse(FieldError fieldError, String message) {
        this.fieldError = fieldError;
        this.message = message;
    }

    public FieldError getErrors() {
        return fieldError;
    }

    public String getMessage() {
        return message;
    }
}
