package app.controller;

import app.model.uav.Uav;
import app.service.UavService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/uav")
public class UavController {

    @Resource
    UavService uavService;

    @GetMapping("/uavlist/{pilotId}")
    public List<String> getUavListByPilotId(@PathVariable String pilotId){
        return uavService.getBelongToUavByPilot(pilotId);
    }

    /*
    等民航局API可用，在串接
     */
    @PostMapping("/")
    public void addUav(@RequestBody Uav uav){
        uavService.addUav(uav);
    }

}
