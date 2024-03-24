package ca.mcmaster.se2aa4.island.team305;

public class DroneData {
    public enum Heading {
        NORTH, SOUTH, EAST, WEST
    }
    private Integer battery; //Integer record of battery remaining for the mission (budget)

    private Heading direction; //Internal record of the drone's current heading

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

    public void turn(String new_heading) {
        switch (new_heading) {
            case "N" -> direction = Heading.NORTH;
            case "W" -> direction = Heading.WEST;
            case "S" -> direction = Heading.SOUTH;
            case "E" -> direction = Heading.EAST;
        }
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
        return null;
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
        return null;
    }
}
