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

    private String biome;

    @Override
    public void fileReader(JSONObject info, Boolean scan_status, String heading, DroneData data) {
        logger.info("file reader called");
        logger.info(info.toString());
        moveCost = info.getInt("cost");
        data.batteryAction(moveCost);
        logger.info("cost checked.");
        if (scan_status) {
            JSONObject extra_data = info.getJSONObject("extras");
            logger.info("JSONObject created.");
            information = extra_data.getString("found");
            logger.info("extrainfo saved");
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
        String info_copy = information;
        return info_copy;
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
    private List<String> biomes;
    private List<String> creeks;
    private List<String> sites;
    private void setBiomes(List<String> biomes){
        this.biomes = biomes;
    }
    private void clearBiomes(){ //to be used to clear biome memory of each space
        biomes.clear();
    }
    private void setCreeks(List<String> creeks){
        this.creeks = creeks;
    }
    private void setSites(List<String> sites){
        this.sites = sites;
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
        clearBiomes();//so we only have biomes of our current space
        JSONArray biomesInfo = extras.getJSONArray("biomes");
        setBiomes(jsonArrayConvert(biomesInfo));
        JSONArray creeksInfo = extras.getJSONArray("creeks");
        setCreeks(jsonArrayConvert(creeksInfo));
        JSONArray sitesInfo = extras.getJSONArray("sites");
        setSites(jsonArrayConvert(sitesInfo));
    }
    private List<String> jsonArrayConvert(JSONArray jsonArray){
        List<String> list = new ArrayList<>();
        for (Object value : jsonArray){
            list.add(String.valueOf(value));
        }
        return list;
    }
}
