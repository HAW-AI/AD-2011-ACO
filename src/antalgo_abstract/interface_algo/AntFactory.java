package antalgo_abstract.interface_algo;

import java.util.Set;

public interface AntFactory {

	Ant antAlgo_create(State startState);
	double antAlgo_getWeight(double distance, double pheromon);
	double antAlgo_selectResult(Set<Double> sortedWeights);

}
