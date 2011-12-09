package adp2.algorithms;

import adp2.algorithms.interfaces.TravelingSalesMan;

import adp2.app.Main;
import adp2.implementations.Values;
import java.util.*;

import static adp2.implementations.Values.*;
import adp2.interfaces.*;

public class AntColonisationOptimation implements TravelingSalesMan {

	private Graph currentGraph;
	private List<Graph> graphStates;
	private List<Ant> antList = new ArrayList<Ant>();
	private List<PheromoneElement> pheromoneUpdateList = new ArrayList<PheromoneElement>();
	
	private int antQuantity; // was 1000 in code from group 2
	private int antsPerStep; // was 5 in code from group 2
	private double alpha;
	private double pheromoneDecrease; // was 1.0 in code from group 2
	private double pheromoneIntensity; // was 10.0 in code from group 2
	
	private int antsLaunched;
	
	private double bestDistance = Double.MAX_VALUE;
	public List<Integer> bestPath = new ArrayList<Integer>();
	
	private int DELAYEDUPDATE = 1;
	private int STEPBYSTEPUPDATE = 2;
	private final boolean LOGSTATES = false;

	//Start all ants at once
	public static AntColonisationOptimation create(Graph graph, int antQuantity, int antsPerStep, double alpha, int pheromoneDecrease, int pheromoneIntensity) {
		Main.LOGGER.info("AntColonisationOptimation created! Params: antQuantity: " + antQuantity + ", antsPerStep: " + antsPerStep + ", alpha: " + alpha + ", pheromoneDecrease: " + pheromoneDecrease + ", pheromoneIntensity: " + pheromoneIntensity);
		return new AntColonisationOptimation(graph, antQuantity, antsPerStep, alpha, pheromoneDecrease, pheromoneIntensity);
	}

	private AntColonisationOptimation(Graph graph, int antQuantity, int antsPerStep, double alpha, int pheromoneDecrease, int pheromoneIntensity) {
		currentGraph = graph;
		graphStates = new ArrayList<Graph>();
		this.antQuantity = antQuantity;
		this.antsPerStep = antsPerStep;
		this.alpha = alpha;
		this.pheromoneDecrease = pheromoneDecrease;
		this.pheromoneIntensity = pheromoneIntensity;
		antsLaunched = 0;
	}

	public void run() {
		while (simulate(DELAYEDUPDATE)) {
			Main.LOGGER.info("Best path so far: " + this.bestPath() + " Shortest way: " + this.bestDistance());
		}
	}

	public void runForSteps(int simulationSteps) {
		int loops = 0;
		while (simulate(STEPBYSTEPUPDATE) && loops < simulationSteps) {
			Main.LOGGER.info("Best path so far: " + this.bestPath() + " Shortest way: " + this.bestDistance());
			loops++;
		}
	}

	public void runForSeconds(int runtimeInS) {
		long timeStart = System.currentTimeMillis();
		long timeStop = runtimeInS * 1000;
		while (simulate(DELAYEDUPDATE) && System.currentTimeMillis() - timeStart < timeStop) {
			Main.LOGGER.info("Best path so far: " + this.bestPath() + " Shortest way: " + this.bestDistance());
		}
	}

	private boolean simulate(int method) {
		Main.LOGGER.fine("Ant count " + antList.size());
		/*
		 * antList = List of ants currently traversing the graph
		 * antsPerStep = Number of ants starting each step (if given)
		 * antQuantity = total ants to traverse the graph
		 */

		/*
		// pseudocode aus der aufgabe
		repeat 
		if antCount < maxAnts then
		create a new ant
		set initial state
		end if 
		for all ants do
		determine all feasible neighbor states  
		if solution found or no feasible neighbor state then
		kill ant 
		if we use delayed pheromone update then
		evaluate solution
		deposit pheromone on all used edges according to a a strategy
		end if 
		else
		stochastically select a feasible neighbor state  
		if we use step-by-step pheromone update then
		deposit pheromone on the used edge
		end if 
		end if
		end for
		evaporate pheromone 
		until termination criterion satisfied
		 */

		if ((antsPerStep != 0) && (antsLaunched < antQuantity)) {
			if ((antsLaunched + antsPerStep) <= antQuantity) {
				// Increase ants by antsPerStep
				antsLaunched += antsPerStep;
				//PC: create a new ant
				//PC: set initial state
				addAnts(antsPerStep);
				Main.LOGGER.fine(antsPerStep + " ants created");
			} else if (antsLaunched < antQuantity) {
				// Increases the number of ants to the maximum if the current step would go beyond the limit
				antsLaunched += antQuantity - antList().size();
				//PC: create a new ant
				//PC: set initial state
				addAnts(antQuantity - antList().size());
				Main.LOGGER.fine(antQuantity - antList().size() + " ants created, maximum reached");
			}
		}
		//PC: for all ants do
		int i = 0;
		while (i < antList().size()) {
			Ant ant = antList().get(i);
			//PC: if solution found or no feasible neighbor state then
			if (ant.hasFinished()) {
				//PC: kill ant 
				Main.LOGGER.fine(antList.get(i).toString() + " removed");
				removeAnt(i);
				//PC: evaluate solution
				// algorithm gets here for every finished ant, no matter if delayed or step by step pheromone update!
				if (ant.traveledPath().waypoints().size() == (currentGraph.allNodes().size() + 1) && ant.pathLength() < bestDistance()) {
					Main.LOGGER.info(ant.toString() + " update distance from " + this.bestDistance() + " to " + ant.pathLength() + " and path from " + this.bestPath() + " to " + ant.traveledPath().waypoints());
					setBestDistance(ant.pathLength());
					setBestPath(ant.traveledPath().waypoints());
					//PC: if we use delayed pheromone update then
					if (method == DELAYEDUPDATE) {
						//PC: deposit pheromone on all used edges according to a a strategy
						// strategy is: "if better way was found". given due to solution evaluation above.
						List<Integer> waypoints = ant.traveledPath().waypoints();
						for (int j = 1; j < waypoints.size(); j++) {
							addPheromoneUpdate(waypoints.get(j - 1), waypoints.get(j), pheromoneIntensity()); // Prepare pheromone updates
						}
						// TODO: strategy part 2: optional (?) pheromone boost according to path quality (means: not just better then before, but differentiate: bit better -> boost 1, lots better -> boost 2, ...)
						pheromoneIncrement();
					}
				}
			} else {
				//PC: stochastically select a feasible neighbor state 
				stochasticNeighborSelection(ant); // Choose next node & move there
//            	ant.step(); // Choose next node & move there
				//PC: if we use step-by-step pheromone update then
				if (method == STEPBYSTEPUPDATE) {
					//PC: deposit pheromone on the used edge
					addPheromoneUpdate(ant.prevPosition(), ant.position(), pheromoneIntensity()); // Prepare pheromone updates
					pheromoneIncrement();
					// fehlt theoretisch: finished? dann: ant routing table updaten -> setbestpath + setbestdistance
				}
				i++;
			}
		}

		//PC: evaporate pheromone 
		pheromoneDecrement();
		if (LOGSTATES) {
			graphStates.add(currentGraph);
			/*
			 * For every Step this adds the current Graph to the list of
			 * graphStates. Doing so gives us the ability to replay the Graph
			 * transformations.
			 */
			currentGraph = currentGraph.deepClone();
		}
		// Ends if all ants have traversed the graph
		return !(antList().isEmpty() && (antsLaunched == antQuantity));
	}

	private void stochasticNeighborSelection(Ant ant) {
		if (ant.hasFinished()) {
			Main.LOGGER.warning("Ant dead!");
		} else if (!ant.hasFinished()) {
			if (ant.getUnvisitedNodes().isEmpty()) {
				ant.getUnvisitedNodes().add(ant.getPath().get(0));
			}

			// calculation of balances
			Map<Integer, Double> probabilities = ant.balances(alpha);

			if (probabilities.isEmpty()) {
				ant.finish();
				Main.LOGGER.fine(this.toString() + " fertig");
			} else {

				/*
				 * Adds up the balance-values; the sum and single values
				 * are needed to calculate the probabilities in the next step
				 */
//                double sum = sumOfValues(probabilities);

				/*
				 * maps the neighbors to a value between 0 and 1
				 * Value = own probability + pre-calculated value
				 * Ex.:
				 * 
				 * Node                 Probability             Value
				 * 1					0.3                     0.3
				 * 2					0.1                     0.4
				 * 3					0.5						0.9
				 * 4					0.1						1.0
				 */
//                 comment from group 3: why not use the probability? why does the highest node get the highest value?
//                double predecessor = 0;
//
//                for (Map.Entry<Integer, Double> entry : probabilities.entrySet()) {
//                    double probability = entry.getValue() / sum + predecessor;
//                    if (probability > 1.0) {
//                        probability = 1;
//                    }
//                    probabilities.put(entry.getKey(), probability);
//                    predecessor = probability;
//                }

				/*
				 * Determines the random choice of a path by the ant using a random value (0.0~1.0)
				 * and the calculated probabilities
				 * ==> Chooses the ant's next node
				 * 
				 * You may want to set minNode to -1 for debugging purposes
				 */
				int minNode = 1;
				double minValue = 0;

				double upperBound = mathFoo();
				for (Map.Entry<Integer, Double> e : probabilities.entrySet()) {
					if (e.getValue() >= minValue && e.getValue() <= upperBound) {
						minNode = e.getKey();
						minValue = e.getValue();
					}
				}
				// if random choice failed, go best way
				if (minNode == 0) {
					for (Map.Entry<Integer, Double> e : probabilities.entrySet()) {
						if (e.getValue() >= minValue) {
							minNode = e.getKey();
							minValue = e.getValue();
						}
					}
				}

				Main.LOGGER.finest("Random: " + upperBound + "; minValue: " + minValue + "; minNode: " + minNode);

				// pathlength += graph.distance(position(), minNode);
				ant.updatePathLength(minNode);

				Main.LOGGER.finer(ant.toString() + " moves from " + ant.getPath().get(ant.getPath().size() - 1) + " to " + minNode);
				ant.getPath().add(minNode);
				ant.getUnvisitedNodes().remove(minNode);

				if (ant.getUnvisitedNodes().isEmpty() && ant.getPath().get(ant.getPath().size() - 1) == ant.getPath().get(0)) {
					ant.finish();
					Main.LOGGER.fine(ant.toString() + " finished");
				}
			}
		}
	}
	
	/**
	 *
	 * @return value from 0.0 to less than 1.0 with higher values occuring more often (exponentially)
	 */
	private double mathFoo() {
		double result;
		double ran1, ran2;
		ran1 = Math.random();
		ran2 = Math.random();
		result = ran1 * ran2; // many 0.000xx to few 0.999xx
		result = 1 - result; // few 0.000xx to many 0.999xx
		return result;
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

	private void addAnt() {
		antList().add(Values.ant(graph()));
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
		if (from != to) {
			pheromoneUpdateList().add(pheromoneElem);
		}
	}

	private void pheromoneIncrement() {
		for (PheromoneElement elem : pheromoneUpdateList()) {
			Main.LOGGER.finest("Edge " + elem.from() + "_" + elem.to() + " " + elem.pheromone());
			currentGraph.incrementPheromones(elem.from(), elem.to(), elem.pheromone());
		}
		pheromoneUpdateList().clear();
	}

	private void pheromoneDecrement() {
		currentGraph.decrementPheromones(pheromoneDecrease());
	}

	public Path minPath() {
		return Values.path(bestPath(), bestDistance());
	}

	private double sumOfValues(Map<?, Double> m) {
		double sum = 0;
		for (Double elem : m.values()) {
			sum += elem;
		}
		return sum;
	}
}
