package app.repository;

import app.model.uav.UavFlightPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Repository
public interface UavFlightPathRepository extends JpaRepository<UavFlightPath, Long> {

    @Transactional(timeout = 5)
    List<UavFlightPath> findAllByPilotId(String pilotId);

    @Transactional(timeout = 5)
    List<UavFlightPath> findTopByOrderByUavIdDesc(String pilotId);


    @Transactional(timeout = 5)
    @Query(value = "select u.uavId from uav_flight_path u where u.pilotId = ?1 group by u.uavId")
    List<String> findUavIdByPilotId(String pilotId);
}
