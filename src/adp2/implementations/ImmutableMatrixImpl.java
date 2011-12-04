package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

public final class ImmutableMatrixImpl<E> extends AbstractMatrix<E> {

    protected static <E> ImmutableMatrixImpl<E> create(int width, int height, List<E> values) {
        // pre-condition check in factory!
        return new ImmutableMatrixImpl<E>(width, height, values);
    }

    private ImmutableMatrixImpl(int width, int height, List<E> values) {
        this.width = width;
        this.height = height;
        this.values = new ArrayList<E>(values);
    }
}
