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
    private Boolean biome_check; //always false rn
    private Queue<JSONObject> action_queue = new LinkedList<>();
    private Phase step;
    private Integer first_x;
    private Integer second_x;
    private Integer first_y;
    private Integer second_y;

    public Decision(Phase p) {
        step = p;
        action_queue.clear();
    }

    public enum Phase {
        FIRST, SCAN_1_1, SCAN_1_2, TURN, SCAN_2_1, SCAN_2_2, LOCATE, SCAN, STOP, TEST
    }

    public void determineAct(DroneData data, Reader scan, Cords cords) {
        String island_side = "";
        radio_decision = false;
        biome_check = false; // this is always false right now, so you never check the current biome
        if (scan.actionInfo() != null) {
            switch (step) {
                case SCAN_1_1 -> {
                    if (scan.actionInfo().equals("GROUND")) {
                        first_x = cords.getEastWestCord();
                        island_side = scan_heading;
                        step = Phase.SCAN_1_2;
                        action_queue.clear();
                    }
                }
                case SCAN_1_2 -> {
                    if (scan.actionInfo().equals("GROUND")) {
                        second_x = cords.getEastWestCord();
                    }
                    else if (scan.actionInfo().equals("OUT_OF_RANGE")) {
                        logger.info("Left bound x: {}", first_x);
                        logger.info("Right bound x: {}", second_x);
                        step = Phase.TURN;
                        action_queue.clear();
                    }
                }
                case SCAN_2_1 -> {
                    if (scan.actionInfo().equals("GROUND")) {
                        first_y = cords.getNorthSouthCord();
                        island_side = scan_heading;
                        step = Phase.SCAN_2_2;
                        action_queue.clear();
                    }
                }
                case SCAN_2_2 -> {
                    if (scan.actionInfo().equals("GROUND")) {
                        second_y = cords.getNorthSouthCord();
                    }
                    else if (scan.actionInfo().equals("OUT_OF_RANGE")) {
                        logger.info("Left bound x: {}", first_x);
                        logger.info("Right bound x: {}", second_x);
                        logger.info("Left bound y: {}", first_y);
                        logger.info("Right bound y: {}", second_y);
                        step = Phase.STOP;
                        action_queue.clear();
                    }
                }
            }
        }
        if (action_queue.isEmpty()) {
            switch (step) {
                case FIRST -> {
                    radio_f();
                    biome_scan();
                    step = Phase.SCAN_1_1;
                }
                case SCAN_1_1, SCAN_2_1 -> {
                    radio_l();
                    radio_r();
                    biome_scan();
                    move_f();
                }
                case SCAN_1_2, SCAN_2_2 -> {
                    if (island_side.equals(data.checkSide("L"))) {
                        radio_l();
                    }
                    else {
                        radio_r();
                    }
                    biome_scan();
                    move_f();
                }
                case TURN -> {
                    biome_scan();
                    if (island_side.equals(data.checkSide("L"))) {
                        turn_l();
                    }
                    else {
                        turn_r();
                    }
                    step = Phase.SCAN_2_1;
                }
                case TEST -> {

                }
                case STOP -> {
                    stop();
                }
            }
        }
        JSONObject action = action_queue.remove();
        if (action.getString("action").equals("echo")) {
            radio_decision = true;
            String echo_direction = action.getJSONObject("parameters").getString("direction");
            action.getJSONObject("parameters").remove("direction");
            if (echo_direction.equals("F")) {
                action.getJSONObject("parameters").put("direction", data.getHeading());
                scan_heading = data.getHeading();
            }
            else {
                action.getJSONObject("parameters").put("direction", data.checkSide(echo_direction));
                scan_heading = data.checkSide(echo_direction);
            }
        }
        if (action.getString("action").equals("heading")) {
            String turn = action.getJSONObject("parameters").getString("direction");
            action.getJSONObject("parameters").remove("direction");
            action.getJSONObject("parameters").put("direction", data.checkSide(turn));
            data.turn(data.checkSide(turn));
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
        return (biome_check = true);
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

    private void radio_f() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "F"));
        action_queue.add(move);
    }

    private void radio_r() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "R"));
        action_queue.add(move);
    }

    private void radio_l() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "L"));
        action_queue.add(move);
    }

    private void turn_r() {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "R"));
        action_queue.add(move);
    }

    private void turn_l() {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "L"));
        action_queue.add(move);
    }

    private void biome_scan() {
        JSONObject move = new JSONObject();
        move.put("action", "scan");
        action_queue.add(move);
    }
}
