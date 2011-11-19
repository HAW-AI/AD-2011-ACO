package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.Matrix;

public final class MatrixImpl implements Matrix {

    final private int width;
    final private int height;
    final private List<Integer> values;
    
    public static Matrix valueOf(int width, int height, List<Integer> values) {
        // pre-condition check in factory!
        return new MatrixImpl(width, height, values);
    }

    private MatrixImpl(int width, int height, List<Integer> values) {
        this.width = width;
        this.height = height;
        this.values = new ArrayList<Integer>(values);
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ArrayList<Integer>(values).iterator();
    }

    @Override
    public int get(int x, int y) {
        if (x < 0 || x >= this.width() || y < 0 || y >= this.height()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return values.get(x + (y * width()));
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof MatrixImpl)) return false;
        return ((MatrixImpl)o).values.equals(values);
    }
    
    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < height(); y++) {
            result.append("[");
            for (int x = 0; x < width(); x++) {
                result.append(get(x, y));
            }
            result.append("]\n");
        }
        return result.toString();
    }

}
