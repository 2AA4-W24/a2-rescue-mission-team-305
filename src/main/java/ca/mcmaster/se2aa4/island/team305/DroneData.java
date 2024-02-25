package ca.mcmaster.se2aa4.island.team305;

public class DroneData {
    private String heading;

    public DroneData(String h) {
        heading = h;
    }

    public DroneData getData() {
        return new DroneData(heading);
    }
}
