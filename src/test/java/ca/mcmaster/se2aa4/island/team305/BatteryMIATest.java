package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.json.JSONObject;
public class BatteryMIATest {

    private BatteryMIA batteryMIA;
    private JSONObject move;
    @BeforeEach
    public void batterySetUp(){
        batteryMIA = new BatteryMIA();
        move = new JSONObject();
    }

    @Test
    public void batteryMIANull() {
        DroneData drone = new DroneData("E", null);
        JSONObject action = batteryMIA.batteryMIA(move);
        assertEquals(move.toString(), action.toString());
    }

    @Test
    public void batteryMIAUnder40() {
        BatteryMIA batteryMIA = new BatteryMIA();
        DroneData drone = new DroneData("E", 20);
        JSONObject move = new JSONObject();
        JSONObject action = batteryMIA.batteryMIA(move);
        assertEquals(move.toString(), action.toString());
    }
    @Test
    public void batteryMIAOver40() {
        // Test when battery is greater than 40
        BatteryMIA batteryMIA = new BatteryMIA();
        DroneData drone = new DroneData("E", 100);
        JSONObject move = new JSONObject();
        JSONObject result = batteryMIA.batteryMIA(move);
        assertEquals(move.toString(), result.toString());
    }

}
