package adp2.implementations;

import adp2.implementations.Values;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import adp2.interfaces.Ant;
import adp2.interfaces.Path;

public class NaA implements Ant {

    public static NaA instance;

    protected static Ant create() {
        if (instance == null) {
            instance = new NaA();
        }
        return instance;
    }

    private NaA() {
    }

    public Path traveledPath() {
        return Values.NaP();
    }

    public int position() {
        return -1;
    }

    public boolean hasFinished() {
        return false;
    }

    public double pathLength() {
        return Double.NaN;
    }

    public int prevPosition() {
        return -1;
    }

    public Set<Integer> getUnvisitedNodes() {
	    return new HashSet<Integer>();
    }

    public List<Integer> getPath() {
	    return new ArrayList<Integer>();
    }

    public Map<Integer, Double> balances(double alpha) {
	    return new HashMap<Integer, Double>();
    }

    public void updatePathLength(int minNode) {
    }

    public void finish() {
    }
}
