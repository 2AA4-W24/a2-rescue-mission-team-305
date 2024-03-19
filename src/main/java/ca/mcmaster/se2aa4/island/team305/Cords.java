package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Cords {
    private final Logger logger = LogManager.getLogger();
    private Integer NorthSouth;
    private Integer EastWest;
    private Reader readerclass;

    public void droneCordsStart() { //using cartesian coordinates system (Tech debt as easy to do)
        NorthSouth = 0;
        EastWest = 0;
    }

    public void droneCordsMove(JSONObject move, String currDirection, JSONObject lastmove) {
        if (move.getString("action").equals("fly")) {
            switch (currDirection) {
                case "N": {
                    NorthSouth += 1;
                    logger.info("NORTH");
                    break;
                }
                case "S": {
                    NorthSouth -= 1;
                    logger.info("SOUTH");
                    break;
                }
                case "E": {
                    EastWest += 1;
                    logger.info("EAST");
                    break;
                }
                case "W": {
                    EastWest -= 1;
                    logger.info("WEST");
                    break;
                }
                default: {
                    logger.info("ERROR in drone cords move");
                }
            }
        }
        if (lastmove != null) {//tech debt
            if (lastmove.getString("action").equals("heading") && lastmove != move) { // i cooked here so it may be burnt and a bit broken
                JSONObject direction = move.getJSONObject("parameters"); //if some error with heading or cords check here first
                String headingChange = direction.getString("direction");
                if (headingChange.equals("N") || headingChange.equals("S") || headingChange.equals("E") || headingChange.equals("W")) {
                    switch (currDirection + "-" + headingChange) {
                        case "N-W", "W-N": {
                            EastWest -= 1;
                            NorthSouth += 1;
                            break;
                        }
                        case "N-E", "E-N": {
                            EastWest += 1;
                            NorthSouth += 1;
                            break;
                        }
                        case "S-W", "W-S": {
                            EastWest -= 1;
                            NorthSouth -= 1;
                            break;
                        }
                        case "S-E", "E-S": {
                            EastWest += 1;
                            NorthSouth -= 1;
                            break;
                        }
                        default: {
                            logger.info("ERROR in drone cords HEADING move");
                        }
                    }
                }
            }
        }
    }

    public Integer GetNorthSouthCord() {
        int cord = NorthSouth;
        return cord;
    }

    public Integer GetEastWestCord() {
        int cord = EastWest;
        return cord;
    }
    private String closestCreek;
    public String ClosestCreekCalculation() {
        double closestResult = 999999999.0; //to ensure first run of code overwrites this number
        try {
            closestCreek = readerclass.getCreek0ID();
        } catch (Exception e) {
            closestCreek = null;
        }
        String currentcreek;
        double result;
        for (int i = 0; i <= readerclass.creekCounter; i++){
            currentcreek = readerclass.getCreekXID(i);
            int[] currentcords = readerclass.getCreekCord(currentcreek);
            result = distanceCalculation(currentcords);
            if (result < closestResult) {
                closestResult = result;
                closestCreek = currentcreek;
            }
        }
        return closestCreek;
    }
    private double distanceCalculation(int[] currentcords) {
        int x = Math.abs(currentcords[0]);
        int y = Math.abs(currentcords[1]);
        return Math.sqrt(x*x+y*y);
    }
}





