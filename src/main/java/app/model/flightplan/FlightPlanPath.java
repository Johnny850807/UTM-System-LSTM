package app.model.flightplan;
import java.io.Serializable;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class FlightPlanPath implements Serializable {

    private double[][] coordinate;

    public FlightPlanPath() {
    }

    public FlightPlanPath(double[][] coordinate) {
        this.coordinate = coordinate;
    }

    public double[][] getCoordinate() {
        return coordinate;
    }
}
