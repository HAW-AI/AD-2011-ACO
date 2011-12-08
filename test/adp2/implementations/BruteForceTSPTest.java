//package adp2.implementations;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//
//import adp2.interfaces.Matrix;
//import adp2.interfaces.Path;
//import adp2.interfaces.TSP;
//import static adp2.implementations.Values.*;
//
//import static java.util.Arrays.asList;
//
//
//// Tests run against the slightly modified Scala implementation by Esser:
////    
////    def minPath(cities: List[Int], distance: (Int, Int) => Int) =
////    cities.permutations.foldLeft(cities, Int.MaxValue) {
////      (cPathMin, cPath) => {
////        val sum = cPath.zip(cPath.tail).foldLeft(0)((d, cc) => d + distance(cc._1, cc._2))
////        if (cPathMin._2 > sum) (cPath, sum) else cPathMin
////      }
////    }
////  
////    def distance(c1: Int, c2: Int) = 100/(if (c1>c2) (c1 - c2) else (c2 - c1))
////            
////            
//// one line was changed to add the distance back to the start point:
//// val sum = cPath.zip(cPath.tail :+ cPath.head).foldLeft(0)((d, cc) => d + distance(cc._1, cc._2))
////
//// The Java implementation differs from the Scala implementation to allow
//// paths of length 1.
//
//
//public class BruteForceTSPTest {
//    private TSP tsp;
//    
//    private Path path1;
//    private Matrix<Double> distances1;
//    
//    private Path path2;
//    private Matrix<Double> distances2;
//    
//    private Path path3;
//    private Matrix<Double> distances3;
//    
//    private Path path4;
//    private Matrix<Double> distances4;
//    
//    private Path path5;
//    private Matrix<Double> distances5;
//    
//    @Before
//    public void setup() {
//        tsp = bruteForceTSP();
//        
//        path1 = path(asList(1, 5, 2, 7, 4, 8, 3, 6, 1), 209);
//        List<Double> distList1 = asList(
//             -1.0, 100.0,  50.0,  33.0,  25.0,  20.0,  16.0,  14.0,
//            100.0,  -1.0, 100.0,  50.0,  33.0,  25.0,  20.0,  16.0,
//             50.0, 100.0,  -1.0, 100.0,  50.0,  33.0,  25.0,  20.0,
//             33.0,  50.0, 100.0,  -1.0, 100.0,  50.0,  33.0,  25.0,
//             25.0,  33.0,  50.0, 100.0,  -1.0, 100.0,  50.0,  33.0,
//             20.0,  25.0,  33.0,  50.0, 100.0,  -1.0, 100.0,  50.0,
//             16.0,  20.0,  25.0,  33.0,  50.0, 100.0,  -1.0, 100.0,
//             14.0,  16.0,  20.0,  25.0,  33.0,  50.0, 100.0,  -1.0
//         );
//        distances1 = matrix(8, distList1);
//        
//        path2 = path(asList(1, 2, 3, 1), 250);
//        List<Double> distList2 = asList(
//                -1.0, 100.0,  50.0,
//                100.0,  -1.0, 100.0,
//                 50.0, 100.0,  -1.0
//        );
//        distances2 = matrix(3, distList2);
//        
//        path3 = NaP();
//        distances3 = NaM();
//        
//        path4 = path(asList(1, 1), 0);
//        distances4 = matrix(1, asList(-1.0));
//        
//        path5 = NaP();
//        distances5 = matrix(0, new ArrayList<Double>());
//    }
//    
//    @Test
//    public void testMinPath() {
//        assertEquals(path1, tsp.minPath(distances1));
//        assertEquals(path2, tsp.minPath(distances2));
//        assertEquals(path3, tsp.minPath(distances3));
//        assertEquals(path4, tsp.minPath(distances4));
//        assertEquals(path5, tsp.minPath(distances5));
//    }
//}
