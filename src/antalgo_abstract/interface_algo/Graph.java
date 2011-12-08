package antalgo_abstract.interface_algo;

import java.util.List;
import java.util.Set;

public interface Graph {

	Set<State> getNeighborStates(State state);
	void evaporatePheromone();
	void depositPheromone_StepByStep(State state, State nextState);
	void depositPheromone_Delayed(List<State> path);
	double getDistance(State state, State otherState);
	double getPheromon(State state, State otherState);
	boolean isSolution(List<State> path);
	State getStartState();

}
