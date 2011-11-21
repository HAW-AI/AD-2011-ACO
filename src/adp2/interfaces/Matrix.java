package adp2.interfaces;

public interface Matrix<E> extends Iterable<E> {

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
    E get(int x, int y);
    
    /**
     * Set a value at the positin (x,y) in the Matrix.
     * 
     * @param x x-position
     * @param y y-position
     * @param value the value which should be set at (x,y)
     */
    void set(int x, int y, E value);
    
}