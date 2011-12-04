package adp2.implementations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import static adp2.implementations.Values.*;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation {

    Graph currentGraph;
    List<Graph> graphStates;
    List<Ant> antList = new ArrayList<Ant>();
    List<PheromoneElement> pheromoneUpdateList = new ArrayList<PheromoneElement>();
    File file = new File("..\\test.log");
    int antQuantity;
    int antsLaunched = 0;
    int antsPerStep = 0;             //Number of ants added per step
    double antAlpha = 0.3;
    int startPoint = 1;
    double pheromoneDecrease = 1.0;
    double pheromoneIntensity = 10.0;
    // The Simulation should not log the states of all Graphs by default
    final boolean logStates;
    double bestDistance = Double.MAX_VALUE;
    public List<Integer> bestPath = new ArrayList<Integer>();

    //Start all ants at once
    protected static Simulation create(Graph graph, int antsQuantity) {
        return new SimulationImpl(graph, antsQuantity);
    }

    //Start ants in waves of specified size
    protected static Simulation create(Graph graph, int antsQuantity,
            int antsPerStep) {
        return new SimulationImpl(graph, antsQuantity, antsPerStep);
    }

    //Start all ants at once
    // Log States
    protected static Simulation create(Graph graph, int antsQuantity,
            boolean logStates) {
        return new SimulationImpl(graph, antsQuantity, logStates);
    }

    //Start ants in waves of specified size
    // Log States
    protected static Simulation create(Graph graph, int antsQuantity,
            int antsPerStep, boolean logStates) {
        return new SimulationImpl(graph, antsQuantity, antsPerStep, logStates);
    }

    private SimulationImpl(Graph graph, int antsQuantity) {
        setGraph(graph);
        setAntQuantity(antsQuantity);
        addAnts(antsQuantity);
        graphStates = new ArrayList<Graph>();
        logStates = false;
    }

    private SimulationImpl(Graph graph, int antQuantity, int antsPerStep) {
        setGraph(graph);
        setAntQuantity(antQuantity);
        setAntsPerStep(antsPerStep);
        graphStates = new ArrayList<Graph>();
        logStates = false;
    }

    private SimulationImpl(Graph graph, int antQuantity, boolean logStates) {
        setGraph(graph);
        setAntQuantity(antQuantity);
        addAnts(antQuantity);
        graphStates = new ArrayList<Graph>();
        this.logStates = logStates;
    }

    private SimulationImpl(Graph graph, int antQuantity, int antsPerStep,
            boolean logStates) {
        setGraph(graph);
        setAntQuantity(antQuantity);
        setAntsPerStep(antsPerStep);
        graphStates = new ArrayList<Graph>();
        this.logStates = logStates;
    }

    @Override
    public void run() {
        file.delete();
        boolean run = true;
        while (run) {
            run = simulate();
            writeToFile();
            writeWay();
        }
    }

    @Override
    public void runForSteps(int simulationSteps) {
        file.delete();
        boolean run = true;
        antsLaunched = 0;
        int loops = 0;
        while (run && loops < simulationSteps) {
            run = simulate();
            writeToFile();
            writeWay();
            loops++;
        }
    }

    private void writeToFile() {
        try {
            if (!bestPath().isEmpty()) {

                FileWriter fw = new FileWriter(file.getPath(), true);

                PrintWriter pw = new PrintWriter(fw);
                pw.println("\n");
                pw.println("Best path: " + bestPath());
                pw.println("Shortest way: " + bestDistance());
                pw.println("-------------------------------------");

                fw.flush();
                fw.close();

                pw.flush();
                pw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeWay() {
        System.out.println("\n");
        System.out.println("Best path: " + bestPath());
        System.out.println("Shortest way: " + bestDistance());
        System.out.println("-------------------------------------");
    }

    @Override
    public void runForSeconds(int runtimeInS) {
        file.delete();
        boolean run = true;
        antsLaunched = 0;
        long timeStart = System.currentTimeMillis();
        long timeStop = runtimeInS * 1000;
        while (run && System.currentTimeMillis() - timeStart < timeStop) {
            run = simulate();
            writeToFile();
            writeWay();
        }
    }

    private boolean simulate() {
        /*
         * antList = List of ants currently traversing the graph
         * antsPerStep = Number of ants starting each step (if given)
         * antQuantity = total ants to traverse the graph
         */


        if ((antsPerStep() != 0) && (antsLaunched < antQuantity())) {
            if ((antsLaunched + antsPerStep()) <= antQuantity()) {
                // Increase ants by antsPerStep
                antsLaunched += antsPerStep();
                addAnts(antsPerStep());
            } else if (antsLaunched < antQuantity()) {
                // Increases the number of ants to the maximum if the current step would go beyond the limit
                antsLaunched += antQuantity() - antList().size();
                addAnts(antQuantity() - antList().size());
            }
        }
        int i = 0;
        while (i < antList().size()) {
            Ant ant = antList().get(i);
            if (ant.hasFinished()) {
                if (ant.traveledPath().waypoints().size() == (currentGraph.allNodes().size() + 1) && ant.pathLength() < bestDistance()) {
                    setBestDistance(ant.pathLength());
                    setBestPath(ant.traveledPath().waypoints());

                }
                removeAnt(i);
            } else {
                ant.step(); // Choose next node & move there
                addPheromoneUpdate(ant.prevPosition(), ant.position(), pheromoneIntensity()); // Prepare pheromone updates
                i++;
            }
        }
        pheromoneDecrement();
        pheromoneIncrement();
        if (logStates) {
            graphStates.add(currentGraph);
            /*
             * For every Step this adds the current Graph to the list of
             * graphStates. Doing so gives us the ability to replay the Graph
             * transformations.
             */
            currentGraph = currentGraph.deepClone();
        }
        // Ends if all ants have traversed the graph
        return !(antList().isEmpty() && (antsLaunched == antQuantity()));
    }

    /*Getter and Setter*/
    private void setGraph(Graph graph) {
        this.currentGraph = graph;
    }

    private void setAntQuantity(int quantity) {
        antQuantity = quantity;
    }

    private int antQuantity() {
        return antQuantity;
    }

    private void setAntsPerStep(int quantity) {
        antsPerStep = quantity;
    }

    private int antsPerStep() {
        return antsPerStep;
    }

    private double bestDistance() {
        return bestDistance;
    }

    private void setBestDistance(double distance) {
        bestDistance = distance;
    }

    private List<Integer> bestPath() {
        return bestPath;
    }

    private void setBestPath(List<Integer> path) {
        bestPath = path;
    }

    private List<Ant> antList() {
        return antList;
    }

    private double antAlpha() {
        return antAlpha;
    }

    private int startPoint() {
        return startPoint;
    }

    private Graph graph() {
        return currentGraph;
    }

    private double pheromoneIntensity() {
        return pheromoneIntensity;
    }

    private double pheromoneDecrease() {
        return pheromoneDecrease;
    }

    private List<PheromoneElement> pheromoneUpdateList() {
        return pheromoneUpdateList;
    }

    /*Functions*/
    private void addAnt() {
        antList().add(Values.ant(antAlpha(), graph()));
    }

    private void addAnts(int quantity) {
        for (int i = 1; i <= quantity; i++) {
            addAnt();
        }
    }

    private void removeAnt(int antIndex) {
        antList().remove(antIndex);
    }

    private void addPheromoneUpdate(int from, int to, double d) {
        PheromoneElement pheromoneElem = pheromoneElement(from, to, d);
//	    	pheromoneElement.add(from);
//	    	pheromoneElement.add(to);
//	    	pheromoneElement.add(d);s
        if (from != to) {
            pheromoneUpdateList().add(pheromoneElem);
        }
    }

    private void pheromoneIncrement() {
        for (PheromoneElement elem : pheromoneUpdateList()) {
            currentGraph.incrementPheromones(elem.from(), elem.to(), elem.pheromone());
            currentGraph.incrementPheromones(elem.to(), elem.from(), elem.pheromone());
        }
        pheromoneUpdateList().clear();
    }

    private void pheromoneDecrement() {
        currentGraph.decrementPheromones(pheromoneDecrease());
    }

    @Override
    public Path minPath() {
        return Values.path(bestPath(), bestDistance());
    }
}
