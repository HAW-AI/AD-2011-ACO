package antalgo_abstract.impl_demo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import antalgo_abstract.interface_algo.Graph;
import antalgo_abstract.interface_algo.State;

/**
 * it isn't ready yet
 * there must be some states and edges between them
 *
 */
public class MrGraph implements Graph {

	@Override
	public Set<State> getNeighborStates(State state) {
		return new HashSet<State>();
	}

	@Override
	public void evaporatePheromone() {
		// TODO Auto-generated method stub
	}

	@Override
	public void depositPheromone_StepByStep(State state, State nextState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void depositPheromone_Delayed(List<State> path) {
		// TODO Auto-generated method stub
	}

	@Override
	public double getDistance(State state, State otherState) {
		return 1;
	}

	@Override
	public double getPheromon(State state, State otherState) {
		return 1;
	}

	@Override
	public boolean isSolution(List<State> path) {
		return false;
	}

	@Override
	public State getStartState() {
		return new MrState();
	}

}
