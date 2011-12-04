package adp2.implementations;

import adp2.interfaces.PheromoneElement;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class NaPE implements PheromoneElement {

    public static PheromoneElement instance;

    protected static PheromoneElement create() {
        if (instance == null) {
            instance = new NaPE();
        }
        return instance;
    }

    private NaPE() {
    }

    @Override
    public int from() {
        return -1;
    }

    @Override
    public int to() {
        return -1;
    }

    @Override
    public double pheromone() {
        return -1;
    }
}
