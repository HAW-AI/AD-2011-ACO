package adp2.algorithms;

import adp2.app.Main;
import adp2.algorithms.interfaces.TravelingSalesMan;
import adp2.implementations.Values;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.*;
import static adp2.implementations.Values.*;

public final class BruteForceTSP implements TravelingSalesMan {

	private Path minPath;
	private Matrix<Double> matrix;

	public static BruteForceTSP create(Matrix<Double> matrix) {
		Main.LOGGER.info("BruteForceTSP created!");
		return new BruteForceTSP(matrix);
	}

	private BruteForceTSP(Matrix<Double> matrix) {
		this.matrix = matrix;
	}

	public void run() {
		if (matrix.size() == 0) {
			this.minPath = NaP();
		}

		// check symmetry
		for (int i = 0, half = matrix.size() / 2; i <= half; ++i) {
			for (int j = 0; j <= half; ++j) {
				if (!matrix.get(i, j).equals(matrix.get(j, i))) {
					this.minPath = NaP();
				}
			}
		}

		List<Integer> minWaypoints = new ArrayList<Integer>();
		int minDistance = Integer.MAX_VALUE;

		// waypoints are numbered 1 through n
		List<Integer> waypoints = new ArrayList<Integer>(matrix.size());
		for (int i = 1; i <= matrix.size(); ++i) {
			waypoints.add(i);
		}

		Iterator<List<Integer>> iter = Values.permutationIterator(waypoints);

		while (iter.hasNext()) {
			List<Integer> perm = iter.next();

			int distance = 0;
			for (int i = 0; i < perm.size() - 1; ++i) {
				// waypoints are numbered 1 through n, so we need to
				// subtract 1 to get the proper index
				distance += matrix.get(perm.get(i) - 1, perm.get(i + 1) - 1);
			}

			// add way back to start point
			if (perm.size() > 1) {
				distance += matrix.get(perm.get(perm.size() - 1) - 1, perm.get(0) - 1);
			}

			if (minDistance > distance) {
				minWaypoints = new ArrayList<Integer>(perm);
				minDistance = distance;
			}
		}
		// add Startpoint at end of way
		if (!minWaypoints.isEmpty()) {
			minWaypoints.add(minWaypoints.get(0));
			this.minPath = path(minWaypoints, minDistance);
		} else {
			this.minPath = path(minWaypoints, Double.POSITIVE_INFINITY);
		}
	}

	public Path minPath() {
		return this.minPath;
	}

	@Override
	public void runForSeconds(int runtimeInS) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void runForSteps(int simulationSteps) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
