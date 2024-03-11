package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

public class Decision {

    private JSONObject next_decision;

    private String scan_heading;

    private Boolean radio_decision;

    private Boolean biome_check;

    public String scan_copy;

    public JSONObject decision_copy;

    private Queue<JSONObject> action_queue = new LinkedList<>();

    private Integer end_range;

    public void determineAct(DroneData data, Reader scan, Integer decision_count) {
        radio_decision = false;
        biome_check = false;
        if (decision_count == 0) {
            radio("E");
            radio_decision = true;
            scan_heading = "E";
        }
        else {
            if (decision_count == 1) {
                end_range = scan.getRange("E");
            }
            if (decision_count == end_range + 1) {
                radio("E");
                radio_decision = true;
                scan_heading = "E";
            }
            else if (decision_count <= end_range) {
                move_f();
            }
            else if (decision_count > end_range + 1) {
                stop();
            }
        }
        next_decision = action_queue.remove();
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

    private void move_f() {
        JSONObject move = new JSONObject();
        move.put("action", "fly");
        action_queue.add(move);
    }

    private void stop() {
        JSONObject move = new JSONObject();
        move.put("action", "stop");
        action_queue.add(move);
    }

    private void radio(String heading) {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", heading));
        action_queue.add(move);
    }
}
