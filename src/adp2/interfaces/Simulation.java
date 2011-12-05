package adp2.interfaces;

public interface Simulation {

    /**
     * Starts the simulation process
     * 
     */
    void run();

    /**
     * Starts the simulation process; terminates after the specified seconds
     * 
     */
    void runForSeconds(int runtimeInS);

    /**
     * Starts the simulation process; terminates after the specified number 
     * of steps
     * 
     */
    void runForSteps(int simulationSteps);

    /**
     * Returns the shortest path found for the simulation
     * 
     * @return shortest path
     */
    Path minPath();

	void stochasticNeighborSelection(Ant ant);
}
