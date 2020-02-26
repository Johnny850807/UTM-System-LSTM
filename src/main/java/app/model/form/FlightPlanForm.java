package app.model.form;

import app.model.flightplan.FlightPlan;
import app.model.flightplan.FlightPlanPath;
import app.model.form.convert.FlightFormConvert;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static app.model.form.FormStatus.MAY_NOT_BE_EMPTY;
import static app.model.form.FormStatus.MAY_NOT_BE_NULL;
import static app.model.form.FormStatus.MUST_GREATER_THAN_60;


/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

public class FlightPlanForm {

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("pilot-id")
    private String pilotId;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("uav-id")
    private String uavId;

    @JsonProperty("ads-b")
    private String adsb;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("expected-takeoff-time")
    private String expectedTakeoffTime;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("expected-arrivals-time")
    private String expectedArrivalsTime;

    @NotNull(message = MAY_NOT_BE_NULL)
    @Min(value = 60, message = MUST_GREATER_THAN_60)
    @JsonProperty("expected-flying-height")
    private int expectedFlyingHeight;

    @NotNull(message = MAY_NOT_BE_NULL)
    @JsonProperty("flight-plan-path")
    private FlightPlanPath flightPlanPath;

    @NotBlank(message = MAY_NOT_BE_EMPTY)
    @JsonProperty("flight-description")
    private String flightDescription;

    public FlightPlanForm(){}

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

    public FlightPlan convertToFlightPlan() {
        return new FlightFormConvert().convert(this);
    }
}
