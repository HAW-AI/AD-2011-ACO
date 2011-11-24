package adp2.interfaces;

import java.util.List;
import java.util.Set;

public interface Graph {

	Double distance(int von, int nach);

	Set<Integer> neighbors(int node);

	Set<Integer> allNodes();

	double intensity(int von, int nach);

	void decrementPheromone(int value);
	
	public void incrementPheromone(int start, int end, double pheromone);
	
	public void highlightPath(Path p);

	/**
	 * deepClone()
	 * @return a copy of the graph where all fields are also copies of their original values.
	 */
	public Graph deepClone();



}
