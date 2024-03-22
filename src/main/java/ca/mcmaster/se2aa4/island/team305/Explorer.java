package ca.mcmaster.se2aa4.island.team305;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team305.Decision.Phase;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    private Decision control_center;

    private DroneData data;

    private String scan_heading;

    private Reader readerclass;
    private Cords droneCords;
    private String direction;
    private DroneData.Heading heading;
    private JSONObject lastaction;
    private BatteryMIA MIA;
    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");
        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
        control_center = new Decision(Phase.FIRST);
        data = new DroneData(direction, batteryLevel);
        readerclass = new Reader();
        readerclass.sitesCordsStart();
        droneCords = new Cords();
        droneCords.droneCordsStart();
    }

    @Override
    public String takeDecision() {
        MIA = new BatteryMIA();
        control_center.determineAct(data,readerclass,droneCords);
        JSONObject action = control_center.getDecision();
        if (control_center.didScan()) {
            scan_heading = control_center.getLastScan();
        }
        droneCords.droneCordsMove(action,direction,lastaction);
        logger.info("** Decision: {}", action.toString());
        lastaction = action;
        action = MIA.batteryMIA(action);
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
        logger.info("Battery left: {}", data.getBattery());
        logger.info("Current heading: {}", data.getHeading());
        if (control_center.checkBiome()) {
            readerclass.processBiomes(extraInfo);
        }
    }
    @Override
    public String deliverFinalReport() {
        String creek;
        droneCords = new Cords();
        creek = droneCords.ClosestCreekCalculation();
        if (creek != null) {
            logger.info("Creek found");
            return creek;
        }
        return "no creek found";
    }
}
