package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class CordsTest {
    private Cords cords;
    private Reader reader;
    private MapInfo mapInfo;


    @BeforeEach
    public void setUpCords(){
        cords = new Cords();
        cords.droneCordsStart();
        reader = new Reader();
    }
    @Test
    public void droneCordsStart() {
        assertEquals(Integer.valueOf(0), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(0), cords.getEastWestCord());
    }

    @Test
    public void droneCordsMoveFly() {
        JSONObject action = new JSONObject().put("action", "fly");
        cords.droneCordsMove(action, "N");
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(0), cords.getEastWestCord());
        cords.droneCordsMove(action, "E");
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(1), cords.getEastWestCord());
    }
    @Test
    public void droneCordsMoveHeading() {
        JSONObject lastMove = new JSONObject();
        JSONObject move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "W"));
        cords.droneCordsMove(move, "N");
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(-1), cords.getEastWestCord());
        cords.droneCordsStart();
        move = new JSONObject();
        move.put("action", "heading");
        move.put("parameters", new JSONObject().put("direction", "S"));
        cords.droneCordsMove(move, "E");
        assertEquals(Integer.valueOf(-1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(1), cords.getEastWestCord());
    }

}