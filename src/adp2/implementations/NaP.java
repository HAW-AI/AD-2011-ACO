package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.Path;


public final class NaP implements Path {
	public static Path instance;

	public static Path valueOf() {
		if (instance == null) {
            instance = new NaP();
        }
        return instance;
	}

	private NaP() {}

	@Override
	public List<Integer> waypoints() {
		return new ArrayList<Integer>();
	}

	@Override
	public int distance() {
		return -1;
	}

}