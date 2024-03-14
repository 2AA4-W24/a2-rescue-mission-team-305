package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class DroneData {
    private final Logger logger = LogManager.getLogger();

    public DroneData() {
    }

    public enum Heading {
        NORTH, SOUTH, EAST, WEST
    }

    private Integer battery;

    private Heading direction;

    public DroneData(String heading, Integer initial_budget) {
        switch (heading) {
            case "N":
                direction = Heading.NORTH;
                logger.info("DRONEDATA N");
                break;
            case "W":
                direction = Heading.WEST;
                logger.info("DRONEDATA W");
                break;
            case "S":
                direction = Heading.SOUTH;
                logger.info("DRONEDATA S");
                break;
            case "E":
                direction = Heading.EAST;
                logger.info("DRONEDATA E");
                logger.info(direction);
                break;

        }
        battery = initial_budget;
    }

    public void batteryAction(Integer cost) {
        battery -= cost;
    }

    public Integer getBattery() {
        return battery;
    }

    public void turn(Heading h) {
        direction = h;
    }

    public String checkSide(String side) {
        /*Inputting "R" checks the heading of the right side of the drone,
        "L" the left, and "B" the rear (back). */
        switch (direction) {
            case NORTH -> {
                if (side.equals("L")) {
                    return "W";
                } else if (side.equals("B")) {
                    return "S";
                } else {
                    return "E";
                }
            }
            case WEST -> {
                if (side.equals("L")) {
                    return "S";
                } else if (side.equals("B")) {
                    return "E";
                } else {
                    return "N";
                }
            }
            case SOUTH -> {
                if (side.equals("L")) {
                    return "E";
                } else if (side.equals("B")) {
                    return "N";
                } else {
                    return "W";
                }
            }
            case EAST -> {
                if (side.equals("L")) {
                    return "N";
                } else if (side.equals("B")) {
                    return "W";
                } else {
                    return "S";
                }
            }
        }
        return null; //should throw error if direction is an improper value!! (technical debt)
    }

    public String getHeading() {
        switch (direction) {
            case NORTH -> {
                return "N";
            }
            case SOUTH -> {
                return "S";
            }
            case EAST -> {
                return "E";
            }
            case WEST -> {
                return "W";
            }
        }
        return null; //should throw error if direction is an improper value!! (technical debt)
    }
}
