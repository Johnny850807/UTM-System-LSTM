package app.repository;

import app.model.flightplan.FlightPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Repository
public interface FlightPlanRepository extends JpaRepository<FlightPlan, Long> {

    FlightPlan findByPilotIdAndPlanId(String pilot,String planId);

    List<FlightPlan> findAllByPilotId(String pilot);
}
