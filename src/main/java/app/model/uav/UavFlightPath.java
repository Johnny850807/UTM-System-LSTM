package app.model.uav;


import javax.persistence.*;

/**
 * @author swshawnwu@gmail.com(ShawnWu)
 */

@Entity(name = "uav_flight_path")
public class UavFlightPath {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int recordIndex;

    @Column(name = "pilot_id")
    private String pilotId;

    @Column(name = "uav_id")
    private String uavId;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "data")
    private String data;

    @Column(name = "time")
    private String time;


    public UavFlightPath() {
    }

    public int getRecordIndex() {
        return recordIndex;
    }

    public String getPilotId() {
        return pilotId;
    }

    public String getUavId() {
        return uavId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getData() {
        return data;
    }

    public String getTime() {
        return time;
    }
}
