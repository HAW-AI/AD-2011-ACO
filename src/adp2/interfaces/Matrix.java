package adp2.interfaces;

public interface Matrix extends Iterable<Integer> {

    /**
     * The width of the Matrix.
     * 
     * @return the width
     */
    int width();

    /**
     * The height of the Matrix.
     * 
     * @return the height
     */
    int height();

    /**
     * The value at the position (x, y) in the Matrix.
     * 
     * @param x x-position
     * @param y y-position
     * @return the value at (x, y)
     */
    int get(int x, int y);
}