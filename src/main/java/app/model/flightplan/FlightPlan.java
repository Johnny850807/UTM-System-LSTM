package app.model.flightplan;

import javax.persistence.*;
@Entity(name = "flight_plan")
@Table(name = "flight_plan")
public class FlightPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "plan_id")
    private String planId;

    @Column(name = "pilot_id")
    private String pilotId;

    @Column(name = "uav_id")
    private String uavId;

    @Column(name = "ads_b")
    private String adsb;

    @Column(name = "expected_takeoff_time")
    private String expectedTakeoffTime;

    @Column(name = "expected_arrivals_time")
    private String expectedArrivalsTime;

    @Column(name = "expected_flying_height")
    private int expectedFlyingHeight;

    @Column(name = "flight_plan_path", length = 2000)
    private FlightPlanPath flightPlanPath;

    @Column(name = "flight_description")
    private String flightDescription;

    public FlightPlan(String pilotId, String uavId, String adsb, String expectedTakeoffTime, String expectedArrivalsTime,
                      int expectedFlyingHeight, FlightPlanPath flightPlanPath, String flightDescription){
        this.pilotId = pilotId;
        this.uavId = uavId;
        this.adsb = adsb;
        this.expectedTakeoffTime = expectedTakeoffTime;
        this.expectedArrivalsTime = expectedArrivalsTime;
        this.expectedFlyingHeight = expectedFlyingHeight;
        this.flightPlanPath = flightPlanPath;
        this.flightDescription = flightDescription;
    }

    public FlightPlan(){}

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public void setUavId(String uavId) {
        this.uavId = uavId;
    }

    public void setAdsb(String adsb) {
        this.adsb = adsb;
    }

    public void setExpectedTakeoffTime(String expectedTakeoffTime) {
        this.expectedTakeoffTime = expectedTakeoffTime;
    }

    public void setExpectedArrivalsTime(String expectedArrivalsTime) {
        this.expectedArrivalsTime = expectedArrivalsTime;
    }

    public void setExpectedFlyingHeight(int expectedFlyingHeight) {
        this.expectedFlyingHeight = expectedFlyingHeight;
    }

    public void setFlightPlanPath(FlightPlanPath flightPlanPath) {
        this.flightPlanPath = flightPlanPath;
    }

    public void setFlightDescription(String flightDescription) {
        this.flightDescription = flightDescription;
    }

    public String getPlanId() {
        return planId;
    }


    public String getPilotId() {
        return pilotId;
    }

    public String getUavId() {
        return uavId;
    }

    public String getAdsb() {
        return adsb;
    }

    public String getExpectedTakeoffTime() {
        return expectedTakeoffTime;
    }

    public String getExpectedArrivalsTime() {
        return expectedArrivalsTime;
    }

    public int getExpectedFlyingHeight() {
        return expectedFlyingHeight;
    }

    public FlightPlanPath getFlightPlanPath() {
        return flightPlanPath;
    }

    public String getFlightDescription() {
        return flightDescription;
    }
}
