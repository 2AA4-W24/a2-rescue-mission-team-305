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
    public Map<String, int[]> creekStorage;
    public Map<String, int[]> siteStorage;

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
        creekStorage = new HashMap<>();
        siteStorage = new HashMap<>();
        biomes = new ArrayList<>();
        creeks = new ArrayList<>();
        sites = new ArrayList<>();
        Cords = new Cords();
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
        try {
        JSONArray biomesInfo = extras.getJSONArray("biomes");
        if (biomesInfo != null && biomesInfo.length() > 0) {
            if (biomes == null) {
                biomes = new ArrayList<>();
            } else {
                biomes.clear();
            }
            biomes.addAll(jsonArrayConvert(biomesInfo));}
            logger.info(biomes);
        } catch (Exception e) {
            logger.info("Biomes were skipped");
        }
        try {
            JSONArray creeksInfo = extras.getJSONArray("creeks");
            logger.info("AAAAAAAAA");
            if (creeksInfo != null && creeksInfo.length() > 0) {
                logger.info("BBBBBBBBB");
                if (creeks == null) {
                    creeks = new ArrayList<>();
                    logger.info("CCCCCCCCC");
                }
                List<String> listcreeks = jsonArrayConvert(creeksInfo);
                logger.info("DDDDDDDDD");
                int maxCreeks = Math.min(6, listcreeks.size());
                logger.info("EEEEEEEEEEEEE");
                for (int i = 0; i < maxCreeks; i++) {
                    String creek = listcreeks.get(i);
                    creeks.add(creek);
                    logger.info("FOR LOOPPPPPPPP");
                    int xCord = Cords.getEastWestCord();//idk why but giving errors when running test
                    int yCord = Cords.getNorthSouthCord(); //idk why but giving errors when running test
                    logger.info("FOR 22222222222");
                    creekCordStorage(creek,xCord,yCord);
                    logger.info("CREEKS ADDED");
                }
                logger.info("FFFFFFFFFFFF");
            }
            logger.info("GGGGGGGGGGGGG");
        } catch (Exception e) {
            logger.info(e);
            logger.info("CREEK ID was skipped");
        }
        try {
            JSONArray sitesInfo = extras.getJSONArray("sites");
            if (sitesInfo != null && sitesInfo.length() > 0) {
                if (sites == null) {
                    sites = new ArrayList<>();
                }
                List<String> listSites = jsonArrayConvert(sitesInfo);
                int maxSites = Math.min(2, listSites.size());
                for (int i = 0; i < maxSites; i++) {
                    String site = listSites.get(i);
                    sites.add(site);
                    int xCord = Cords.getEastWestCord(); //idk why but giving errors when running test
                    int yCord = Cords.getNorthSouthCord(); //idk why but giving errors when running test
                    siteCordStorage(site,xCord,yCord);
                }
            }
        } catch (Exception e) {
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

    public void creekCordStorage(String id, int xCord,int yCord) {//public for testing purposes
        creekStorage.put(id, new int[]{xCord,yCord});
    }
    public void siteCordStorage(String id, int xCord,int yCord) {//public for testing purposes
        siteStorage.put(id, new int[]{xCord,yCord});
    }

    public int[] getCreekCord(String id){
        int[] cords = creekStorage.get(id);
        return cords;
    }
    public int[] getSiteCord(String id){
        int[] cords = siteStorage.get(id);
        return cords;
    }
    public int[] getSiteCord0(){
        String i = sites.getFirst();
        int[] cords = siteStorage.get(i);
        return cords;
    }
    public String getCreek0ID(){
        return creeks.get(0);
    }
    public String getCreekXID(int num){
        return creeks.get(num);
    }

    public Integer getCreekSize(){
        return creeks.size();
    }
    public Integer getSiteSize(){
        return sites.size();
    }



}