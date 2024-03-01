
package ca.mcmaster.se2aa4.island.team305;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Scanner {
    String N_delta;

    String W_delta;

    String S_delta;

    String E_delta;

    String biome;

    public void scan() {
        biome = "";
    }

    public void radio() {
        N_delta = "";
        W_delta = "";
        S_delta = "";
        E_delta = "";
    }

    public Scanner getScans() {
        Scanner copy = new Scanner();
        copy.biome = biome;
        copy.N_delta = N_delta;
        copy.W_delta = W_delta;
        copy.S_delta = S_delta;
        copy.E_delta = E_delta;
        return copy;
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
    public List<String> getsites(){
        return sites;
    }

    public void processBiomes(JSONObject extras) {
        JSONArray biomesInfo = extras.getJSONArray("biomes");
        setBiomes(jsonArrayConvert(biomesInfo));
        JSONArray creeksInfo = extras.getJSONArray("creeks");
        setCreeks(jsonArrayConvert(creeksInfo));
        JSONArray sitesInfo = extras.getJSONArray("sites");
        setSites(jsonArrayConvert(sitesInfo));
    }
    private List<String> jsonArrayConvert(JSONArray jsonArray){ //do we want to convert biomes and extras to a string or change it later
        List<String> list = new ArrayList<>();
        for (Object value : jsonArray){
            list.add(String.valueOf(value));
        }
        return list;
    }
}

