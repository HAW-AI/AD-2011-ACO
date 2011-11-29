package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import adp2.interfaces.Ant;
import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.MutableMatrix;
import adp2.interfaces.Path;
import adp2.interfaces.Simulation;
import adp2.interfaces.TSP;

/**
 * Factory class
 *
 */
public final class Values {
    
    /**
     * Create a brute force TSP
     * 
     * @return brute force TSP
     */
    public static TSP bruteForceTSP() {
        return BruteForceTSP.valueOf();
    }

    /**
     * Create a Matrix with given dimensions and values.
     * 
     * @param width  the width
     * @param height the height
     * @param values the values (the first n elements are the first row, the
     *               following the second one and so forth)
     * @return a valid Matrix object or NaM if any argument is or contains null
     *         or values.size() != width*height
     */
    public static <T> Matrix<T> matrix(int width, int height, List<T> values) {
        if (values == null || values.size() != width*height || values.contains(null))
            return NaM();
        return ImmutableMatrixImpl.valueOf(width, height, values);
    }
    
    /**
     * Create a Matrix with given dimensions and values.
     * 
     * @param width  the width
     * @param height the height
     * @param values the values (the first n elements are the first row, the
     *               following the second one and so forth)
     * @return a valid Matrix object or NaM if any argument is or contains null
     *         or values.size() != width*height
     */
    public static <T> MutableMatrix<T> mutableMatrix(int width, int height, List<T> values) {
        if (values == null || values.size() != width*height || values.contains(null))
            return NaMM();
        return MutableMatrixImpl.valueOf(width, height, values);
    }
    
    /**
     * Not a Matrix
     * 
     * @return Not a Matrix
     */
    public static <T> Matrix<T> NaM() {
        return NaM.valueOf();
    }

    /**
     * Not a Mutable Matrix
     * 
     * @return Not a Mutable Matrix
     */
    public static <T> MutableMatrix<T> NaMM() {
        return NaMM.valueOf();
    }
    
    /**
     * Not a Graph
     *
     * @return Not a Graph
     */
    public static Graph NaG() {
		return NaG.valueOf();
	}

    /**
     * Not an Ant
     *
     * @return Not an Ant
     */
    public static Ant NaA() {
    	return NaA.valueOf();
    }

    /**
     * Not an Path
     *
     * @return Not an Ant
     */
    public static Path NaP() {
    	return NaP.valueOf();
    }
    
    /**
     * Not an Simulation
     *
     * @return Not an Simulation
     */
    public static Simulation NaS() {
    	return NaS.valueOf();
    }
    
    /**
     * Create a simulation with a defined number of ants starting at once
     * 
     * @param graph 
     * @param antsQuantity  total number of ants in the graph, pushed at once
     * 
     * @return Simulation 
     * 
     */
    public static Simulation simulation(Graph graph, int antsQuantity) {
    	if(antsQuantity < 0){ 
    		return Values.NaS();
    	}
    	return SimulationImpl.create(graph, antsQuantity);
    }
    
    /**
     * Create a simulation with a defined number of ants starting at once and logs states of all graphs
     * 
     * @param graph 
     * @param antsQuantity  total number of ants in the graph, pushed at once
     * @param logStates 	simulation log states or not
     * 
     * @return Simulation 
     * 
     */
    public static Simulation simulation(Graph graph, int antsQuantity, boolean logStates) {
    	if(antsQuantity < 0){ 
    		return Values.NaS();
    	}
    	return SimulationImpl.create(graph, antsQuantity, logStates);
    }
    
    /**
     * Create a simulation with a defined number of ants in total starting step by step
     * 
     * @param graph 
     * @param antsQuantity  total number of ants in the graph
     * @param antsByStep 	number of ants, pushed at every step
     * 
     * @return Simulation 
     * 
     */
    public static Simulation simulation(Graph graph, int antsQuantity, int antsByStep) {
    	if(antsQuantity < 0 || antsByStep < 0){ 
    		return Values.NaS();
    	}
    	return SimulationImpl.create(graph, antsQuantity, antsByStep);
    }
    
    /**
     * Create a simulation with a defined number of ants in total starting step by step and logs states of all graphs
     * 
     * @param graph 
     * @param antsQuantity  total number of ants in the graph
     * @param antsByStep 	number of ants, pushed at every step
     * @param logStates 	simulation log states or not
     * 
     * @return Simulation
     * 
     */
    public static Simulation simulation(Graph graph, int antsQuantity, int antsByStep, boolean logStates) {
    	if(antsQuantity < 0 || antsByStep < 0){ 
    		return Values.NaS();
    	}
    	return SimulationImpl.create(graph, antsQuantity, antsByStep, logStates);
    }
    
    /**
     * Create a Path
     * 
     * @param waypoints the waypoints in order of traversal
     * @param distance  the total distance of the path
     * @return a valid Path object or Path(EmptyList, -1) if waypoints is or
     *         contains null or if distance is negative
     */
    public static Path path(List<Integer> waypoints, double distance) {
        if (waypoints == null || waypoints.contains(null) || distance < 0)
            return NaP();
        return PathImpl.creator(waypoints, distance);
    }
    
    /**
     * Create a Graph from Array
     * 
     * @param args the array from which the Graph will be created 
     * @return a valid Graph Object
     */
    public static Graph graphFromList(double... args){
    	if(!graphPreCheck(args)){
    		return NaG();
    	}
    	List<Double> l = new ArrayList<Double>();
    	for(Double elem : args){
    		l.add(elem);
    	}
    	MutableMatrix<Double> m = mutableMatrix((int)Math.sqrt(args.length), (int)Math.sqrt(args.length), l);
    	
    	return GraphImpl.valueOf(m);
    }
    
    /**
     * PreConditionCheck for the Method "graphFromList"
     * 
     * @param args the array from which the Graph will be created 
     * @return a boolean to show if given Array is valid
     */
    private static boolean graphPreCheck(double[] x){
    	double i = Math.floor(Math.sqrt(x.length));
    	if(i == Math.sqrt(x.length)){
    		return true;
    	}
    	return false;
    }
    
    /**
     * Create a new ant -> public for testing purposes only -> should be package private
     * @param startNode
     * @param alpha
     * @param g
     * @return Ant
     */
    public static Ant ant(int startNode, double alpha, Graph g){
    	return AntImpl.valueOf(startNode, alpha, g);
    }
    
}
