package ca.mcmaster.se2aa4.island.team305;


import org.json.JSONObject;

public class BatteryMIA {
    private DroneData data;
    private Integer battery;
    public JSONObject batteryMIA(JSONObject move) {
        data = new DroneData();
        JSONObject action;
        action = move;
        battery = data.getBattery();
        if (battery == null) {return action;}
        else if (battery <= 40) {
            action = action.put("action", "stop");
            return action;
        }
        return action;
    }
}
