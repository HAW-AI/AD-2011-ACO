package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.*;
import static adp2.implementations.TSPs.*;

public final class BruteForceTSP implements TSP {
    
    private static TSP instance;
    
    public static TSP valueOf() {
        if (instance == null) {
            instance = new BruteForceTSP();
        }
        return instance;
    }
    
    private BruteForceTSP() {}

    @Override
    public Path minPath(Matrix<Integer> distances) {
        if (distances.width() != distances.height())
            return NaP();
        
        // check symmetry
        for (int i = 0, half = distances.width()/2; i <= half; ++i) {
            for (int j = 0; j <= half; ++j) {
                if (distances.get(i,j) != distances.get(j,i)) {
                    return NaP();
                }
            }
        }

        List<Integer> minWaypoints = new ArrayList<Integer>();
        int minDistance = Integer.MAX_VALUE;
        
        // waypoints numbered from 1 through n
        List<Integer> waypoints = new ArrayList<Integer>(distances.width());
        for (int i = 1; i <= distances.width(); ++i) waypoints.add(i);
        
        Iterator<List<Integer>> iter = new PermutationIterator<Integer>(waypoints);
        
        while (iter.hasNext()) {
            List<Integer> perm = iter.next();
            
            int distance = 0;
            for (int i = 0; i < perm.size()-1; ++i) {
                // waypoints are numbered from 1 through n, so we need to
                // subtract 1 to get the proper index
                distance += distances.get(perm.get(i)-1, perm.get(i+1)-1);
            }
            
            if (minDistance > distance) {
                minWaypoints = new ArrayList<Integer>(perm);
                minDistance = distance;
            }
        }
        
        
        return path(minWaypoints, minDistance);
    }

}
