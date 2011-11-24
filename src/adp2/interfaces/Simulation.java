package adp2.interfaces;

import java.util.Map;

public interface Simulation {
	/**
	 * The results of the simulation as frequency map
	 * 
	 * @return frequency map of traveled paths
	 */
	Map<Path, Integer>frequencyMap();
	
	
	/**
	 * Starts the simulation process
	 * 
	 */
	void start();
	
	/**
	 * Returns the shortest path found for the simulation
	 * 
	 * @return shortest path
	 */
	Path minPath();

}
