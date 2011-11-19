package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;

import adp2.interfaces.Matrix;

public final class NaM implements Matrix {
    
    private static Matrix instance;
    
    public static Matrix valueOf() {
        if (instance == null) {
            instance = new NaM();
        }
        return instance;
    }
    
    private NaM() {}

    @Override
    public Iterator<Integer> iterator() {
        return new ArrayList<Integer>().iterator();
    }

    @Override
    public int width() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public int get(int x, int y) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public String toString() {
        return "NaM";
    }

}
