package adp2.implementations;

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

    @Override
    public Path traveledPath() {
        return Values.NaP();
    }

    @Override
    public int position() {
        return -1;
    }

//    @Override
//    public void step() {
//    }

    @Override
    public boolean hasFinished() {
        return false;
    }

    @Override
    public double pathLength() {
        return Double.NaN;
    }

    @Override
    public double alpha() {
        return 0;
    }

    @Override
    public int prevPosition() {
        return -1;
    }

	@Override
    public Set<Integer> getUnvisitedNodes() {
	    return new HashSet<Integer>();
    }

	@Override
    public List<Integer> getPath() {
	    return new ArrayList<Integer>();
    }

	@Override
    public Map<Integer, Double> balances() {
	    return new HashMap<Integer, Double>();
    }

	@Override
    public void updatePathLength(int minNode) {
    }

	@Override
    public void finish() {
    }
}
