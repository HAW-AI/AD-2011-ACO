package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.*;
import static adp2.implementations.Values.*;

public final class BruteForceTSP implements TSP {

    private static TSP instance;

    protected static TSP create() {
        if (instance == null) {
            instance = new BruteForceTSP();
        }
        return instance;
    }

    private BruteForceTSP() {
    }

    @Override
    public Path minPath(Matrix<Double> matrix) {
        if (matrix.width() != matrix.height() || matrix.width() == 0) {
            return NaP();
        }

        // check symmetry
        for (int i = 0, half = matrix.width() / 2; i <= half; ++i) {
            for (int j = 0; j <= half; ++j) {
                if (!matrix.get(i, j).equals(matrix.get(j, i))) {
                    return NaP();
                }
            }
        }

        List<Integer> minWaypoints = new ArrayList<Integer>();
        int minDistance = Integer.MAX_VALUE;

        // waypoints are numbered 1 through n
        List<Integer> waypoints = new ArrayList<Integer>(matrix.width());
        for (int i = 1; i <= matrix.width(); ++i) {
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
            return path(minWaypoints, minDistance);
        } else {
            return path(minWaypoints, Double.POSITIVE_INFINITY);
        }


    }
}
