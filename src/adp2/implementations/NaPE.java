/**
 * 
 */
package adp2.implementations;

import adp2.interfaces.PheromoneElement;
import adp2.interfaces.TSP;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class NaPE implements PheromoneElement {

    public static PheromoneElement instance;

    protected static PheromoneElement create() {
        if (instance == null) {
            instance = new NaPE();
        }
        return instance;
    }

    private NaPE() {}
    
    /* (non-Javadoc)
     * @see adp2.interfaces.PheromoneElement#from()
     */
    @Override
    public int from() {
        return -1;
    }

    /* (non-Javadoc)
     * @see adp2.interfaces.PheromoneElement#to()
     */
    @Override
    public int to() {
        // TODO Auto-generated method stub
        return -1;
    }

    /* (non-Javadoc)
     * @see adp2.interfaces.PheromoneElement#pheromone()
     */
    @Override
    public double pheromone() {
        // TODO Auto-generated method stub
        return -1;
    }

}
