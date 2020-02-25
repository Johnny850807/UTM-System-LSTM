package app.controller;

import app.gto.UavGeoJsonOnMapGTO;
import app.model.uav.UavFlightPath;
import app.service.UavDataService;

import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/uav/flightData")
public class UavFlightDataController {

    @Resource
    UavDataService uavDataService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @CrossOrigin
    @GetMapping(value = "/location/{pilotId}")
    public List<UavGeoJsonOnMapGTO> getUavLatLongData(@PathVariable String pilotId){
        List<UavGeoJsonOnMapGTO> geoJson = uavDataService.getUavPathDataByPilot(pilotId);
        System.out.println(geoJson);
        return geoJson;
    }

    @PostMapping(value = "/mock/location")
    public ResponseEntity<Object> saveMockUavLatLongData(@RequestBody UavFlightPath uavFlightPath){
        int recordIndex = uavDataService.saveUavFlightPath(uavFlightPath);
        return ResponseEntity.ok(recordIndex);
    }

    @CrossOrigin
    @GetMapping(value = "/limitArea/forbid")
    public JSONObject getLimitAreaData(){
        return uavDataService.getForbidAreaJson();
    }

    @CrossOrigin
    @GetMapping(value = "/limitArea/allowFly")
    public JSONObject getAllowsFlyAreaData(){
        return uavDataService.getAllowsFlyAreaJson();
    }

    @CrossOrigin
    @GetMapping(value = "/limitArea/airportCamp")
    public JSONObject getAirportCampLimitAreaData(){
        return uavDataService.getAirportCampLimitAreaJson();
    }

}
