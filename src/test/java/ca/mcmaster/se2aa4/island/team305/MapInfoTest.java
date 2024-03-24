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
        assertNull(mapInfo.creeks);
    }

    @Test
    void processBiomesBiomes() {
        JSONObject extras = new JSONObject()
                .put("biomes", new JSONArray().put("forest").put("desert").put("mountain"));
        extras.put("creeks",new JSONArray("[]"));
        extras.put("sites",new JSONArray("[]"));
        mapInfo.sitesCordsStart();
        mapInfo.processBiomes(extras,cords);
        assertEquals(3, mapInfo.biomes.size());
        assertTrue(mapInfo.biomes.contains("forest"));
        assertTrue(mapInfo.biomes.contains("desert"));
        assertTrue(mapInfo.biomes.contains("mountain"));
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
        assertEquals(2, mapInfo.creeks.size());
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
        assertEquals(2, mapInfo.sites.size());
    }

}
