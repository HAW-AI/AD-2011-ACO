package adp2.implementations;

import java.util.HashMap;
import java.util.Map;

import adp2.interfaces.Path;
import adp2.interfaces.Simulation;

public class NaS implements Simulation {
	public static NaS instance;
	
	public static Simulation valueOf() {
		if (instance == null) {
            instance = new NaS();
        }
        return instance;
	}

	private NaS() {}
	
	@Override
	public void run() {}

	@Override
	public void runForSeconds(int runtimeInS) {}

	@Override
	public void runForSteps(int simulationSteps) {}

	@Override
	public Map<Path, Integer> frequencyMap() {
		return new HashMap<Path, Integer>();
	}

	@Override
	public Path minPath() {
		return Values.NaP();
	}
}