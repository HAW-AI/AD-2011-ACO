package adp2.implementations;

import java.util.HashSet;
import java.util.Set;

import adp2.interfaces.Graph;
import adp2.interfaces.Path;

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

    @Override
    public double distance(int from, int to) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Set<Integer> neighbors(int node) {
        return new HashSet<Integer>();
    }

    @Override
    public Set<Integer> allNodes() {
        return new HashSet<Integer>();
    }

    @Override
    public double intensity(int from, int to) {
        return -1;
    }

    @Override
    public void decrementPheromones(double value) {
    }

    @Override
    public void highlightPath(Path p) {
    }

    @Override
    public void incrementPheromones(int start, int end, double pheromone) {
    }

    @Override
    public Graph deepClone() {
        return create();
    }
}
