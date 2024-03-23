package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.Map;



public class CordsTest {
    private Cords cords;
    private Reader reader;


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
        cords.droneCordsMove(action, "N", null);
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(0), cords.getEastWestCord());
        cords.droneCordsMove(action, "E", action);
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(1), cords.getEastWestCord());
    }
    @Test
    public void droneCordsMoveHeading() {
        JSONObject lastMove = new JSONObject();
        lastMove.put("action", "heading");
        JSONObject move = new JSONObject().put("parameters", new JSONObject().put("direction", "W"));
        cords.droneCordsMove(move, "N", lastMove);
        assertEquals(Integer.valueOf(1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(-1), cords.getEastWestCord());
        cords.droneCordsStart();
        move = new JSONObject();
        move.put("parameters", new JSONObject().put("direction", "S"));
        cords.droneCordsMove(move, "E", lastMove);
        assertEquals(Integer.valueOf(-1), cords.getNorthSouthCord());
        assertEquals(Integer.valueOf(1), cords.getEastWestCord());
    }
    @Test
    public void droneCordsMoveLastMoveNull() {
        JSONObject action = new JSONObject().put("action", "fly");
        cords.droneCordsMove(action, "N", null);
        assertTrue(true);
    }
    @Test
    public void droneCordsMoveMoveNull() {
        JSONObject action = new JSONObject().put("action", "fly");
        cords.droneCordsMove(null, "N", action);
        assertTrue(true);
    }
//    @Test        Maybe I can figure this out but rn its too complex
//public void closestCreekCalculation() {
//    //set up creek 1
//    JSONObject extras = new JSONObject();
//    JSONArray creeksArray = new JSONArray();
//    creeksArray.put("Creek1");
//    cords.setCords(5,5);
//    extras.put("creeks", creeksArray);
//    reader.processBiomes(extras);
//    //set up creek 2
//    extras = new JSONObject();
//    creeksArray = new JSONArray();
//    cords.setCords(7,7);
//    creeksArray.put("Creek2");
//    extras.put("creeks", creeksArray);
//    reader.processBiomes(extras);
//
//    Map<String, int[]> expectedCreekCords = new HashMap<>();
//    expectedCreekCords.put("Creek1", new int[]{5, 5});
//    expectedCreekCords.put("Creek2", new int[]{7, 7});
//    cords.setReaderClass(reader);
//    String closestCreek = cords.ClosestCreekCalculation();
//    assertEquals("Creek1", closestCreek);
//    assertEquals(expectedCreekCords, reader.getCreekCord("Creek1"));
//}
}
