package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;

import adp2.interfaces.*;

public class NaIM<T> implements ImmutableMatrix<T> {

    private static ImmutableMatrix<Object> instance;
    
    @SuppressWarnings("unchecked")
    protected static <T> ImmutableMatrix<T> create() {
        if (instance == null) {
            instance = (NaIM<Object>)new NaIM<T>();
        }
        return (NaIM<T>)instance;
    }
	
	public int size() {
		return -1;
	}

	public T get(int x, int y) {
        throw new IndexOutOfBoundsException();
	}

	public Iterator<T> iterator() {
        return new ArrayList<T>().iterator();
	}

	public void set(int x, int y, T value) {
		
	}

	public ImmutableMatrix<T> deepClone() {
		return create();
	}

	
	
}
