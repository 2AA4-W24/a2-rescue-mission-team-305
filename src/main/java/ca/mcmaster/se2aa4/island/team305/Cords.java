package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

interface CoordinateTracking {
    void droneCordsMove(JSONObject move, String currDirection);
    String ClosestCreekCalculation(Reader readerclass);
    Integer getEastWestCord();
    Integer getNorthSouthCord();
}

public class Cords implements CoordinateTracking {
    private final Logger logger = LogManager.getLogger();
    private Integer NorthSouth; //y coordinate
    private Integer EastWest; //x coordinate
    private String closestCreek; //String id of closest creek from calculation

    public void droneCordsStart() { //using cartesian coordinates system
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
                    }
                    NorthSouth -= 1;
                    break;
                }
                case "E": {
                    if (new_heading.equals("S")) {
                        NorthSouth -= 1;
                    }
                    else if (new_heading.equals("N")) {
                        NorthSouth += 1;
                    }
                    EastWest += 1;
                    break;
                }
                case "W": {
                    if (new_heading.equals("S")) {
                        NorthSouth -= 1;
                    }
                    else if (new_heading.equals("N")) {
                        NorthSouth += 1;
                    }
                    EastWest -= 1;
                    break;
                }
                default: {
                    logger.info("ERROR in drone cords move");
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
    public String ClosestCreekCalculation(Reader readerclass) {
        double closestResult = 999999999.0; //to ensure first run of code overwrites this number
        try {
            closestCreek = readerclass.getCreek0ID();
            logger.info("closestcreek: {}", closestCreek);
        } catch (Exception e) {
            closestCreek = null;
        }
        String currentcreek;
        double result;
        if (readerclass.sites != null) {
            String site = readerclass.getSiteID();
            logger.info("site: {}", site);
            int[] site_cords = readerclass.GetSiteCord(site);
            logger.info("site x: {}", site_cords[0]);
            logger.info("site y: {}", site_cords[1]);
            logger.info("creek counter: {}", readerclass.creekCounter);
            for (int i = 0; i <= readerclass.creekCounter; i++) {
                currentcreek = readerclass.getCreekXID(i);
                logger.info("currentcreek: {}", currentcreek);
                if (readerclass.creekStorage.containsKey(currentcreek)) {
                    int[] currentcords = readerclass.getCreekCord(currentcreek);
                    logger.info("creek x: {}", currentcords[0]);
                    logger.info("creek y: {}", currentcords[1]);
                    result = distanceCalculation(currentcords, site_cords);
                    logger.info("calc finished");
                    if (result < closestResult) {
                        closestResult = result;
                        closestCreek = currentcreek;
                    }
                }
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





