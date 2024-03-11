package ca.mcmaster.se2aa4.island.team305;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

interface ReaderInter{
    void fileReader(JSONObject info, Boolean scan_status, String heading, DroneData data);
    String actionInfo();
    Integer getMoveCost();
}
public class Reader implements ReaderInter{
    private final Logger logger = LogManager.getLogger();
    private String information;
    private Integer moveCost;

    private Integer range_N;

    private Integer range_W;

    private Integer range_S;

    private Integer range_E;

    private List<String> biomes;

    private List<String> creeks;

    private List<String> sites;

    @Override
    public void fileReader(JSONObject info, Boolean scan_status, String heading, DroneData data) {
        moveCost = info.getInt("cost");
        data.batteryAction(moveCost);
        if (scan_status) {
            JSONObject extra_data = info.getJSONObject("extras");
            information = extra_data.getString("found");
            switch (heading) {
                case "N" -> range_N = extra_data.getInt("range");
                case "W" -> range_W = extra_data.getInt("range");
                case "S" -> range_S = extra_data.getInt("range");
                case "E" -> range_E = extra_data.getInt("range");
            }
        }
    }
    @Override
    public String actionInfo() {
        return information;
    }
    @Override
    public Integer getMoveCost(){
        return moveCost;
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
                return 0;
            }
        }
    }
    public List<String> getBiomes(){
        return biomes;
    }
    public List<String> getCreeks(){
        return creeks;
    }
    public List<String> getSites(){
        return sites;
    }

    public void processBiomes(JSONObject extras) {
        if (biomes != null) {
            biomes.clear(); //so we only have biomes of our current space
        }
        JSONArray biomesInfo = extras.getJSONArray("biomes");
        biomes = jsonArrayConvert(biomesInfo);
        JSONArray creeksInfo = extras.getJSONArray("creeks");
        creeks = jsonArrayConvert(creeksInfo);
        JSONArray sitesInfo = extras.getJSONArray("sites");
        sites = jsonArrayConvert(sitesInfo);
    }
    private List<String> jsonArrayConvert(JSONArray jsonArray){
        List<String> list = new ArrayList<>();
        for (Object value : jsonArray){
            list.add(String.valueOf(value));
        }
        return list;
    }
}
