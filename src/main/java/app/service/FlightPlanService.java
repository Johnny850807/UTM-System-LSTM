package app.service;

import app.geotools.FeatureCollectionBuilder;
import app.geotools.GeoJsonTool;
import app.geotools.Properties;
import app.dto.FeatureCollectionDTO;
import app.model.flightplan.FlightPlan;
import app.model.flightplan.FlightPlanPath;
import app.response.FlightPlanResponse;
import app.model.form.FlightPlanForm;
import app.repository.FlightPlanRepository;
import app.utility.DateTimeManager;
import com.mapbox.geojson.Point;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.model.flightplan.FlightPlanStatus.*;


/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Service
public class FlightPlanService {

    @Resource
    FlightPlanRepository flightPlanRepository;

    public FlightPlanResponse validateFlightPlanFeasible(FlightPlanForm flightPlanForm){
        String responseMessage = OK_MESSAGE;
        int responseCode = OK_CODE;

        if(isPilotExist(flightPlanForm.getPilotId())){
            //TODO
        }else if(isUavExist(flightPlanForm.getUavId())){
            //TODO
        }else if(!isTimeBefore1Hour(flightPlanForm.getExpectedTakeoffTime())) {
            responseMessage = EXPECTED_TAKEOFF_TIME_NOT_BEFORE_1Hour_MESSAGE;
            responseCode = EXPECTED_TAKEOFF_TIME_NOT_BEFORE_1Hour_MESSAGE_CODE;
        }else if(!isFlightPathLessThan2Point(flightPlanForm.getFlightPlanPath().getCoordinate())){
            responseMessage = FLIGHT_PATH_LESS_THEN_2_POINT_MESSAGE;
            responseCode = FLIGHT_PATH_LESS_THEN_2_POINT_MESSAGE_CODE;
        }
        return new FlightPlanResponse(responseCode, responseMessage);
    }

    private boolean isPilotExist(String pilotId){
        //TODO
        return true;
    }

    private boolean isUavExist(String uavId){
        //TODO
        return true;
    }

    private boolean isTimeBefore1Hour(String flightTime){
        LocalDateTime expectedTakeoffTime = DateTimeManager.convertToDateTime(flightTime);
        LocalDateTime nowTime = DateTimeManager.getCurrentTime();
        if(expectedTakeoffTime.isAfter(nowTime)){
            Duration timeDifference = Duration.between(nowTime, expectedTakeoffTime);
            return timeDifference.toMinutes() >= 60;
        }
        return false;
    }

    private boolean isFlightPathLessThan2Point(double[][] coordinate) {
        return coordinate.length>=2;
    }

    public void saveFlightPlan(FlightPlan flightPlan){
        System.out.println("run save");
        flightPlanRepository.save(flightPlan);
    }

    public FlightPlan findOneFlightPlan(String pilotId, String planId){
        System.out.println("GET ONE PLAN");
        return flightPlanRepository.findByPilotIdAndPlanId(pilotId, planId);
    }

    public List<FlightPlan> findAllFlightPlan(String pilotId){
        System.out.println("GET ALL PLAN");
        return flightPlanRepository.findAllByPilotId(pilotId);
    }

    public FeatureCollectionDTO getFlightPlanGeoJson(List<FlightPlan> flightPlanList){
        FeatureCollectionBuilder pathFeatureCollection = GeoJsonTool.buildFeatureCollection();
        flightPlanList.forEach(flightPlan -> {
            FlightPlanPath flightPlanPath = flightPlan.getFlightPlanPath();
            double[][] planPoint = flightPlanPath.getCoordinate();
            List<Point> lineStringPoint = new ArrayList<>();
            for (double[] point : planPoint) {
                lineStringPoint.add(Point.fromLngLat(point[0], point[1]));
            }

            Map<String, String> pathProperties = new HashMap<>();
            pathProperties.put("plan-id", String.valueOf(flightPlan.getPlanId()));
            pathProperties.put("uav-id", flightPlan.getUavId());
            pathProperties.put("expected-takeoff-time", flightPlan.getExpectedTakeoffTime());
            pathProperties.put("fly-height", String.valueOf(flightPlan.getExpectedFlyingHeight()));
            pathProperties.put("description", flightPlan.getFlightDescription());
            pathFeatureCollection.addLineString(lineStringPoint, new Properties(pathProperties));
        });
        return pathFeatureCollection.buildJsonObject();
    }

    public void updateFlightPlan(){

    }

    public void deleteFlightPlan(){

    }

}
