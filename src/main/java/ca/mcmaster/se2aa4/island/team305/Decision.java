package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONObject;

public class Decision {

    private JSONObject next_decision;

    private String scan_heading;

    private Boolean radio_decision;

    private Boolean biome_check;

    public String scan_copy;

    public JSONObject decision_copy;

    private Integer end_range;

    public void determineAct(DroneData data, Reader scan, Integer decision_count, Integer home_distance) {
        JSONObject action = new JSONObject();
        radio_decision = false;
        biome_check = false;
        if (decision_count == 0) {
            action.put("action", "echo");
            action.put("parameters", new JSONObject().put("direction", "E"));
            radio_decision = true;
            scan_heading = "E";
        }
        else {
            if (decision_count == 1) {
                end_range = scan.getRange("E");
            }
            if (decision_count == end_range + 1) {
                action.put("action", "echo");
                action.put("parameters", new JSONObject().put("direction", "E"));
                radio_decision = true;
                scan_heading = "E";
            }
            else if (decision_count <= end_range) {
                action.put("action", "fly");
            }
            else if (decision_count > end_range + 1) {
                action.put("action","stop");
            }
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

    public Boolean checkBiome() {
        return biome_check;
    }
}
