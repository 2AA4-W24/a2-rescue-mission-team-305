package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface MapInfoInter{
    int[] getCreekCord(String id);

    int[] GetSiteCord(String id);

    String getCreek0ID();

    String getCreekXID(int num);

    String getSiteID();

    void sitesCordsStart();
    void processBiomes(JSONObject extras, Cords cord);
}
public class MapInfo implements MapInfoInter{
    private final Logger logger = LogManager.getLogger();
    public List<String> biomes;
    public List<String> creeks;
    public List<String> sites;
    public Integer creekCounter;
    public Integer siteCounter;
    public Map<String, int[]> creekStorage = new HashMap<>();
    public Map<String, int[]> siteStorage = new HashMap<>();
    @Override
    public void processBiomes(JSONObject extras, Cords cord) {
        if (biomes != null) {
            biomes.clear(); //so we only have biomes of our current space
        }
        try {
            JSONArray biomesInfo = extras.getJSONArray("biomes");
            biomes = jsonArrayConvert(biomesInfo, 2);} catch (Exception e) {
            logger.info("Biomes were skipped");
        }
        JSONArray creeksInfo = extras.getJSONArray("creeks");
        if (!creeksInfo.isEmpty()) {
            List<String> temp = jsonArrayConvert(creeksInfo, 0);
            if (creeks == null) {
                creeks = temp;
            }
            else {
                creeks.addAll(temp);
            }
            creekCordStorage(creeks.get(creekCounter), cord);
        }
        JSONArray sitesInfo = extras.getJSONArray("sites");
        if (!sitesInfo.isEmpty()) {
            List<String> temp2 = jsonArrayConvert(sitesInfo, 1);
            if (sites == null) {
                sites = temp2;
            }
            else {
                sites.addAll(temp2);
            }
            siteCordStorage(sites.get(siteCounter), cord);
        }
    }
    private List<String> jsonArrayConvert(JSONArray jsonArray, Integer creek_status){
        List<String> list = new ArrayList<>();
        for (Object value : jsonArray){
            list.add(String.valueOf(value));
            if (creek_status == 0) {
                if (creeks != null) {
                    creekCounter++;
                }
            }
            else if (creek_status == 1) {
                if (sites != null) {
                    siteCounter++;
                }
            }
        }
        return list;
    }

    private void creekCordStorage(String id, Cords cord) {
        creekStorage.put(id, new int[]{cord.getEastWestCord(), cord.getNorthSouthCord()});
    }
    private void siteCordStorage(String id, Cords cord) {
        siteStorage.put(id, new int[]{cord.getEastWestCord(), cord.getNorthSouthCord()});
    }

    @Override
    public int[] getCreekCord(String id){
        int[] cords = creekStorage.get(id);
        return cords;
    }
    @Override
    public int[] GetSiteCord(String id){
        int[] cords = siteStorage.get(id);
        return cords;
    }
    @Override
    public String getCreek0ID(){
        return creeks.get(0);
    }
    @Override
    public String getCreekXID(int num){
        return creeks.get(num);
    }
    @Override
    public String getSiteID() {
        return sites.get(0);
    }

    @Override
    public void sitesCordsStart() {
        creekCounter = 0;
        siteCounter = 0;
    }

}
