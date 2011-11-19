package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.*;

/**
 * Factory class
 *
 */
public final class TSPs {

    /**
     * Create a Matrix with given dimensions and values.
     * 
     * @param width  the width
     * @param height the height
     * @param values the values (the first n elements are the first row, the
     *               following the second one and so forth)
     * @return a valid Matrix object or NaM if any argument is or contains null
     *         or values.size() != width*height
     */
    public static Matrix matrix(int width, int height, List<Integer> values) {
        if (values == null || values.size() != width*height || values.contains(null))
            return NaM();
        return MatrixImpl.valueOf(width, height, values);
    }
    
    /**
     * Not a Matrix
     * 
     * @return Not a Matrix
     */
    public static Matrix NaM() {
        return NaM.valueOf();
    }
    
    /**
     * Create a Path
     * 
     * @param waypoints the waypoints in order of traversal
     * @param distance  the total distance of the path
     * @return a valid Path object or Path(EmptyList, -1) if waypoints is or
     *         contains null or if distance is negative
     */
    public static Path path(List<Integer> waypoints, int distance) {
        if (waypoints == null || waypoints.contains(null) || distance < 0)
            return PathImpl.valueOf(new ArrayList<Integer>(), -1);
        return PathImpl.valueOf(waypoints, distance);
    }
    
}
