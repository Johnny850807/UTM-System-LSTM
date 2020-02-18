package app.controller;

import app.model.uav.UavFlightPath;
import app.service.UavDataService;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/uav/flight_data")
public class UavFlightDataController {

    @Resource
    UavDataService uavDataService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @CrossOrigin
    @GetMapping(value = "/location/{pilotId}")
    public List<JSONObject> getUavLatLongData(@PathVariable String pilotId){
        List<JSONObject> geoJson = uavDataService.getUavPathDataByPilot(pilotId);
        System.out.println(geoJson);
        return geoJson;
    }

    @PostMapping(value = "/mock/location")
    public ResponseEntity<Object> saveMockUavLatLongData(@RequestBody UavFlightPath uavFlightPath){
        int recordIndex = uavDataService.saveUavFlightPath(uavFlightPath);
        return ResponseEntity.ok(recordIndex);
    }

    @CrossOrigin
    @GetMapping(value = "/limit_area/forbid")
    public JSONObject getLimitAreaData(){
        return uavDataService.getForbidAreaJson();
    }

    @CrossOrigin
    @GetMapping(value = "/limit_area/allow_fly")
    public JSONObject getAllowsFlyAreaData(){
        return uavDataService.getAllowsFlyAreaJson();
    }

    @CrossOrigin
    @GetMapping(value = "/limit_area/airport_camp")
    public JSONObject getAirportCampLimitAreaData(){
        return uavDataService.getAirportCampLimitAreaJson();
    }

}
