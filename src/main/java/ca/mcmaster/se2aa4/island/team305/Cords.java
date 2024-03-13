package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class Cords {
    private final Logger logger = LogManager.getLogger();
    private Integer NorthSouth;
    private Integer EastWest;
    private DroneData droneData;
    private String CurrentDirection;

    public void droneCordsStart(){ //using cartesian coordinates system (Tech debt as easy to do)
        NorthSouth = 0;
        EastWest = 0;
    }

    public void droneCordsMove(JSONObject action,String currDirection){
        if (action.getString("action").equals("fly")) {
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
    }

    public Integer GetNorthSouthCord(){
        int cord = NorthSouth;
        return cord;
    }
    public Integer GetEastWestCord(){
        int cord = EastWest;
        return cord;
    }

}
