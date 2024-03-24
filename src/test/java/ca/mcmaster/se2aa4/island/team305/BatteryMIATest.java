package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.json.JSONObject;
public class BatteryMIATest {

    private BatteryMIA batteryMIA;
    private JSONObject move;
    private DroneData drone;
    @BeforeEach
    public void batterySetUp(){
        batteryMIA = new BatteryMIA();
        move = new JSONObject();
    }

    @Test
    public void batteryMIAUnder40() {
        drone = new DroneData("E", 20);
        JSONObject action = batteryMIA.batteryCheck(drone, move);
        JSONObject stop = new JSONObject();
        stop.put("action", "stop");
        assertEquals(stop.toString(), action.toString());
    }
    @Test
    public void batteryMIAOver40() {
        drone = new DroneData("E", 100);
        JSONObject result = batteryMIA.batteryCheck(drone, move);
        assertEquals(move.toString(), result.toString());
    }

}
