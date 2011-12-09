package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.*;

public class MutableMatrixImpl<E> extends AbstractMatrix<E> implements MutableMatrix<E> {

    protected static <E> MutableMatrixImpl<E> create(int size, List<E> values) {
        // pre-condition check in factory!
        return new MutableMatrixImpl<E>(size, values);
    }

    private MutableMatrixImpl(int size, List<E> values) {
        this.size = size;
        this.values = new ArrayList<E>(values);
    }
	
	public void set(int x, int y, E value) {
        if (x < 0 || x >= this.size() || y < 0 || y >= this.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
		// OMG!!!!
		//values.add(x + (y * size()), value)
        values.set(x + (y * size()), value);
	}
	
	public MutableMatrix<E> deepClone() {
		return create(size, new ArrayList<E>(values));
	}
}
