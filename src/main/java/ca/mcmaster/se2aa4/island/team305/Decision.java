package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.apache.logging.log4j.Logger;
import java.util.LinkedList;
import java.util.Queue;

public class Decision {

    private JSONObject next_decision;

    private final Logger logger = LogManager.getLogger();

    private String scan_heading;

    private Boolean radio_decision;

    private Boolean biome_check;

    private Queue<JSONObject> action_queue = new LinkedList<>();

    private Integer end_range;

    private Phase step;

    public Decision(Phase p) {
        step = p;
        action_queue.clear();
    }

    public enum Phase {
        FIRST, PRELIM, LOCATE, SCAN, STOP
    }

    public void determineAct(DroneData data, Reader scan) {
        radio_decision = false;
        biome_check = false;
        if (scan.actionInfo() != null) {
            if (scan.actionInfo().equals("GROUND")) {
                logger.info("Island Found!!");
                step = Phase.STOP;
                action_queue.clear();
                stop();
            }
        }
        if (action_queue.isEmpty()) {
            if (step == Phase.FIRST) {
                radio_f(data);
                step = Phase.PRELIM;
            }
            else if (step == Phase.PRELIM) {
                radio_l(data);
                radio_r(data);
                move_f();
            }
        }
        JSONObject action = action_queue.remove();
        if (action.getString("action").equals("echo")) {
            radio_decision = true;
            scan_heading = action.getJSONObject("parameters").getString("direction");
        }
        next_decision = action;
    }

    public JSONObject getDecision() {
        return next_decision;
    }

    public String getLastScan() {
        return scan_heading;
    }

    public Boolean didScan() {
        return radio_decision;
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

    private void radio_f(DroneData data) {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", data.getHeading()));
        action_queue.add(move);
    }

    private void radio_r(DroneData data) {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", data.checkSide("R")));
        if (!data.checkSide("R").equals(data.checkSide("B"))) {
            action_queue.add(move);
        }
    }

    private void radio_l(DroneData data) {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", data.checkSide("L")));
        if (!data.checkSide("L").equals(data.checkSide("B"))) {
            action_queue.add(move);
        }
    }

    private void turn_r(DroneData data) {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", data.checkSide("R")));
        if (!data.checkSide("R").equals(data.checkSide("B"))) {
            action_queue.add(move);
        }
    }

    private void turn_l(DroneData data) {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", data.checkSide("L")));
        if (!data.checkSide("L").equals(data.checkSide("B"))) {
            action_queue.add(move);
        }
    }

    private void biome_scan() {
        JSONObject move = new JSONObject();
        move.put("action", "scan");
        action_queue.add(move);
    }
}
