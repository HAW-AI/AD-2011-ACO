package adp2.interfaces;

import java.util.Set;

public interface Graph {

    /**
     * Get distance between two nodes
     * @param von start node
     * @param nach end node
     * @return distance 
     */
    double distance(int von, int nach);

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
     * Pheromone intensity the edge between two nodes
     * @param von
     * @param nach
     * @return intensity of pheromone on edge
     */
    double intensity(int from, int to);

    /**
     * Decrement pheromones on all edges by current value
     * @param value - to decrement intensity by
     */
    void decrementPheromones(double value);

    /**
     * Add a value of pheromone intensity to the edge from start to end
     * @param start - start node
     * @param end - end node
     * @param pheromone - value to add on intensity of edge
     */
    void incrementPheromones(int start, int end, double pheromone);

    /**
     * deepClone()
     * @return a copy of the graph where all fields are also copies of their original values.
     */
    Graph deepClone();
	
	/**
	 * Used by PrintGraph to access distance matrix
	 */
	Matrix<Double> distanceVar();
}