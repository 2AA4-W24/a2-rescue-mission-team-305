package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;

public class ReaderTest {
    private Reader reader;

    private Cords cords;
    @BeforeEach
    public void setUpReader() {
        reader = new Reader();
        cords = new Cords();
    }

    @Test
    public void sitesCordsStart() {
        reader.sitesCordsStart();
        assertEquals(0, reader.getCreekSize());
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

    @Test
    void processBiomesBiomes() {
        JSONObject extras = new JSONObject()
                .put("biomes", new JSONArray().put("forest").put("desert").put("mountain"));
        reader.sitesCordsStart();
        reader.processBiomes(extras);
        assertEquals(3, reader.biomes.size());
        assertTrue(reader.biomes.contains("forest"));
        assertTrue(reader.biomes.contains("desert"));
        assertTrue(reader.biomes.contains("mountain"));
    }

    @Test
    void processBiomesCreeks() {
        JSONObject extras = new JSONObject().put("creeks", new JSONArray().put("creek1"));
        cords.droneCordsStart();
        reader.sitesCordsStart();
        reader.processBiomes(extras);
        JSONObject moreExtras = new JSONObject().put("creeks", new JSONArray().put("creek2"));
        reader.processBiomes(moreExtras);
        assertEquals(2, reader.getCreekSize());
        assertEquals("creek1", reader.getCreek0ID());
        assertEquals("creek2", reader.getCreekXID(1));

    }

    @Test
    void processBiomesSites() {
        JSONObject extras = new JSONObject().put("sites", new JSONArray().put("site1"));
        reader.sitesCordsStart();
        cords.droneCordsStart();
        reader.processBiomes(extras);
        JSONObject moreExtras = new JSONObject().put("sites", new JSONArray().put("site2"));
        reader.processBiomes(moreExtras);
        assertEquals(2, reader.getSiteSize());
    }
    @Test
    void creekCordStorage() {
        reader.sitesCordsStart();
        cords.droneCordsStart();
        String creekId = "creek1";
        int eastWestCord = 5;
        int northSouthCord = 25;
        reader.creekCordStorage(creekId, eastWestCord, northSouthCord);
        int[] storedCords = reader.getCreekCord(creekId);
        assertEquals(eastWestCord, storedCords[0]);
        assertEquals(northSouthCord, storedCords[1]);
    }

    @Test
    void testSiteCordStorage() {
        reader.sitesCordsStart();
        cords.droneCordsStart();
        String siteId = "site1";
        int eastWestCord = 30;
        int northSouthCord = 40;
        reader.siteCordStorage(siteId, eastWestCord, northSouthCord);
        int[] storedsCords = reader.getSiteCord(siteId);
        assertNotNull(storedsCords);
        assertEquals(2, storedsCords.length); // Ensure array length is 2
        assertEquals(eastWestCord, storedsCords[0]);
        assertEquals(northSouthCord, storedsCords[1]);
    }
}
