package adp2.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;

public class GraphImpl implements Graph {

	private final Matrix<Integer> distance;
	private Matrix<Double> pheromones;
	private Double[][] dm;
	private Double[][] tm;

	private GraphImpl(Matrix<Integer> distance, Matrix<Double> pheromones) {
		this.distance = distance;
		this.pheromones = pheromones;
		dm = new Double[distance.width()][distance.height()];
		tm = new Double[distance.width()][distance.height()];
		floydWarshall();
	}

	@Override
	public int distance(int von, int nach) {
		int d;
		try {
			d = distance.get(von - 1, nach - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			d = -1;
		}
		return d;
	}

	@Override
	public Set<Integer> neighbors(int node) {
		Set<Integer> result = new HashSet<Integer>();
		if (node <= 0 || node > distance.height()) {
			return result;
		}

		for (int i = 0; i < distance.width(); i++) {
			if (distance.get(node - 1, i) != -1 && i != node - 1) {
				result.add(i + 1);
			}
		}
		return result;
	}

	@Override
	public Set<Integer> allNodes() {
		Set<Integer> result = new HashSet<Integer>();
		for (int i = 1; i <= distance.height(); i++) {
			result.add(i);
		}
		return result;
	}

	@Override
	public double intensity(int von, int nach) {
		double p;
		try {
			p = pheromones.get(von - 1, nach - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			p = -1;
		}
		return p;
	}

	@Override
	public void decrementPheromone(int value) {
		for (int i = 0; i < pheromones.width(); i++) {
			for (int j = 0; j < pheromones.height(); j++) {
				if (pheromones.get(i, j) - value < 0) {
					pheromones.set(i, j, 0.);
				} else {
					pheromones.set(i, j, pheromones.get(i, j) - value);
				}
			}
		}
	}

	private void floydWarshall() {
		for (int i = 0; i <= distance.width(); i++) {
			for (int j = 0; j <= distance.height(); j++) {
				tm[i][j] = 0.0;
				if (!(i == j)) {
					if (distance.get(i, j) == -1) {
						dm[i][j] = Double.POSITIVE_INFINITY;
					} else {
						dm[i][j] = (double) distance.get(i, j);
					}
				} else {
					dm[i][j] = 0.0;
				}
			}
		}

		for (int i = 0; i <= distance.width(); i++) {
			for (int j = 0; j <= distance.height(); j++) {
				if (!(i == j)) {
					for (int k = 0; k <= distance.width(); k++) {
						if (!(j == k)) {
							if (dm[i][k] != Math.min(dm[i][k],
									(dm[i][j] + dm[j][k]))) {
								dm[i][k] = Math.min(dm[i][k],
										(dm[i][j] + dm[j][k]));
								tm[i][k] = (double) j;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<Integer> pointsBetween(int von, int bis) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (von == bis) {
			return res;
		}
		res.add(von);
		res.addAll(pointsBetween_(von, bis));
		res.add(bis);
		return res;
	}

	private List<Integer> pointsBetween_(int von, int bis) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		int i = tm[von][bis].intValue();
		if (i == 0) {
			return res;
		} else {
			res.addAll(pointsBetween_(von, i));
			res.add(i);
			res.addAll(pointsBetween_(i, bis));
			return res;
		}

	}

	@Override
	public int minDist(int von, int bis) {
		return dm[von][bis].intValue();
	}
}
