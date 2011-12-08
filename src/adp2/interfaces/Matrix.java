package adp2.interfaces;

public interface Matrix<E> extends Iterable<E> {

    /**
     * The size of the Matrix.
     * 
     * @return the size
     */
    int size();
    /**
     * The value at the position (x, y) in the Matrix.
     * 
     * @param x x-position
     * @param y y-position
     * @return the value at (x, y)
     */
    E get(int x, int y);
}