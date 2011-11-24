package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;

import adp2.interfaces.*;

public class NaMM<T> implements MutableMatrix<T> {

    private static MutableMatrix<Object> instance;
    
    @SuppressWarnings("unchecked")
    public static <T> MutableMatrix<T> valueOf() {
        if (instance == null) {
            instance = (NaMM<Object>)new NaMM<T>();
        }
        return (NaMM<T>)instance;
    }
	
	@Override
	public int width() {
		return -1;
	}

	@Override
	public int height() {
		return -1;
	}

	@Override
	public T get(int x, int y) {
        throw new IndexOutOfBoundsException();
	}

	@Override
	public Iterator<T> iterator() {
        return new ArrayList<T>().iterator();
	}

	@Override
	public void set(int x, int y, T value) {
		
	}

	
	
}
