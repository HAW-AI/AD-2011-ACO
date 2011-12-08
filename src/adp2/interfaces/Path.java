package adp2.interfaces;

import java.util.List;

public interface Path {

    /**
     * The waypoints in the order they are visited.
     * 
     * @return the waypoints in the order they are visited
     */
    List<Integer> waypoints();

    /**
     * The total distance of the path
     * 
     * @return total distance of the path
     */
    double distance();
	
	/**
	 * Count of waypoints
	 * 
	 * @return size of waypoint list
	 */
	int size();
}
