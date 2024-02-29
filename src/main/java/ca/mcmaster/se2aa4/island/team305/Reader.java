package ca.mcmaster.se2aa4.island.team305;
import org.json.JSONObject;
import org.json.JSONTokener;
import eu.ace_design.island.bot.IExplorerRaid;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reader {
    private String heading;
    private Integer batteryLevel;
    private final Logger logger = LogManager.getLogger();

    public void fileReader(String s) { //this doesn't work
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s))); //this line is broken
        //IDK how to get the exploro island json file to be read mid run as right now it auto crashing on this line every time
        //Thats why the try and accept block is in explorer take desision
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
