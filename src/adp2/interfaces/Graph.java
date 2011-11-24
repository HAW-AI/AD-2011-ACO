package adp2.interfaces;

import java.util.Set;

public interface Graph {

	/**
	 * Get distance between two nodes
	 * @param von - start node
	 * @param nach - end node
	 * @return Double - distance 
	 */
	Double distance(int von, int nach);

	/**
	 * Neighbor nodes of one node
	 * @param node
	 * @return Set<Integer> of nodes
	 */
	Set<Integer> neighbors(int node);

	/**
	 * All nodes of the current graph
	 * @return Set<Integer> all nodes of the graph
	 */
	Set<Integer> allNodes();

	/**
	 * Pheromone intensity between on the edge between two nodes
	 * @param von
	 * @param nach
	 * @return double - intensity of pheromone on edge
	 */
	double intensity(int von, int nach);

	/**
	 * Decrement pheromones on all edges by current value
	 * @param value - to decrement intensity by
	 */
	void decrementPheromone(double value);
	
	/**
	 * Add a value of pheromone intensity to the edge from start to end
	 * @param start - start node
	 * @param end - end node
	 * @param pheromone - value to add on intensity of edge
	 */
	public void incrementPheromone(int start, int end, double pheromone);
	
	/**
	 * display the path
	 * @param p
	 */
	public void highlightPath(Path p);

	/**
	 * deepClone()
	 * @return a copy of the graph where all fields are also copies of their original values.
	 */
	public Graph deepClone();



}
