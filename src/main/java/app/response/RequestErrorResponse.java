package app.response;

import app.handler.ErrorField;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

public class RequestErrorResponse {

    private List<ErrorField> errors;
    private String message;

    public RequestErrorResponse(List<ErrorField> errors, String message) {
        this.errors = errors;
        this.message = message;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }
}
