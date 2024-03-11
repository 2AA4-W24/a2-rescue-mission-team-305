package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DroneData {
    private final Logger logger = LogManager.getLogger();

    public enum Heading {
        NORTH, SOUTH, EAST, WEST
    }

    private Integer battery;

    private Heading direction;

    public DroneData(String heading, Integer initial_budget) {
        switch (heading) {
            case "N":
                direction = Heading.NORTH;
                break;
            case "W":
                direction = Heading.WEST;
                break;
            case "S":
                direction = Heading.SOUTH;
                break;
            case "E":
                direction = Heading.EAST;
                break;

        }
        battery = initial_budget;
    }

    public void batteryAction(Integer cost) {
        battery -= cost;
    }

    public Integer getBattery() {
        Integer copy = battery;
        return copy;
    }

    public Heading getHeading() {
        return direction;
    }


}
