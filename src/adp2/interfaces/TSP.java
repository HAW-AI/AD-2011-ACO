package adp2.interfaces;

import adp2.interfaces.Matrix;

/**
 * Traveling Salesman Problem
 */
public interface TSP {

    /**
     * The shortest path connecting all waypoints in the nxn-Matrix of distances
     * between all of them.
     * 
     * @return the shortest path connecting the waypoints in m or
     *         Path(EmptyList, -1) if m is not an nxn-Matrix
     */
    Path minPath(Matrix m);
    
}