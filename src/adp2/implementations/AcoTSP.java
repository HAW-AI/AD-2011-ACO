/**
 * 
 */
package adp2.implementations;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.Path;
import adp2.interfaces.Simulation;
import adp2.interfaces.TSP;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class AcoTSP implements TSP {

    private int antsQuantity;

    
    private AcoTSP(int antsQuantity) {
        this.antsQuantity = antsQuantity;

    }
    
    static AcoTSP create(){
        return new AcoTSP(1000);
    }
    
    static AcoTSP create(int antsQuantity){
        return new AcoTSP(antsQuantity);
    }
    

//    
    /* (non-Javadoc)
     * @see adp2.interfaces.TSP#minPath(adp2.interfaces.Matrix)
     */
    @Override
    public Path minPath(Matrix<Double> m) {
        Graph graph = Values.graph(m);
        Simulation sim = Values.simulation(graph, antsQuantity);
        sim.run();
        return sim.minPath();
    }

}
