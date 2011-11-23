package adp2.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import adp2.interfaces.Matrix;
import adp2.interfaces.Path;
import adp2.interfaces.TSP;
import static adp2.implementations.TSPs.*;

import static java.util.Arrays.asList;


public class BruteForceTSPTest {
    private TSP tsp;
    
    private Path path1;
    private Matrix<Integer> distances1;
    
    private Path path2;
    private Matrix<Integer> distances2;
    
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
    }
    
    @Test
    public void testMinPath1() {
        assertEquals(path1, tsp.minPath(distances1));
    }
    
    @Test
    public void testMinPath2() {
        assertEquals(path2, tsp.minPath(distances2));
    }
}
