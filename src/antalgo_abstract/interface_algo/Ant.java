package antalgo_abstract.interface_algo;

import java.util.List;
import java.util.Set;

public interface Ant {

	State getState();
	Set<State> getNoSecoundVisitStates();
	boolean solutionFound();
	void kill();
	void nextState(State nextState);
	List<State> getPath();
	boolean isDeath();
	void setSolutionFound();
	
}
