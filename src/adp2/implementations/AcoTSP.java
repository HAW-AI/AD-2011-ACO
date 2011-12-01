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

    private final int antsQuantity;
    private final int antsByStep;
 
    private final int maxSeconds;
    private final int maxSteps;
    private Simulation sim;

    
    private AcoTSP(int antsQuantity, int antsByStep, int maxSeconds, int maxSteps) {
        this.antsQuantity = antsQuantity;
        this.antsByStep = antsByStep;
        this.maxSeconds = maxSeconds;
        this.maxSteps = maxSteps;
     

    }
    
    static AcoTSP create(){
        return new AcoTSP(1000, 5, 0, 0);
    }
    
    static AcoTSP create(int antsQuantity, int antsByStep){
        return new AcoTSP(antsQuantity, antsByStep, 0, 0);
    }
    
    /**
     * number = seconds if runForSeconds == true
     * number = steps if runForSeconds == false
     * @param antsQuantity
     * @param antsByStep
     * @param number
     * @param runForSeconds
     * @return
     */
    static AcoTSP create(int antsQuantity, int antsByStep, int number, boolean runForSeconds){
        if (runForSeconds == true) {
        return new AcoTSP(antsQuantity, antsByStep, number, 0);
        } else {
            return new AcoTSP(antsQuantity, antsByStep, 0,number);
        }
        
    }

    

//    
    /* (non-Javadoc)
     * @see adp2.interfaces.TSP#minPath(adp2.interfaces.Matrix)
     */
    @Override
    public Path minPath(Matrix<Double> matrix) {
    	 if (matrix.width() != matrix.height() || matrix.width() == 0){
    		return Values.NaP();
    	}
        Graph graph = Values.graph(matrix);
        
        sim = Values.simulation(graph, antsQuantity, antsByStep);
        if(maxSeconds == 0 && maxSteps == 0) {
        sim.run();
        } else if(maxSeconds == 0){
            sim.runForSteps(maxSteps);
        } else if(maxSteps == 0) {
            sim.runForSeconds(maxSeconds);
        } else {
            System.out.println("Shouldnt be here");
        }
        return sim.minPath();
    }

}
