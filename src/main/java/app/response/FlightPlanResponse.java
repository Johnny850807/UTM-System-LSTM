package app.response;

public class FlightPlanResponse {

    private int statusCode;
    private String message;


    public FlightPlanResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
