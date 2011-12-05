package adp2.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
//    void step();

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
    double pathLength();

    /**
     * Alpha value for balance computation between pheromones and distance
     *  
     * @return double - (0.0 - 1.0)
     */
    double alpha();

    /**
     * The position before the current position. If the ant hasn't traveled so far. 
     * The return value is the number of the starting node 
     * 
     * @return int - node of the position before the current
     */
    int prevPosition();
    
	Set<Integer> getUnvisitedNodes();

	List<Integer> getPath();
	
	Map<Integer, Double> balances();

	void setFinished(boolean b);

	double sumOfValues(Map<?, Double> probabilities);
	
	void setPathLength(double pathLength);
}