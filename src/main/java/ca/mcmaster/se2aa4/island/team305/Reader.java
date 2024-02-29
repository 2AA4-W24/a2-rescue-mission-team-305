package ca.mcmaster.se2aa4.island.team305;
import org.json.JSONObject;
import org.json.JSONTokener;
import eu.ace_design.island.bot.IExplorerRaid;
import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
interface ReaderInter{
    void fileReader(String s);
    String actionInfo();
    Integer getMoveCost();

}
public class Reader implements ReaderInter{
    private final Logger logger = LogManager.getLogger();
    private String information;
    private Integer moveCost;
    @Override
    public void fileReader(String s) {
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        moveCost = info.getInt("cost");
        information = info.getString("extras");
    }
    @Override
    public String actionInfo(){
        return information;
    }
    @Override
    public Integer getMoveCost(){
        return moveCost;
    }

}
