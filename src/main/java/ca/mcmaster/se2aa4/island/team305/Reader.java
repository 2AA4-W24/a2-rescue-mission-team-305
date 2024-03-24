package ca.mcmaster.se2aa4.island.team305;
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
    Integer getRange(String heading);
    Integer getMoveCost();
}
public class Reader implements ReaderInter{
    private final Logger logger = LogManager.getLogger();
    private String information_N;
    private String information_W;
    private String information_S;
    private String information_E;
    public Integer moveCost;
    private Integer range_N;
    private Integer range_W;
    private Integer range_S;
    private Integer range_E;

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
    public Integer getMoveCost(){
        return moveCost;
    }

}
