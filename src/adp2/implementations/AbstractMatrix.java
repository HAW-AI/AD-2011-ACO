package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.Matrix;

public abstract class AbstractMatrix<E> implements Matrix<E> {

    protected int size;
    protected List<E> values;
	
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayList<E>(values).iterator();
    }

    public E get(int x, int y) {
        if (x < 0 || x >= this.size() || y < 0 || y >= this.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return values.get(x + (y * size()));
    }
	
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AbstractMatrix<?>)) return false;
        return ((AbstractMatrix<?>)o).values.equals(values);
    }
	
    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < size(); y++) {
            result.append("[ ");
            for (int x = 0; x < size(); x++) {
            	result.append(String.format("%6.2f ", get(x, y)));
            }
            result.append("]\n");
        }
        return result.toString();
    }
}
