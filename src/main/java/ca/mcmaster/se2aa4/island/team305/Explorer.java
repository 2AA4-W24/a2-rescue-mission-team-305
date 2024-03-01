package ca.mcmaster.se2aa4.island.team305;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private Integer decision_number;

    private Decision control_center;

    private DroneData data;

    private Integer home_distance;

    private String scan_heading;

    private Reader readerclass;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
        control_center = new Decision();
        data = new DroneData(direction, batteryLevel);
        readerclass = new Reader();
        home_distance = 0;
        decision_number = 0;
    }

    @Override
    public String takeDecision() {
        control_center.determineAct(data, readerclass, decision_number, home_distance);
        JSONObject action = control_center.getDecision();
        if (control_center.didScan()) {
            scan_heading = control_center.getLastScan();
        }
        logger.info("** Decision: {}", action.toString());
        decision_number += 1;
        return action.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        readerclass.fileReader(response, control_center.didScan(), scan_heading, data);
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);
        Scanner scanner = new Scanner();
        scanner.processBiomes(extraInfo);
        logger.info("E delta = {}", readerclass.getRange("E")); //why is this here
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
