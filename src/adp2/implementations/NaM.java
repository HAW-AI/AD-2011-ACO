package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;

import adp2.interfaces.Matrix;

public final class NaM implements Matrix<Object> {
    
    private static Matrix<Object> instance;
    
    public static Matrix<Object> valueOf() {
        if (instance == null) {
            instance = new NaM();
        }
        return instance;
    }
    
    private NaM() {}

    @Override
    public Iterator<Object> iterator() {
        return new ArrayList<Object>().iterator();
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
    public Integer get(int x, int y) {
        throw new IndexOutOfBoundsException();
    }
    
    @Override
    public String toString() {
        return "NaM";
    }

	@Override
	public void set(int x, int y, Object value) {
		
	}
    
    

}
