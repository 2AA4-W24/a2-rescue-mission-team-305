package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class Cords {
    private final Logger logger = LogManager.getLogger();
    private Integer NorthSouth;
    private Integer EastWest;
    private Reader readerclass;
    private Reader readerClass;

    public void droneCordsStart() { //using cartesian coordinates system (Tech debt as easy to do)
        NorthSouth = 0;
        EastWest = 0;
    }

    public void droneCordsMove(JSONObject move, String currDirection) {
        if (move.getString("action").equals("fly")) {
            switch (currDirection) {
                case "N": {
                    NorthSouth += 1;
                    break;
                }
                case "S": {
                    NorthSouth -= 1;
                    break;
                }
                case "E": {
                    EastWest += 1;
                    break;
                }
                case "W": {
                    EastWest -= 1;
                    break;
                }
                default: {
                    logger.info("ERROR in drone cords move");
                }
            }
        }
        else if (move.getString("action").equals("heading")) {
            String new_heading = move.getJSONObject("parameters").getString("direction");
            switch (currDirection) {
                case "N": {
                    if (new_heading.equals("W")) {
                        EastWest -= 1;
                    }
                    else if (new_heading.equals("E")) {
                        EastWest += 1;
                    }
                    NorthSouth += 1;
                    break;
                }
                case "S": {
                    if (new_heading.equals("W")) {
                        EastWest -= 1;
                    }
                    else if (new_heading.equals("E")) {
                        EastWest += 1;

    public void droneCordsMove(JSONObject move, String currDirection, JSONObject lastmove) {
        try {//tech debt
            if (move.getString("action").equals("fly")) {
                switch (currDirection) {
                    case "N": {
                        NorthSouth += 1;
                        break;

                    }
                    case "S": {
                        NorthSouth -= 1;
                        break;
                    }
                    case "E": {
                        EastWest += 1;
                        break;
                    }
                    case "W": {
                        EastWest -= 1;
                        break;
                    }
                    default: {
                        logger.info("ERROR in drone cords move");
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Not fly");
        }
        if (lastmove != move) {  //tech debt
            if (lastmove != null) {//tech debt
                if (lastmove.getString("action").equals("heading")) { // i cooked here so it may be burnt and a bit broken
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

    }
    public Integer getNorthSouthCord() {
        int cord = NorthSouth;
        return cord;
    }

    public Integer getEastWestCord() {
        int cord = EastWest;
        return cord;
    }
    private String closestCreek;
    public String ClosestCreekCalculation(Reader readerclass) {
        double closestResult = 999999999.0; //to ensure first run of code overwrites this number
        try {
            closestCreek = readerclass.getCreek0ID();
        } catch (Exception e) {
            closestCreek = null;
        }
        String currentcreek;
        double result;
        String site = readerclass.getSiteID();
        int[] site_cords = readerclass.GetSiteCord(site);
        logger.info("site x:{}", site_cords[0]);
        logger.info("site y:{}", site_cords[1]);
        for (int i = 0; i <= readerclass.creekCounter; i++){
            currentcreek = readerclass.getCreekXID(i);
            int[] currentcords = readerclass.getCreekCord(currentcreek);
            logger.info("creek: {}", currentcreek);
            logger.info("creek x:{}", currentcords[0]);
            logger.info("creek y:{}", currentcords[1]);
            result = distanceCalculation(currentcords, site_cords);
            logger.info("distance: {}", result);
            if (result < closestResult) {
                closestResult = result;
                closestCreek = currentcreek;
            }
        }
        return closestCreek;
    }
    private double distanceCalculation(int[] currentcords, int[] site_cord) {
        double x1 = currentcords[0];
        double y1 = currentcords[1];
        double x2 = site_cord[0];
        double y2 = site_cord[1];
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

}





