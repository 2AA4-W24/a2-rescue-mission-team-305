package ca.mcmaster.se2aa4.island.team305;


public class DroneData {
    private String heading;

    public DroneData(String h) {
        heading = h;
    }

    public DroneData getData() {
        return new DroneData(heading);
    }
  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import scala.Int;

public class DroneData {
    private int batteryLevel = 7000;
    private int batteryLevelmove;
    private String heading;
    private final Logger logger = LogManager.getLogger();
    public void dataReader() {
        Reader Reader = new Reader();
        heading = Reader.getDirection();
        batteryLevelmove = Reader.getBatteryLevel();
        batteryLevel = batteryLevel - batteryLevelmove;
        logger.info("Current heading", heading);
        logger.info("Current Battery level", batteryLevel);
    }
    public Integer getBatteryLevel(){
        return batteryLevel;
    }
    public String getDirection(){
        return heading;
    }


}
