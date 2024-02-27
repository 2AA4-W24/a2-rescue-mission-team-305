package ca.mcmaster.se2aa4.island.team305;

public class Battery {

    private Integer budget;

    public Battery(Integer b) {
        budget = b;
    }

    public Battery getBattery() {
        return new Battery(budget);
    }

    public void action(Integer cost) {
        budget -= cost;
    }
}
