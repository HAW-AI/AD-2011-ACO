package adp2.interfaces;


public interface Ant {
	
	/**
	 * Path the ant has traveled
	 * 
	 * @return Path traveled Path
	 */
	Path traveledPath();
	
	/**
	 * Current position of the ant
	 * 
	 * @return int current node, where the ant is
	 */
	int position();
	
	/**
	 * Compute one step. One step does not mean, that the ant moves. 
	 * This is only the case, when the ant the whole distance between two nodes has been traveled.
	 * 
	 */
	void step();
	
	/**
	 * Has the ant visited all nodes and returned home?
	 * 
	 * @return boolean - has the ant finished
	 */
	boolean hasFinished();
	
	/**
	 * The distance the ant has traveled so far
	 * 
	 * @return int - traveled distance
	 * 
	 */
	int weglaenge();
	
	/**
	 * Alpha value for balance computation between pheromones and distance
	 *  
	 * @return double - (0.0 - 1.0)
	 */
	double alpha();
	
	/**
	 * The time the ant has to wait until the ant can move to the next node.
	 * Depends on the distance between the current and the next node.
	 * One call of step  reduces this value by one.
	 *  
	 * @return double - waiting time
	 */
	double getWaitingTime();
	
	/**
	 * The position before the current position. If the ant hasn't traveled so far. 
	 * The return value is the number of the starting node 
	 * 
	 * @return int - node of the position before the current
	 */
	int prePosition();
}


