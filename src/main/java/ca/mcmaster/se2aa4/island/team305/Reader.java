package ca.mcmaster.se2aa4.island.team305;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface ReaderInter{
    void fileReader(JSONObject info, Boolean scan_status, String heading, DroneData data);
    String actionInfo(String heading);
    Integer getMoveCost();
    void sitesCordsStart();
    void processBiomes(JSONObject extras);
}
public class Reader implements ReaderInter{
    private final Logger logger = LogManager.getLogger();
    private String information_N;
    private String information_W;
    private String information_S;
    private String information_E;
    private Integer moveCost;

    private Integer range_N;

    private Integer range_W;

    private Integer range_S;

    private Integer range_E;

    public List<String> biomes;

    private List<String> creeks;

    private List<String> sites;

    private Cords Cords;
    public Integer creekCounter;
    private Integer siteCounter;
    Map<String, int[]> creekStorage = new HashMap<>();
    Map<String, int[]> siteStorage = new HashMap<>();

    @Override
    public void fileReader(JSONObject info, Boolean scan_status, String heading, DroneData data) {
        moveCost = info.getInt("cost");
        data.batteryAction(moveCost);
        if (scan_status) {
            JSONObject extra_data = info.getJSONObject("extras");
            switch (heading) {
                case "N" -> {
                    range_N = extra_data.getInt("range");
                    information_N = extra_data.getString("found");
                }
                case "W" -> {
                    range_W = extra_data.getInt("range");
                    information_W = extra_data.getString("found");
                }
                case "S" -> {
                    range_S = extra_data.getInt("range");
                    information_S = extra_data.getString("found");
                }
                case "E" -> {
                    range_E = extra_data.getInt("range");
                    information_E = extra_data.getString("found");
                }
            }
        }
    }
    @Override
    public String actionInfo(String heading) {
        switch (heading) {
            case "N" -> {
                return information_N;
            }
            case "W" -> {
                return information_W;
            }
            case "S" -> {
                return information_S;
            }
            case "E" -> {
                return information_E;
            }
            default -> {
                return null; //Should throw error if input is not expected!
            }
        }
    }
    @Override
    public Integer getMoveCost(){
        return moveCost;
    }

    @Override
    public void sitesCordsStart() {
        creekCounter = 0;
        siteCounter = 0;
    }

    public Integer getRange(String heading) {
        switch (heading) {
            case "N" -> {
                return range_N;
            }
            case "W" -> {
                return range_W;
            }
            case "S" -> {
                return range_S;
            }
            case "E" -> {
                return range_E;
            }
            default -> {
                return 0; //Should throw error if input is not expected!
            }
        }
    }

    @Override
    public void processBiomes(JSONObject extras) {
        if (biomes != null) {
            biomes.clear(); //so we only have biomes of our current space
        }
        try {
        JSONArray biomesInfo = extras.getJSONArray("biomes");
        biomes = jsonArrayConvert(biomesInfo);} catch (Exception e) {
            logger.info("Biomes were skipped");
        }
        try {
        JSONArray creeksInfo = extras.getJSONArray("creeks");
        creeks.addAll(jsonArrayConvert(creeksInfo));
        creekCordStorage(creeks.get(creekCounter));
        creekCounter += 1;} catch (Exception e) {
            logger.info("CREEK ID was skipped");
        } try {
        JSONArray sitesInfo = extras.getJSONArray("sites");
        sites.addAll(jsonArrayConvert(sitesInfo));
        siteCordStorage(sites.get(siteCounter));
        siteCounter += 1; } catch (Exception e) {
            logger.info("Site ID was skipped");
        }// try catch blocks can be tech debt and how it just easier doing it this way to save time
    }
    private List<String> jsonArrayConvert(JSONArray jsonArray){
        List<String> list = new ArrayList<>();
        for (Object value : jsonArray){
            list.add(String.valueOf(value));
        }
        return list;
    }

    private void creekCordStorage(String id) {
        creekStorage.put(id, new int[]{Cords.getEastWestCord(),Cords.getNorthSouthCord()});
    }
    private void siteCordStorage(String id) {
        siteStorage.put(id, new int[]{Cords.getEastWestCord(),Cords.getNorthSouthCord()});
    }

    public int[] getCreekCord(String id){
        int[] cords = creekStorage.get(id);
        return cords;
    }
    public int[] GetSiteCord(String id){
        int[] cords = creekStorage.get(id);
        return cords;
    }
    public String getCreek0ID(){
        return creeks.get(0);
    }
    public String getCreekXID(int num){
        return creeks.get(num);
    }
}
