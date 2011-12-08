package adp2.algorithms.interfaces;

public abstract interface Simulation {

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
}
