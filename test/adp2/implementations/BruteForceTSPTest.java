package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import adp2.interfaces.Matrix;
import adp2.interfaces.Path;
import adp2.interfaces.TSP;
import static adp2.implementations.Values.*;

import static java.util.Arrays.asList;


// Tests run against the slightly modified Scala implementation by Esser:
//    
//    def minPath(cities: List[Int], distance: (Int, Int) => Int) =
//    cities.permutations.foldLeft(cities, Int.MaxValue) {
//      (cPathMin, cPath) => {
//        val sum = cPath.zip(cPath.tail).foldLeft(0)((d, cc) => d + distance(cc._1, cc._2))
//        if (cPathMin._2 > sum) (cPath, sum) else cPathMin
//      }
//    }
//  
//    def distance(c1: Int, c2: Int) = 100/(if (c1>c2) (c1 - c2) else (c2 - c1))
//            
//            
// one line was changed to add the distance back to the start point:
// val sum = cPath.zip(cPath.tail :+ cPath.head).foldLeft(0)((d, cc) => d + distance(cc._1, cc._2))


public class BruteForceTSPTest {
    private TSP tsp;
    
    private Path path1;
    private Matrix<Integer> distances1;
    
    private Path path2;
    private Matrix<Integer> distances2;
    
    private Path path3;
    private Matrix<Integer> distances3;
    
    private Path path4;
    private Matrix<Integer> distances4;
    
    private Path path5;
    private Matrix<Integer> distances5;
    
    @Before
    public void setup() {
        tsp = bruteForceTSP();
        
        path1 = path(asList(1, 5, 2, 7, 4, 8, 3, 6), 209);
        List<Integer> distList1 = asList(
             -1, 100,  50,  33,  25,  20,  16,  14,
            100,  -1, 100,  50,  33,  25,  20,  16,
             50, 100,  -1, 100,  50,  33,  25,  20,
             33,  50, 100,  -1, 100,  50,  33,  25,
             25,  33,  50, 100,  -1, 100,  50,  33,
             20,  25,  33,  50, 100,  -1, 100,  50,
             16,  20,  25,  33,  50, 100,  -1, 100,
             14,  16,  20,  25,  33,  50, 100,  -1
         );
        distances1 = matrix(8, 8, distList1);
        
        path2 = path(asList(1, 2, 3), 250);
        List<Integer> distList2 = asList(
             -1, 100,  50,
            100,  -1, 100,
             50, 100,  -1
        );
        distances2 = matrix(3, 3, distList2);
        
        path3 = NaP();
        distances3 = NaM();
        
        path4 = path(asList(1), 0);
        distances4 = matrix(1, 1, asList(-1));
        
        path5 = NaP();
        distances5 = matrix(0, 0, new ArrayList<Integer>());
    }
    
    @Test
    public void testMinPath() {
        assertEquals(path1, tsp.minPath(distances1));
        assertEquals(path2, tsp.minPath(distances2));
        assertEquals(path3, tsp.minPath(distances3));
        assertEquals(path4, tsp.minPath(distances4));
        assertEquals(path5, tsp.minPath(distances5));
    }
}
