
package ca.mcmaster.se2aa4.island.team305;

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
}

