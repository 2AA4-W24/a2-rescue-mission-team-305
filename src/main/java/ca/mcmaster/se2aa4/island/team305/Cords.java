package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Cords {
    private final Logger logger = LogManager.getLogger();
    private Integer NorthSouth;
    private Integer EastWest;

    public void droneCordsStart(){ //using cartesian coordinates system (Tech debt as easy to do)
        NorthSouth = 0;
        EastWest = 0;
    }

    public void droneCordsMove(JSONObject action,String currDirection){
        if (action.getString("action").equals("fly")) {
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
        else if (action.getString("action").equals("heading")) {
            String new_heading = action.getJSONObject("parameters").getString("direction");
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

    public Integer getNorthSouthCord(){
        int cord = NorthSouth;
        return cord;
    }
    public Integer getEastWestCord(){
        int cord = EastWest;
        return cord;
    }

}
