/**
 * 
 */
package adp2.app;

import adp2.implementations.Values;
import adp2.interfaces.*;
import adp2.parser.TspFile;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class Vergleich {

    /**
     * @param args
     */
    public static void main(String[] args) {
        TspFile t = null;
//        t = TspFile.open("samples/gr21.tsp");
//      t = TspFile.open("samples/ant2.tsp");       
      t = TspFile.open("samples/ant5.tsp");
//      t = TspFile.open("samples/ant9.tsp");
//      t = TspFile.open("samples/ant15.tsp");
        TSP bf = Values.bruteForceTSP();
        TSP aco = Values.acoTSP();
        
        Path bfPath = bf.minPath(t.matrix());
        Path acoPath = aco.minPath(t.matrix());
        System.out.println("\n\n\n");
        
        System.out.println("Way BF: " + bfPath);
        System.out.println("Distance BF: " + bfPath.distance());
        System.out.println("Way ACO: " + acoPath);
        System.out.println("Distance ACO: " + acoPath.distance());
        

    }

}
