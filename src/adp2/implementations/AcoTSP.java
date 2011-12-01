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
    private int antsByStep;
    private Simulation sim;

    
    private AcoTSP(int antsQuantity, int antsByStep) {
        this.antsQuantity = antsQuantity;
        this.antsByStep = antsByStep;

    }
    
    static AcoTSP create(){
        return new AcoTSP(1000, 5);
    }
    
    static AcoTSP create(int antsQuantity, int antsByStep){
        return new AcoTSP(antsQuantity, antsByStep);
    }

    

//    
    /* (non-Javadoc)
     * @see adp2.interfaces.TSP#minPath(adp2.interfaces.Matrix)
     */
    @Override
    public Path minPath(Matrix<Double> m) {
        Graph graph = Values.graph(m);
        
        sim = Values.simulation(graph, antsQuantity, antsByStep);
        sim.run();
        return sim.minPath();
    }

}
