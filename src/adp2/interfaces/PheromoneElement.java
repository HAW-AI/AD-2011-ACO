/**
 * 
 */
package adp2.interfaces;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public interface PheromoneElement {

    
    /**
     * Get the Start Point of the Element
     * @return
     */
    int from();
    /**
     * Get the End Point of the Element
     * @return
     */
    int to();
    
    
    /**
     * get the pheromone of the current edge
     * @return
     */
    double pheromone();
}
