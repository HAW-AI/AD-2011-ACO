package antalgo_abstract.impl_demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import antalgo_abstract.interface_algo.Ant;
import antalgo_abstract.interface_algo.AntFactory;
import antalgo_abstract.interface_algo.State;

public class MrAnt implements Ant, AntFactory {

	private boolean death = false;
	private List<State> path = new ArrayList<State>();
	private boolean solutionFound = false;

	public MrAnt(State startState) {
		if(startState == null) death = true;
		
		path.add(startState);
	}

	@Override
	public Ant antAlgo_create(State startState) {
		return new MrAnt(startState);
	}

	@Override
	public double antAlgo_getWeight(double distance, double pheromon) {
		return distance - pheromon;
	}

	@Override
	public double antAlgo_selectResult(Set<Double> sortedWeights) {
		return sortedWeights.iterator().next();
	}

	@Override
	public State getState() {
		return path.get(path.size()-1);
	}

	@Override
	public Set<State> getNoSecoundVisitStates() {
		Set<State> set = new HashSet<State>(path);
		return set;
	}

	@Override
	public boolean solutionFound() {
		return solutionFound;
	}

	@Override
	public void kill() {
		death = true;
	}

	@Override
	public void nextState(State nextState) {
		path.add(nextState);
	}

	@Override
	public List<State> getPath() {
		return path;
	}

	@Override
	public boolean isDeath() {
		return death;
	}

	@Override
	public void setSolutionFound() {
		solutionFound = true;
	}

}
