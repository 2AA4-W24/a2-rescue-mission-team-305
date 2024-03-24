package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class ReaderTest {
    private Reader reader;

    @BeforeEach
    public void setUpReader() {
        reader = new Reader();
    }

    @Test
    public void fileReaderCost() {
        JSONObject info = new JSONObject();
        info.put("cost", 7);
        reader.fileReader(info, false, "N", new DroneData("N", 1000));
        assertEquals(Integer.valueOf(7), reader.getMoveCost());
    }

    @Test
    public void fileReaderEcho() {
        JSONObject info = new JSONObject();
        info.put("cost", 7);
        JSONObject extras = new JSONObject();
        extras.put("range", 3);
        extras.put("found", "Ground");
        info.put("extras", extras);
        reader.fileReader(info, true, "N", new DroneData("N", 1000));
        assertEquals(7, reader.getMoveCost());
        assertEquals(3, reader.getRange("N"));
        assertEquals("Ground", reader.actionInfo("N"));
    }
}