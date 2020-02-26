package app.service;

import app.model.uav.Uav;
import app.repository.UavRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Service
public class UavService {

    @Resource
    UavRepository uavRepository;

    public List<String> getBelongToUavByPilot(String pilotId){
        return uavRepository.findUavListByPilotId(pilotId);
    }

    public void addUav(Uav uav){
        uavRepository.save(uav);
    }
}
