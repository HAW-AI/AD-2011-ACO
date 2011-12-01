package adp2.interfaces;

import java.util.Map;

public interface Simulation {
	
	
	/**
	 * Starts the simulation process
	 * 
	 */
	void run();
	
	/**
	 * Starts the simulation process
	 * 
	 */
	void runForSeconds(int runtimeInS);
	
	/**
	 * Starts the simulation process
	 * 
	 */
	void runForSteps(int simulationSteps);
	
	/**
	 * Returns the shortest path found for the simulation
	 * 
	 * @return shortest path
	 */
	Path minPath();

}
