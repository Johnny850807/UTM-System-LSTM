package app.handler;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class ErrorField {

    String field;
    String message;

    public ErrorField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }


}
