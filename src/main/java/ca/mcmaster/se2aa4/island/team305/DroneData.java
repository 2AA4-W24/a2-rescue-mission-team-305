package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DroneData{
    ReaderInter reader = new Reader();
    private final Logger logger = LogManager.getLogger();

    private Integer battery;
    public enum Heading {
        NORTH, SOUTH, EAST, WEST
    }

    public void batteryAction(Integer cost) {
        battery -= cost;
    }

    public Integer getBattery() {
        Integer copy = battery;
        return copy;
    }


}
