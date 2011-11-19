package adp2.implementations;

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
    
}
