package app.controller;

import app.gto.UavGeoJsonOnMapGTO;
import app.handler.exception.InvalidRequestException;
import app.handler.exception.NotFoundException;
import app.model.flightplan.FlightPlan;
import app.response.FlightPlanResponse;
import app.model.flightplan.FlightPlanStatus;

import app.model.form.FlightPlanForm;
import app.service.FlightPlanService;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;



@CrossOrigin
@RestController
@RequestMapping("/flightPlan")
public class FightPlanController {

    @Resource
    FlightPlanService flightPlanService;

    @PostMapping
    public FlightPlanResponse flightPlanRegister(@Valid @RequestBody FlightPlanForm flightPlanForm, BindingResult formValidateResult){
        if(isFlightPlanFormHasError(formValidateResult))
            throw new InvalidRequestException(formValidateResult.getFieldErrors());

        FlightPlanResponse response = flightPlanService.validateFlightPlanFeasible(flightPlanForm);
        if (response.getStatusCode() == FlightPlanStatus.OK_CODE) {
            FlightPlan flightPlan = flightPlanForm.convertToFlightPlan();
            flightPlanService.saveFlightPlan(flightPlan);
        }
        return response;
    }

    private boolean isFlightPlanFormHasError(BindingResult bindingResult){
        return bindingResult.hasErrors();
    }

    @GetMapping("/{pilotId}/{planId}")
    public ResponseEntity<?> findOneFlightPlan(@PathVariable String pilotId, @PathVariable String planId){
        FlightPlan flightPlan = flightPlanService.findOneFlightPlan(pilotId, planId);
        if(flightPlan == null)
            throw new NotFoundException("Not Found FlightPlan");
        return ResponseEntity.ok(flightPlan);
    }

    @GetMapping("/{pilotId}")
    public ResponseEntity<?> findAllFlightPlanGeoJson(@PathVariable String pilotId){
        List<FlightPlan> flightPlanList = flightPlanService.findAllFlightPlan(pilotId);
        if(flightPlanList.isEmpty())
            throw new NotFoundException("Not Found FlightPlan");
        UavGeoJsonOnMapGTO flightPlanGeoJson = flightPlanService.getFlightPlanGeoJson(flightPlanList);
        return ResponseEntity.ok(flightPlanGeoJson);
    }

}
