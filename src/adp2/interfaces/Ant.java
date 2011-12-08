package adp2.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Ant {

    /**
     * Path the ant has traveled
     * 
     * @return traveled Path
     */
    Path traveledPath();

    /**
     * Current position of the ant
     * 
     * @return current node, where the ant is
     */
    int position();

    /**
     * Has the ant visited all nodes and returned home?
     * 
     * @return has the ant finished
     */
    boolean hasFinished();

    /**
     * The distance the ant has traveled so far
     * 
     * @return traveled distance
     * 
     */
    double pathLength();

    /**
     * The position before the current position. If the ant hasn't traveled so far. 
     * The return value is the number of the starting node 
     * 
     * @return node of the position before the current
     */
    int prevPosition();
    
	
	/**
	 * Gets all unsivited nodes the ant has revealed so far
	 * @return unvisited Nodes
	 */
	Set<Integer> getUnvisitedNodes();

	/**
	 * The path of ant so far
	 */
	List<Integer> getPath();
	
	Map<Integer, Double> balances(double alpha);

	/** 
	 * set's ant in finished status
	 */
	void finish();

	//double sumOfValues(Map<?, Double> probabilities);
	
	void updatePathLength(int minNode);
}