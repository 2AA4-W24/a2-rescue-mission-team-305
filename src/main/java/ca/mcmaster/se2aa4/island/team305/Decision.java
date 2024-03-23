package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.apache.logging.log4j.Logger;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Decision {

    private JSONObject next_decision;
    private final Logger logger = LogManager.getLogger();
    private String scan_heading;
    private Boolean radio_decision;
    private Boolean biome_check;
    private Queue<JSONObject> action_queue = new LinkedList<>();
    private Phase step;
    private Integer fly_count;
    private Integer check;
    private Boolean scan_turn_r;

    public Decision(Phase p) {
        step = p;
        action_queue.clear();
        check = 0;
        biome_check = false;
        radio_decision = false;
        scan_turn_r = false;
    }

    public enum Phase {
        FIRST, TURN, RADIO, LOCATE, SCAN, SCAN_TURN, RADIO_2, LOCATE_2, SCAN_2, SCAN_TURN_2, TEST
    }

    public void determineAct(DroneData data, Reader scan, Cords cords) {
        if (scan_heading != null) {
            if (scan.actionInfo(scan_heading) != null) {
                if (step == Phase.FIRST) {
                    if (scan.actionInfo(scan_heading).equals("GROUND")) {
                        action_queue.clear();
                        step = Phase.TURN;
                    }
                }
                if (step == Phase.RADIO_2) {
                    if (radio_decision) {
                        if (scan.actionInfo(scan_heading).equals("OUT_OF_RANGE")) {
                            if (!scan_turn_r) {
                                turn_l();
                                move_f();
                                turn_l();
                                scan_turn_r = true;
                            } else {
                                turn_r();
                                move_f();
                                turn_r();
                                scan_turn_r = false;
                            }
                            radio_f();
                            fly_count = 0;
                            step = Phase.LOCATE_2;
                        }
                    }
                }
            }
        }
        if (biome_check) {
            if (step == Phase.SCAN || step == Phase.SCAN_2) {
                if (scan.biomes.contains("OCEAN")) {
                    if (scan.biomes.size() == 1) {
                        if (scan.actionInfo(scan_heading).equals("OUT_OF_RANGE")) {
                            if (step == Phase.SCAN) {
                                step = Phase.SCAN_TURN;
                            }
                            else {
                                step = Phase.SCAN_TURN_2;
                            }
                        }
                    }
                    else {
                        check = 0;
                    }
                }
                else {
                    check = 0;
                }
            }
        }
        if (action_queue.isEmpty()) {
            switch (step) {
                case FIRST -> {
                    biome_scan();
                    radio_l();
                    radio_r();
                    move_f();
                }
                case TURN -> {
                    if (scan_heading.equals(data.checkSide("L"))) {
                        turn_l();
                    }
                    else {
                        turn_r();
                    }
                    biome_scan();
                    step = Phase.RADIO;
                }
                case RADIO -> {
                    radio_f();
                    fly_count = 0;
                    step = Phase.LOCATE;
                }
                case LOCATE, LOCATE_2 -> {
                    if (fly_count < scan.getRange(data.getHeading())) {
                        move_f();
                        fly_count++;
                    }
                    else {
                        biome_scan();
                        if (step == Phase.LOCATE) {
                            step = Phase.SCAN;
                        }
                        else {
                            step = Phase.SCAN_2;
                        }
                    }
                }
                case SCAN, SCAN_2 -> {
                    radio_f();
                    biome_scan();
                    move_f();
                }
                case SCAN_TURN, SCAN_TURN_2 -> {
                    if (check == 0) {
                        if (!scan_turn_r) {
                            turn_l();
                            turn_l();
                            scan_turn_r = true;
                        } else {
                            turn_r();
                            turn_r();
                            scan_turn_r = false;
                        }
                        if (step == Phase.SCAN_TURN) {
                            step = Phase.SCAN;
                        }
                        else {
                            step = Phase.SCAN_2;
                        }
                        check++;
                    }
                    else {
                        if (step == Phase.SCAN_TURN_2) {
                            action_queue.clear();
                            stop();
                        }
                        else {
                            scan_turn_r = !scan_turn_r;
                            step = Phase.RADIO_2;
                            biome_scan();
                            check = 0;
                        }
                    }
                }
                case RADIO_2 -> {
                    if (!scan_turn_r) {
                        radio_l();
                    }
                    else {
                        radio_r();
                    }
                    move_f();
                }
            }
        }
        if (data.getBattery() <= 40) {
            action_queue.clear();
            stop();
        }
        JSONObject action = action_queue.remove();
        if (action.getString("action").equals("echo")) {
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
        radio_decision = action.getString("action").equals("echo");
        biome_check = action.getString("action").equals("scan");
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
