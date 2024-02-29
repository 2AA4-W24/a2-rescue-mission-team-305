package ca.mcmaster.se2aa4.island.team305;
interface BatteryInter {
    void batteryAction();
    Integer getBatteryLevel();
}
public class Battery extends DroneData implements BatteryInter {
    private int batteryLevel = 7000;
    @Override
    public void batteryAction() {
        batteryLevel -= reader.getMoveCost();
    }
    @Override
    public Integer getBatteryLevel(){
        return batteryLevel;
    }
}

