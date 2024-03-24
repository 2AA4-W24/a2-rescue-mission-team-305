package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONObject;

public class BatteryMIA {
    JSONObject batteryCheck(DroneData data, JSONObject move) {
        Integer battery = data.getBattery();
        if (battery <= 40) {
            JSONObject action = new JSONObject();
            action.put("action", "stop");
            return action;
        }
        else {
            return move;
        }
    }
}
