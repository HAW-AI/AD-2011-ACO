package adp2.implementations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import adp2.interfaces.Ant;
import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.MutableMatrix;
import adp2.interfaces.Path;
import adp2.interfaces.PheromoneElement;
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

	private Values() {
	}

	public static TSP bruteForceTSP() {
		return BruteForceTSP.create();
	}

	/**
	 * Create a Matrix with given dimensions and values.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param values
	 *            the values (the first n elements are the first row, the
	 *            following the second one and so forth)
	 * @return a valid Matrix object or NaM if any argument is or contains null
	 *         or values.size() != width*height
	 */
	public static <T> Matrix<T> matrix(int width, int height, List<T> values) {
		if (values == null || values.size() != width * height
				|| values.contains(null))
			return NaM();
		return immutableMatrix(width, height, values);
	}

	/**
	 * Create a Matrix with given dimensions and values.
	 * 
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @param values
	 *            the values (the first n elements are the first row, the
	 *            following the second one and so forth)
	 * @return a valid Matrix object or NaM if any argument is or contains null
	 *         or values.size() != width*height
	 */
	public static <T> MutableMatrix<T> mutableMatrix(int width, int height,
			List<T> values) {
		if (values == null || values.size() != width * height
				|| values.contains(null))
			return NaMM();
		return MutableMatrixImpl.create(width, height, values);
	}

	/**
	 * Not a Matrix
	 * 
	 * @return Not a Matrix
	 */
	public static <T> Matrix<T> NaM() {
		return NaM.create();
	}

	/**
	 * Not a Mutable Matrix create *
	 * 
	 * @return Not a Mutable Matrix
	 */
	public static <T> MutableMatrix<T> NaMM() {
		return NaMM.create();
	}

	/**
	 * Not a Graph
	 * 
	 * @return Not a Graph
	 */
	public static Graph NaG() {
		return NaG.create();
	}

	/**
	 * Not an Ant
	 * 
	 * @return Not an Ant
	 */
	public static Ant NaA() {
		return NaA.create();
	}

	/**
	 * Not an Path
	 * 
	 * @return Not an Ant
	 */
	public static Path NaP() {
		return NaP.create();
	}

	/**
	 * Not a Simulation
	 * 
	 * @return Not a Simulation
	 */
	public static Simulation NaS() {
		return NaS.create();
	}
    
	
    
    /**
     * Not a Traveling Salesman Probelm
     * @return Traveling Salesman Probelm
     */
    public static TSP NaTSP() {
        return NaTSP.create();
    }

    /**
     * not a PheromoneElement
     * @return not a PheromoneElement
     */
    public static PheromoneElement NaPE() {
        return NaPE.create();
    }

	/**
	 * Create a simulation with a defined number of ants starting at once
	 * 
	 * @param graph
	 * @param antsQuantity
	 *            total number of ants in the graph, pushed at once
	 * 
	 * @return Simulation
	 * 
	 */
	public static Simulation simulation(Graph graph, int antsQuantity) {
		if (antsQuantity < 0) {
			return Values.NaS();
		}
		return SimulationImpl.create(graph, antsQuantity);
	}

	/**
	 * Create a simulation with a defined number of ants starting at once and
	 * logs states of all graphs
	 * 
	 * @param graph
	 * @param antsQuantity
	 *            total number of ants in the graph, pushed at once
	 * @param logStates
	 *            simulation log states or not
	 * 
	 * @return Simulation
	 * 
	 */
	public static Simulation simulation(Graph graph, int antsQuantity,
			boolean logStates) {
		if (antsQuantity < 0) {
			return Values.NaS();
		}
		return SimulationImpl.create(graph, antsQuantity, logStates);
	}

	/**
	 * Create a simulation with a defined number of ants in total starting step
	 * by step
	 * 
	 * @param graph
	 * @param antsQuantity
	 *            total number of ants in the graph
	 * @param antsByStep
	 *            number of ants, pushed at every step
	 * 
	 * @return Simulation
	 * 
	 */
	public static Simulation simulation(Graph graph, int antsQuantity,
			int antsByStep) {
		if (antsQuantity < 0 || antsByStep < 0) {
			return Values.NaS();
		}
		return SimulationImpl.create(graph, antsQuantity, antsByStep);
	}

	/**
	 * Create a simulation with a defined number of ants in total starting step
	 * by step and logs states of all graphs
	 * 
	 * @param graph
	 * @param antsQuantity
	 *            total number of ants in the graph
	 * @param antsByStep
	 *            number of ants, pushed at every step
	 * @param logStates
	 *            simulation log states or not
	 * 
	 * @return Simulation
	 * 
	 */
	public static Simulation simulation(Graph graph, int antsQuantity,
			int antsByStep, boolean logStates) {
		if (antsQuantity < 0 || antsByStep < 0) {
			return Values.NaS();
		}
		return SimulationImpl
				.create(graph, antsQuantity, antsByStep, logStates);
	}

	/**
	 * Create a Path
	 * 
	 * @param waypoints
	 *            the waypoints in order of traversal
	 * @param distance
	 *            the total distance of the path
	 * @return a valid Path object or Path(EmptyList, -1) if waypoints is or
	 *         contains null or if distance is negative
	 */
	public static Path path(List<Integer> waypoints, double distance) {
		if (waypoints == null || waypoints.contains(null) || distance < 0)
			return NaP();
		return PathImpl.create(waypoints, distance);
	}

	/**
	 * Create a Graph from Array
	 * 
	 * @param args
	 *            the array from which the Graph will be created
	 * @return a valid Graph Object
	 */
	public static Graph graphFromList(double... args) {
		if (!graphPreCheck(args)) {
			return NaG();
		}
		List<Double> l = new ArrayList<Double>();
		for (Double elem : args) {
			l.add(elem);
		}
		MutableMatrix<Double> m = mutableMatrix((int) Math.sqrt(args.length),
				(int) Math.sqrt(args.length), l);

		return graph(m);
	}

	/**
	 * PreConditionCheck for the Method "graphFromList"
	 * 
	 * @param args
	 *            the array from which the Graph will be created
	 * @return a boolean to show if given Array is valid
	 */
	private static boolean graphPreCheck(double[] x) {
		double i = Math.floor(Math.sqrt(x.length));
		if (i == Math.sqrt(x.length)) {
			return true;
		}
		return false;
	}

	/**
	 * Create a new ant -> public for testing purposes only -> should be package
	 * private
	 * 
	 * @param startNode
	 * @param alpha
	 * @param g
	 * @return Ant
	 */
	public static Ant ant(int startNode, double alpha, Graph g) {
		return AntImpl.create(startNode, alpha, g);
	}
	
	public static Ant ant(double alpha, Graph g) {
		return AntImpl.create(alpha, g);
	}

	/**
	 * Create a new Graph
	 * 
	 * @param matrix
	 * @return Graph
	 */
	public static Graph graph(Matrix<Double> matrix) {
		return GraphImpl.create(matrix);
	}

	/**
	 * Create a new ImmutableMatrixImpl<E>
	 * 
	 * @param width
	 * @param height
	 * @param values
	 * @return ImmutableMatrixImpl<E>
	 */
	public static <E> ImmutableMatrixImpl<E> immutableMatrix(int width,
			int height, List<E> values) {
		return ImmutableMatrixImpl.create(width, height, values);
	}

	/**
	 * Create a new Darstellung
	 * 
	 * @param g
	 * @return Darstellung
	 */
	public static Darstellung darstellung(Graph g) {
		return Darstellung.create(g);
	}
	
	/**
	 * Erstellt eine Graphendarstellung die NUR den Path p enthällt
     * 
	 * @param g
	 * @param p
	 * @return Darstellung
	 */
	   public static Darstellung darstellung(Graph g, Path p) {
	        return Darstellung.create(g, p);
	    }

	public static PrintGraph printableGraph(Graph g) {
		return PrintGraph.create(g);
	}

	public static <E extends Comparable<? super E>> PermutationIterator<E> permutationIterator(
			List<E> l) {
		return PermutationIterator.create(l);
	}
	
    /**
     * not a PheromoneElement
     * @param from
     * @param to
     * @param pheromone
     * @return not a PheromoneElement
     */
    public static PheromoneElement pheromoneElement(int from, int to,
            double pheromone) {
        if (from <= 0 || to <= 0 || pheromone < 0) {
            return NaPE();
        }
	    return PheromoneElementImpl.valueOf(from, to, pheromone);
	}

    /**
     * TSP Lösung per ANT Algorithmus
     * @return AcoTsp
     */

    public static TSP acoTSP() {
        return AcoTSP.create();
    }

    public static TSP acoTSP(int antsQuantity, int antsByStep) {
        if (antsQuantity < 0 || antsByStep < 0) {
            return NaTSP();
        }
        return AcoTSP.create(antsQuantity, antsByStep);
    }

    /**
     * TSP Ant Algortihmus
     * @param antsQuantity
     * @param antsByStep
     * @param number
     * @param runForSeconds    gibt an ob number maxSeconds(true) oder maxSteps(false)
     * @return AcoTSP
     */
    public static TSP acoTSP(int antsQuantity, int antsByStep, int number, boolean runForSeconds) {
        if(antsQuantity < 0 || antsByStep < 0  || number < 0  ) {return NaTSP();}
         return AcoTSP.create(antsQuantity, antsByStep, number, runForSeconds);
     }
}
