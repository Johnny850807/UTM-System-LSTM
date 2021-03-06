package app.controller;

import app.dto.FeatureCollectionDTO;
import app.model.uav.UavFlightPath;
import app.service.UavDataService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@CrossOrigin
@RestController
@RequestMapping("/uav/flightData")
public class UavFlightDataController {

    @Resource
    UavDataService uavDataService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }


    @GetMapping(value = "/location/{pilotId}")
    public List<FeatureCollectionDTO> getUavLatLongData(@PathVariable String pilotId){
        List<FeatureCollectionDTO> geoJson = uavDataService.getUavPathDataByPilot(pilotId);
        System.out.println(geoJson);
        return geoJson;
    }

    @PostMapping(value = "/mock/location")
    public ResponseEntity<Object> saveMockUavLatLongData(@RequestBody UavFlightPath uavFlightPath){
        int recordIndex = uavDataService.saveUavFlightPath(uavFlightPath);
        return ResponseEntity.ok(recordIndex);
    }

    @GetMapping(value = "/limitArea/forbid")
    public FeatureCollectionDTO getLimitAreaData(){
        return uavDataService.getForbidAreaJson();
    }

    @GetMapping(value = "/limitArea/allowFly")
    public FeatureCollectionDTO getAllowsFlyAreaData(){
        return uavDataService.getAllowsFlyAreaJson();
    }

    @GetMapping(value = "/limitArea/airportCamp")
    public FeatureCollectionDTO getAirportCampLimitAreaData(){
        return uavDataService.getAirportCampLimitAreaJson();
    }

}
