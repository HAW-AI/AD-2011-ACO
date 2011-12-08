package adp2.implementations;

import adp2.implementations.Values;
import java.util.HashSet;
import java.util.Set;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;

public class NaG implements Graph {

    public static Graph instance;

    protected static Graph create() {
        if (instance == null) {
            instance = new NaG();
        }
        return instance;
    }

    private NaG() {
    }

    public double distance(int from, int to) {
        return Double.POSITIVE_INFINITY;
    }
	
    public Set<Integer> neighbors(int node) {
        return new HashSet<Integer>();
    }
    public Set<Integer> allNodes() {
        return new HashSet<Integer>();
    }

    public double intensity(int from, int to) {
        return -1;
    }

    public void decrementPheromones(double value) {
    }


    public void incrementPheromones(int start, int end, double pheromone) {
    }

    public Graph deepClone() {
        return create();
    }
	
	public Matrix<Double> distanceVar() {
		return Values.NaM();
	}
}
