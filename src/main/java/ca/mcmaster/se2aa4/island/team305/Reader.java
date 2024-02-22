package ca.mcmaster.se2aa4.island.team305;
import org.json.JSONObject;
import org.json.JSONTokener;
import eu.ace_design.island.bot.IExplorerRaid;
import java.io.StringReader;

public class Reader {
    private String heading;
    private Integer batteryLevel;
    public void fileReader(String s) {
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        heading = info.getString("heading");
        batteryLevel = info.getInt("budget");
    }
    public String getDirection(){
        return heading;
    }
    public Integer getBatteryLevel(){
        return batteryLevel;
    }

}
