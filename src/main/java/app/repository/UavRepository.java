package app.repository;

import app.model.uav.Uav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Repository
public interface UavRepository extends JpaRepository<Uav, Long> {

    @Query(value = "select uavId from uav_info where pilotId = ?1 group by uavId")
    List<String> findUavListByPilotId(String pilotId);

}
