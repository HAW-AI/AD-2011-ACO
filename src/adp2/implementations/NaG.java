package adp2.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adp2.interfaces.Graph;

public class NaG implements Graph {

	@Override
	public int distance(int von, int nach) {
		return -1;
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
	public double intensity(int von, int nach) {
		return -1;
	}

	@Override
	public void decrementPheromone(int value) {}

	@Override
	public List<Integer> pointsBetween(int von, int bis) {
		return new ArrayList<Integer>();
	}

	@Override
	public int minDist(int von, int bis) {
		return -1;
	}

}
