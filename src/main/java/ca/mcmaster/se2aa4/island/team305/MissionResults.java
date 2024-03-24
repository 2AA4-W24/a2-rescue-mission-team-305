package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MissionResults {
    private final Logger logger = LogManager.getLogger();

    public String getResults(Cords dronecords, MapInfo map_info) {
        String closest_creek = dronecords.ClosestCreekCalculation(map_info);
        if (closest_creek != null) {
            logger.info("Closest creek found");
            logger.info("Creek id is: {}", closest_creek);
            return closest_creek;
        }
        logger.info("no creek found");
        return "No creek found";
    }
}
