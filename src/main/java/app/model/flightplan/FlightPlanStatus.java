package app.model.flightplan;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public interface FlightPlanStatus {

    String OK_MESSAGE = "OK";
    String NOT_FOUND_PILOT_MESSAGE = "Not found Pilot.";
    String EXPECTED_TAKEOFF_TIME_NOT_BEFORE_1Hour_MESSAGE = "Expected takeoff time not early 1 hour.";
    String FLIGHT_PATH_LESS_THEN_2_POINT_MESSAGE = "Flight path less then 2 point.";

    int OK_CODE = 2000;
    int NOT_FOUND_PILOT_CODE = 2001;
    int EXPECTED_TAKEOFF_TIME_NOT_BEFORE_1Hour_MESSAGE_CODE = 2002;
    int FLIGHT_PATH_LESS_THEN_2_POINT_MESSAGE_CODE = 2003;



    int FORM_ERROR_CODE = 4000;
}
