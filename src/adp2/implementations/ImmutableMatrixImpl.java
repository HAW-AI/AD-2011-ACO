package adp2.implementations;

import adp2.interfaces.ImmutableMatrix;
import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.Matrix;

public final class ImmutableMatrixImpl<E> extends AbstractMatrix<E> implements ImmutableMatrix<E> {

    protected static <E> ImmutableMatrixImpl<E> create(int size, List<E> values) {
        // pre-condition check in factory!
        return new ImmutableMatrixImpl<E>(size, values);
    }

    private ImmutableMatrixImpl(int size, List<E> values) {
        this.size = size;
        this.values = new ArrayList<E>(values);
    }
}
