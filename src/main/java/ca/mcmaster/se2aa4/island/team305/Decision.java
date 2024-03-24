package ca.mcmaster.se2aa4.island.team305;

import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.apache.logging.log4j.Logger;
import java.util.LinkedList;
import java.util.Queue;

interface DecisionHub {
    void determineAct(DroneData data, Reader scan, MapInfo mapInfo);
    JSONObject getDecision();
    void move_f();

    void stop();

    void radio_f();

    void radio_r();

    void radio_l();

    void turn_r();

    void turn_l();

    void biome_scan();
}
public class Decision implements DecisionHub {

    private JSONObject next_decision; //JSONObject representation of next action to send to the island repository
    private final Logger logger = LogManager.getLogger();
    private String scan_heading; //Heading of last radio scan
    private Boolean radio_decision; //Boolean indicating if the last action was a radio scan
    private Boolean biome_check; //Boolean indicating if the last action was a biome scan
    private Queue<JSONObject> action_queue = new LinkedList<>(); //Queue containing next actions
    private Phase step; //Current phase of exploration
    private Integer fly_count; //Number of spaces to fly to initially reach the island
    private Integer turn_counter; /*Amount of times drone has done a complete turn in current phase,
    must transition to next phase if above 0. */
    private Boolean scan_turn_r; //Boolean representing which direction to turn next. If true, turn right next. Else, turn left.

    public Decision(Phase p) {
        step = p;
        action_queue.clear();
        turn_counter = 0;
        biome_check = false;
        radio_decision = false;
        scan_turn_r = false;
    }

    public enum Phase {
        FIRST, TURN, RADIO, LOCATE, SCAN, SCAN_TURN, RADIO_2, LOCATE_2, SCAN_2, SCAN_TURN_2, TEST
    }

    @Override
    public void determineAct(DroneData data, Reader scan, MapInfo mapInfo) {
        BatteryMIA battery_checker = new BatteryMIA();
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
                if (mapInfo.biomes.contains("OCEAN")) {
                    if (mapInfo.biomes.size() == 1) {
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
                        turn_counter = 0;
                    }
                }
                else {
                    turn_counter = 0;
                }
            }
        }
        if (action_queue.isEmpty()) {
            switch (step) {
                case FIRST -> {
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
                    if (turn_counter == 0) {
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
                        turn_counter++;
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
                            turn_counter = 0;
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
        JSONObject action = action_queue.remove();
        action = battery_checker.batteryCheck(data, action);
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
        JSONObject copy = next_decision;
        return copy;
    }

    public String getLastScan() {
        String copy = scan_heading;
        return copy;
    }

    public Boolean didScan() {
        Boolean copy = radio_decision;
        return copy;
    }

    public Boolean checkBiome() {
        Boolean copy = biome_check;
        return copy;
    }
    @Override
    public void move_f() {
        JSONObject move = new JSONObject();
        move.put("action", "fly");
        action_queue.add(move);
    }
    @Override
    public void stop() {
        JSONObject move = new JSONObject();
        move.put("action", "stop");
        action_queue.add(move);
    }
    @Override
    public void radio_f() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "F"));
        action_queue.add(move);
    }
    @Override
    public void radio_r() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "R"));
        action_queue.add(move);
    }
    @Override
    public void radio_l() {
        JSONObject move = new JSONObject();
        move.put("action", "echo");
        move.put("parameters", new JSONObject().put("direction", "L"));
        action_queue.add(move);
    }
    @Override
    public void turn_r() {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "R"));
        action_queue.add(move);
    }
    @Override
    public void turn_l() {
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "L"));
        action_queue.add(move);
    }
    @Override
    public void biome_scan() {
        JSONObject move = new JSONObject();
        move.put("action", "scan");
        action_queue.add(move);
    }
}
