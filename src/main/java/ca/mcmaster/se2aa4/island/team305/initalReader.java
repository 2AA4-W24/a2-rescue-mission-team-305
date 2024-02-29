package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.Int;

interface initalReaderData {
    void initalBattery(Integer batteryLevel);
    void initalHeading(String direction);
}

public class initalReader extends DroneData implements initalReaderData {
    protected Integer startBatteryLevel;
    DroneData droneData = new DroneData();
    private DroneData.Heading heading;

    private final Logger logger = LogManager.getLogger();

    @Override
    public void initalBattery(Integer batteryLevel) {
        startBatteryLevel = batteryLevel;
    }

    @Override
    public void initalHeading(String direction) {
        switch (direction) {
            case "N":
                heading = DroneData.Heading.NORTH;
                break;
            case "S":
                heading = DroneData.Heading.SOUTH;
                break;
            case "E":
                heading = DroneData.Heading.EAST;
                break;
            case "W":
                heading = DroneData.Heading.WEST;
                break;
            default:
                logger.error("Invalid starting direction");
        }
    }
}