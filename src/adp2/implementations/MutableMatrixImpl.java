package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.*;

public class MutableMatrixImpl<E> extends AbstractMatrix<E> implements MutableMatrix<E> {

    protected static <E> MutableMatrixImpl<E> creator(int width, int height, List<E> values) {
        // pre-condition check in factory!
        return new MutableMatrixImpl<E>(width, height, values);
    }

    private MutableMatrixImpl(int width, int height, List<E> values) {
        this.width = width;
        this.height = height;
        this.values = new ArrayList<E>(values);
    }
	
	@Override
	public void set(int x, int y, E value) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        values.add(x + (y * width()), value);
	}

	@Override
	public MutableMatrix<E> deepClone() {
		return creator(width, height, new ArrayList<E>(values));
	}
}
