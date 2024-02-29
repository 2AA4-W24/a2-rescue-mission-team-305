package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONObject;

public class Decision {

    private JSONObject next_decision;

    private String scan_heading;

    private Boolean radio_decision;

    public String scan_copy;

    public JSONObject decision_copy;

    public void DetermineAct(DroneData data, Scanner scan, Integer decision_count, Battery costs, Integer home_distance) {
        JSONObject action = new JSONObject();
        switch (decision_count) {
            case 0:
                action.put("action", "echo");
                action.put("parameters", (new JSONObject()).put("direction", "E"));
                scan_heading = "E";
                radio_decision = true;
                break;
            case 1:
                action.put("action", "echo");
                action.put("parameters", (new JSONObject()).put("direction", "N"));
                scan_heading = "N";
                radio_decision = true;
                break;
            case 2:
                action.put("action", "echo");
                action.put("parameters", (new JSONObject()).put("direction", "S"));
                scan_heading = "S";
                radio_decision = true;
                break;
            case 3:
                action.put("action", "stop");
                scan_heading = null;
                radio_decision = false;
                break;

        }
        next_decision = action;
    }

    public JSONObject getDecision() {
        decision_copy = next_decision;
        return decision_copy;
    }

    public String getLastScan() {
        scan_copy = scan_heading;
        return scan_copy;
    }

    public Boolean didScan() {
        Boolean choice_copy = radio_decision;
        return choice_copy;
    }
}
