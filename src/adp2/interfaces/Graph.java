package adp2.interfaces;

import java.util.List;
import java.util.Set;

public interface Graph {

	int distance(int von, int nach);

	Set<Integer> neighbors(int node);

	Set<Integer> allNodes();

	double intensity(int von, int nach);

	void decrementPheromone(int value);
	
	public void incrementPheromone(List<List<Integer>> pheromoneUpdateList);
	
	public void highlightPath(Path p);

    /**
     * Points between two nodes
     * 
     * @param von Startnode
     * @param bis Endnode
     * @return Integer-List that includes von,bis and 
     * all nodes of the shortest way between them
     */
	List<Integer> pointsBetween(int von, int bis);

    /**
     * minimum distance between two nodes
     * 
     * @param von Startnode
     * @param bis Endnode
     * @return distance of the shortest way between Start- and Endnode
     */
	int minDist(int von, int bis);

}
