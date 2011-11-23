package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.Ant;

public class NaA implements Ant {

	@Override
	public List<Integer> traveledPath() {
		return new ArrayList<Integer>();
	}

	@Override
	public int position() {
		return -1;
	}

	@Override
	public void step() {}

	@Override
	public boolean hasFinished() {
		return false;
	}

	@Override
	public int weglaenge() {
		return -1;
	}

	@Override
	public double alpha() {
		return 0;
	}

	@Override
	public int getWaitingTime() {
		return 0;
	}

}
