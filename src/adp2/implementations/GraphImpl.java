package adp2.implementations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mxgraph.view.mxGraph;

import adp2.interfaces.*;
import static adp2.implementations.Values.*;

public class GraphImpl extends mxGraph implements Graph {	
    private final Matrix<Double> distance;
    private MutableMatrix<Double> pheromones;

    private GraphImpl(Matrix<Double> distance, MutableMatrix<Double> pheromones) {
        this.distance = distance;
        this.pheromones = pheromones;
    }

	protected static Graph create(Matrix<Double> distance) {
		if (distance == null) {
			return Values.NaG();
		}
		return new GraphImpl(distance, calcPheromoneMatrix(distance.size()));
	}	

	private static MutableMatrix<Double> calcPheromoneMatrix(int size){
		List<Double> resultList=new ArrayList<Double>();
		for(int i=0; i<(size*size); i++){
			resultList.add(0.);
		}
		return mutableMatrix(size, resultList);
	}

    public double distance(int from, int to) {
        double d;
        try {
            d = distance.get(from - 1, to - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            d = Double.POSITIVE_INFINITY;
        }
        return d;
    }

    public Set<Integer> neighbors(int node) {
        Set<Integer> result = new HashSet<Integer>();
        if (node <= 0 || node > distance.size()) {
            return result;
        }

        for (int i = 0; i < distance.size(); i++) {
            if (!distance.get(node - 1, i).isInfinite() && i != node - 1) {
                result.add(i + 1);
            }
        }
        return result;
    }

    public Set<Integer> allNodes() {
        Set<Integer> result = new HashSet<Integer>();
        for (int i = 1; i <= distance.size(); i++) {
            result.add(i);
        }
        return result;
    }

    public double intensity(int from, int to) {
        double p;
        try {
            p = pheromones.get(from - 1, to - 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            p = Double.POSITIVE_INFINITY;
        }
        return p;
    }

    public void decrementPheromones(double value) {
        for (int i = 0; i < pheromones.size(); i++) {
            for (int j = 0; j < pheromones.size(); j++) {
                if (pheromones.get(i, j) - value < 0 && pheromones.get(j, i) - value < 0) {
                    pheromones.set(i, j, 0.);
                } else {
                    pheromones.set(i, j, pheromones.get(i, j) - value);
                }
				adp2.app.Main.LOGGER.finer("Edge " + (i+1) + "_" + (j+1) + " " + pheromones.get(i, j));
            }
        }
    }

    public void incrementPheromones(int start, int end, double pheromone) {
		int matrixstart = start - 1;
		int matrixend = end - 1;

        pheromones.set(matrixstart, matrixend, pheromones.get(matrixstart, matrixend) + pheromone);
		pheromones.set(matrixend, matrixstart, pheromones.get(matrixend, matrixstart) + pheromone);
    }
	
    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Distance:\n");
        result.append(distance.toString());
        result.append("\n");
        result.append("Pheromones:\n");
        result.append(pheromones.toString());
        result.append("\n");

        return result.toString();
    }

    public Graph deepClone() {
        return new GraphImpl(distance, pheromones.deepClone());
    }

    public Matrix<Double> distanceVar() {
        return this.distance;
    }
}
