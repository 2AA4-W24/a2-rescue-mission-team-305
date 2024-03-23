package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
public class DroneDataTest {

    @Test
    public void batteryCost() {
        DroneData drone = new DroneData("N", 50);
        drone.batteryAction(5);
        assertEquals(45, (int) drone.getBattery());
        drone.batteryAction(9);
        assertEquals(36, (int) drone.getBattery());
    }
    @Test
    public void droneStartUp() {
        DroneData drone = new DroneData("E", 100000);
        assertEquals("E", drone.getHeading());
        assertEquals(100000, (int) drone.getBattery());
    }

    @Test
    public void checkSideOfDrone() {
        DroneData drone = new DroneData("E", 1000);
        assertEquals("N", drone.checkSide("L"));
        assertEquals("W", drone.checkSide("B"));
        assertEquals("S", drone.checkSide("R"));
        drone.turn("S");
        assertEquals("E", drone.checkSide("L"));
        assertEquals("N", drone.checkSide("B"));
        assertEquals("W", drone.checkSide("R"));
    }
    @Test
    public void testTurn() {
        DroneData drone = new DroneData("N", 100);
        drone.turn("E");
        assertEquals("E", drone.getHeading());
        drone.turn("S");
        assertEquals("S", drone.getHeading());
        drone.turn("W");
        assertEquals("W", drone.getHeading());
    }


}
