package adp2.algorithms.interfaces;

import adp2.interfaces.Path;

/**
 * @author Benjamin Kahlau
 */
public interface TravelingSalesMan extends Simulation {

    /**
     * The shortest path connecting all waypoints in the nxn-Matrix of distances
     * between all of them.
     * 
     * @return the shortest path connecting the waypoints in m or
     *         Path(EmptyList, -1) if m is not an nxn-Matrix or is not symmetric
     */
    Path minPath();
}
