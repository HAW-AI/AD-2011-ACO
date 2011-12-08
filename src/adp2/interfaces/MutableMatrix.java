package adp2.interfaces;

public interface MutableMatrix<E> extends Matrix<E> {

    /**
     * Set a value at the position (x,y) in the Matrix.
     * 
     * @param x x-position
     * @param y y-position
     * @param value the value which should be set at (x,y)
     */
    void set(int x, int y, E value);
	       
	/**
	 * deepClone()
	 * @return a copy of the graph where all fields are also copies of their original values.
	 */
    MutableMatrix<E> deepClone();
}
