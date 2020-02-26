package app.model.uav;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Entity(name = "uav_info")
public class Uav {

    @Id
    @Column(name = "uav_id")
    @JsonProperty("uav-id")
    private String uavId;

    @Column(name = "pilot_id")
    @JsonProperty("pilot-id")
    private String pilotId;

    public Uav(){}

    public void setUavId(String uavId) {
        this.uavId = uavId;
    }

    public void setPilotId(String pilotId) {
        this.pilotId = pilotId;
    }

    public Uav(String pilotId, String uavId) {
        this.pilotId = pilotId;
        this.uavId = uavId;
    }

    public String getPilotId() {
        return pilotId;
    }

    public String getUavId() {
        return uavId;
    }
}
