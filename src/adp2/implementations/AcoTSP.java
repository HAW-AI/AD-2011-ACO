//package adp2.implementations;
//
//import adp2.interfaces.Graph;
//import adp2.interfaces.Matrix;
//import adp2.interfaces.Path;
//import adp2.algorithms.interfaces.Simulation;
//
///**
// * @author Kai Bielenberg (kai.bielenberg@haw-hamburg.de)
// *
// */
//public class AcoTSP implements Simulation {
//
//    private final int ANT_QUANTITY;
//    private final int ANTS_PER_STEP;
//    private final int MAX_SECONDS;
//    private final int MAX_STEPS;
//	private final double ALPHA;
//    private Simulation sim;
//
//    private AcoTSP(int antsQuantity, int antsByStep, int maxSeconds, int maxSteps, double alpha) {
//        this.ANT_QUANTITY = antsQuantity;
//        this.ANTS_PER_STEP = antsByStep;
//        this.MAX_SECONDS = maxSeconds;
//        this.MAX_STEPS = maxSteps;
//		this.ALPHA = alpha;
//
//
//    }
//
//    static AcoTSP create() {
//        return new AcoTSP(1000, 5, 0, 0, 0.4);
//    }
//
//    static AcoTSP create(int antsQuantity, int antsByStep) {
//        return new AcoTSP(antsQuantity, antsByStep, 0, 0, 0.4);
//    }
//
//    /**
//     * number = seconds if runForSeconds == true
//     * number = steps if runForSeconds == false
//     * @param antsQuantity
//     * @param antsByStep
//     * @param number
//     * @param runForSeconds
//     * @return
//     */
//    static AcoTSP create(int antsQuantity, int antsByStep, int number, boolean runForSeconds) {
//        if (runForSeconds == true) {
//            return new AcoTSP(antsQuantity, antsByStep, number, 0, 0.4);
//        } else {
//            return new AcoTSP(antsQuantity, antsByStep, 0, number, 0.4);
//        }
//
//    }
//
////    
//    /* (non-Javadoc)
//     * @see adp2.interfaces.TSP#minPath(adp2.interfaces.Matrix)
//     */
//    public Path minPath(Matrix<Double> matrix) {
//        if (matrix.size() == 0) {
//            return Values.NaP();
//        }
//        Graph graph = Values.graph(matrix);
//
//        sim = Values.simulation(graph, ANT_QUANTITY, ANTS_PER_STEP, ALPHA);
//        if (MAX_SECONDS == 0 && MAX_STEPS == 0) {
//            sim.run();
//        } else if (MAX_SECONDS == 0) {
//            sim.runForSteps(MAX_STEPS);
//        } else if (MAX_STEPS == 0) {
//            sim.runForSeconds(MAX_SECONDS);
//        } else {
//            System.out.println("Shouldn't be here");
//        }
//        return sim.minPath();
//    }
//
//	@Override
//	public void run() {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public void runForSeconds(int runtimeInS) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public void runForSteps(int simulationSteps) {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//
//	@Override
//	public Path minPath() {
//		throw new UnsupportedOperationException("Not supported yet.");
//	}
//}
