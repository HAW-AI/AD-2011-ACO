/**
 * 
 */
package adp2.implementations;

import adp2.interfaces.Matrix;
import adp2.interfaces.Path;
import adp2.interfaces.TSP;

/**
 * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
 *
 */
public class NaTSP implements TSP {

    /* (non-Javadoc)
     * @see adp2.interfaces.TSP#minPath(adp2.interfaces.Matrix)
     */

        public static TSP instance;

        protected static TSP create() {
            if (instance == null) {
                instance = new NaTSP();
            }
            return instance;
        }

        private NaTSP() {}

    
    @Override
    public Path minPath(Matrix<Double> m) {
        return Values.NaP();
    }

}
