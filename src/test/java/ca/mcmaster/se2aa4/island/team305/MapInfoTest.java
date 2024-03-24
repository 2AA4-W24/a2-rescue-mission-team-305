package ca.mcmaster.se2aa4.island.team305;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class MapInfoTest {
    private Cords cords;
    private MapInfo mapInfo;
    @BeforeEach
    public void setUpMapInfo() {
        mapInfo = new MapInfo();
        cords = new Cords();
    }

    @Test
    public void sitesCordsStart() {
        mapInfo.sitesCordsStart();
        assertNull(mapInfo.getCreeks());
    }

    @Test
    void processBiomesBiomes() {
        JSONObject extras = new JSONObject()
                .put("biomes", new JSONArray().put("forest").put("desert").put("mountain"));
        extras.put("creeks",new JSONArray("[]"));
        extras.put("sites",new JSONArray("[]"));
        mapInfo.sitesCordsStart();
        mapInfo.processBiomes(extras,cords);
        assertEquals(3, mapInfo.getBiomes().size());
        assertTrue(mapInfo.getBiomes().contains("forest"));
        assertTrue(mapInfo.getBiomes().contains("desert"));
        assertTrue(mapInfo.getBiomes().contains("mountain"));
    }

    @Test
    void processBiomesCreeks() {
        JSONObject extras = new JSONObject().put("creeks", new JSONArray().put("creek1"));
        cords.droneCordsStart();
        extras.put("biomes",new JSONArray("[]"));
        extras.put("sites",new JSONArray("[]"));
        mapInfo.sitesCordsStart();
        mapInfo.processBiomes(extras,cords);
        JSONObject moreExtras = new JSONObject().put("creeks", new JSONArray().put("creek2"));
        moreExtras.put("biomes",new JSONArray("[]"));
        moreExtras.put("sites",new JSONArray("[]"));
        mapInfo.processBiomes(moreExtras,cords);
        assertEquals(2, mapInfo.getCreeks().size());
        assertEquals("creek1", mapInfo.getCreek0ID());
        assertEquals("creek2", mapInfo.getCreekXID(1));

    }

    @Test
    void processBiomesSites() {
        JSONObject extras = new JSONObject().put("sites", new JSONArray().put("site1"));
        extras.put("biomes",new JSONArray("[]"));
        extras.put("creeks",new JSONArray("[]"));
        mapInfo.sitesCordsStart();
        cords.droneCordsStart();
        mapInfo.processBiomes(extras,cords);
        JSONObject moreExtras = new JSONObject().put("sites", new JSONArray().put("site2"));
        moreExtras.put("biomes",new JSONArray("[]"));
        moreExtras.put("creeks",new JSONArray("[]"));
        mapInfo.processBiomes(moreExtras,cords);
        assertEquals(2, mapInfo.getSites().size());
    }

}
