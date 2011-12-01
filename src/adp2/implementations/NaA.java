package adp2.implementations;

import adp2.interfaces.Ant;
import adp2.interfaces.Path;

public class NaA implements Ant {
	public static NaA instance;

	protected static Ant create() {
		if (instance == null) {
            instance = new NaA();
        }
        return instance;
	}

	private NaA() {}

	@Override
	public Path traveledPath() {
		return Values.NaP();
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
	public double weglaenge() {
		return Double.NaN;
	}

	@Override
	public double alpha() {
		return 0;
	}

	@Override
	public double getWaitingTime() {
		return 0.0;
	}

	@Override
	public int prevPosition() {
		return -1;
	}
}
