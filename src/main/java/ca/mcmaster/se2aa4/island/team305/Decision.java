package ca.mcmaster.se2aa4.island.team305;

public class Decision {

    Battery costs;

    public String DetermineAct(DroneData data, Scanner scan) {
        String action = null;
        return action;

    private static int moveCounter = 0;
    public static String[] move() {
        //Done just to get the drone movign finally and we can see progress on the map
        if (moveCounter < 40 && moveCounter % 2 == 0) {
            moveCounter++;
            return new String[]{"action", "fly"};
        }
        if (moveCounter < 40 && moveCounter % 2 == 1) {
            moveCounter++;
            return new String[]{"action", "scan"}; //this is how scan works and it revals parts of the map on the svg file (try it out)
        }
        else {
            return new String[]{"action", "stop"};
        }
    }
}
