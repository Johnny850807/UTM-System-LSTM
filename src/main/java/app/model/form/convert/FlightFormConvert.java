package app.model.form.convert;

import app.model.flightplan.FlightPlan;
import app.model.form.FlightPlanForm;
import app.model.form.FormConvert;
import org.springframework.beans.BeanUtils;

public class FlightFormConvert implements FormConvert<FlightPlanForm, FlightPlan> {

    @Override
    public FlightPlan convert(FlightPlanForm flightPlanForm) {
        FlightPlan flightPlan = new FlightPlan();
        BeanUtils.copyProperties(flightPlanForm, flightPlan);
        return flightPlan;
    }
}
