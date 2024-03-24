package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team305.Decision.Phase;

interface islandFactory {
    void initialize(String direction, Integer battery_level);

    JSONObject getAction();

    void readResults(JSONObject response);
    String getReport();
}

public class MissionFactory implements islandFactory {
    private final Logger logger = LogManager.getLogger();
    private Decision control_center;
    private DroneData data;
    private String scan_heading;
    private Reader readerclass;
    private Cords droneCords;
    private MapInfo mapInfo;
    private JSONObject action;
    private MissionResults results;

    @Override
    public void initialize(String direction, Integer battery_level) {
        control_center = new Decision(Phase.FIRST);
        data = new DroneData(direction, battery_level);
        readerclass = new Reader();
        droneCords = new Cords();
        droneCords.droneCordsStart();
        mapInfo = new MapInfo();
        mapInfo.sitesCordsStart();
        results = new MissionResults();
    }

    public void findAction() {
        control_center.determineAct(data, readerclass, mapInfo);
        action = control_center.getDecision();
        if (control_center.didScan()) {
            scan_heading = control_center.getLastScan();
        }
        droneCords.droneCordsMove(action,data.getHeading());
    }

    @Override
    public JSONObject getAction() {
        this.findAction();
        JSONObject copy = action;
        return copy;
    }

    @Override
    public void readResults(JSONObject response) {
        readerclass.fileReader(response, control_center.didScan(), scan_heading, data);
        JSONObject extraInfo = response.getJSONObject("extras");
        if (control_center.checkBiome()) {
            mapInfo.processBiomes(extraInfo, droneCords);
        }
    }

    public String getReport() {
        return results.getResults(droneCords, mapInfo);
    }


}
