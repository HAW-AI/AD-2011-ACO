package adp2.implementations;

import adp2.interfaces.PheromoneElement;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class PheromoneElementImpl implements PheromoneElement {

    int from;
    int to;
    double pheromone;

    private PheromoneElementImpl(int from, int to, double pheromone) {
        this.from = from;
        this.to = to;
        this.pheromone = pheromone;
    }

    static PheromoneElementImpl valueOf(int from, int to, double pheromone) {
        return new PheromoneElementImpl(from, to, pheromone);
    }

    public int from() {
        return from;
    }

    
    public int to() {
        return to;
    }

    public double pheromone() {
        return pheromone;
    }
	
    @Override
	public String toString() {
		return "Pheromone: " + from + " to " + to + ": " + pheromone;
	}
}
