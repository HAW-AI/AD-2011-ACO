package adp2.implementations;

import adp2.interfaces.Ant;
import adp2.interfaces.Graph;
import adp2.interfaces.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AntImpl implements Ant {

    private static int number = 0;
    private int mynumber;
    private List<Integer> path;             // current path
    private double pathlength;              // length of current path
    private Set<Integer> unvisitedNodes;	// nodes of the graph the ant hasn't passed yet
    private double alpha;
    private Graph graph;
    boolean finished;

    private AntImpl(int startNode, double alpha, Graph g) {
        mynumber = number++;
        path = new ArrayList<Integer>();
        pathlength = 0;
        path.add(startNode);
        this.graph = g;
        unvisitedNodes = g.neighbors(startNode);
        this.alpha = alpha;
        finished = false;
    }

    private AntImpl(double alpha, Graph g) {
        mynumber = number++;
        path = new ArrayList<Integer>();
        pathlength = 0;
        int startNode = (mynumber % g.allNodes().size()) + 1;
        path.add(startNode);
        this.graph = g;
        unvisitedNodes = g.neighbors(startNode);
        this.alpha = alpha;
        finished = false;
    }

    protected static Ant create(int startNode, double alpha, Graph g) {
        if (g == null || g instanceof NaG || alpha < 0 || alpha > 1 || !g.allNodes().contains(startNode)) {
            return Values.NaA();
        }
        return new AntImpl(startNode, alpha, g);
    }

    protected static Ant create(double alpha, Graph g) {
        if (g == null || g instanceof NaG || alpha < 0 || alpha > 1) {
            return Values.NaA();
        }
        return new AntImpl(alpha, g);
    }

    @Override
    public Path traveledPath() {
        return Values.path(this.path, this.pathlength);
    }

    @Override
    public boolean hasFinished() {
        return finished;
    }

    @Override
    public int position() {
        return this.path.get(path.size() - 1);
    }

    private Set<Integer> unvisitedNeighbors() {
        Set<Integer> neighbors = graph.neighbors(position());
        neighbors.retainAll(unvisitedNodes);
        return neighbors;
    }

    /*
     * Returns the balance-values of every node connected to the current one the ant hasn't passed yet
     * bildet zur aktuellen Position alle unbesuchten Nachbarn dieses Knotens auf einen Balance-Wert ab
     * 
     * Balances are calculated as [alpha * (Pheromone/maxPheromone) + [(1- alpha) * (Distance/maxDistance)]
     *                      wherein  Pheromone & Distance are the values for the current edge
     *                      and the maxValues are the highest values of all checked edges connected to the current node
     */
    public Map<Integer, Double> balances() {
        Map<Integer, Double> result = new HashMap<Integer, Double>();
        //find highest distance to all neighbors  + 
        //find highest pheromone saturation for each outgoing edge
        double maxDist = -1.0;
        double maxPher = -1.0;
        for (Integer elem : this.unvisitedNeighbors()) {
            if (graph.distance(position(), elem) > maxDist) {
                maxDist = graph.distance(position(), elem);
            }
            if (graph.intensity(position(), elem) > maxPher) {
                maxPher = graph.intensity(position(), elem);
            }
        }

        // Calculate balace-values and put them into the map
        for (Integer elem : this.unvisitedNeighbors()) {
            result.put(elem, balance1(maxDist, maxPher, graph.intensity(position(), elem), graph.distance(position(), elem)));
        }
        return result;
    }

    // Returns a balance-value between 1 and 1001
    private double balance1(double maxDist, double maxPher, double pher, double dist) {
        double distance;
        if (!(maxDist == 0)) {
            distance = 1 - (dist / maxDist);
        } else {
            distance = 1;
        }

        double pheromones;
        if (!(maxPher == 0)) {
            pheromones = pher / maxPher;
        } else {
            pheromones = 0;
        }

        //double balance = alpha * (pheromones/10) + (1-alpha) * distance;
        double balance = alpha * pheromones + (1 - alpha) * distance;
        return balance * 1000 + 1; // balance * 1000, so the +1 doesn't change the balance too much
    }

    // Creates a balance-value as alpha * pheromones + (1-alpha) * (maxDist -distance)
    private double balance2(double maxDist, double maxPher, double pher, double dist) {
        double distance;
        if (!(maxDist == 0)) {
            distance = maxDist - dist;
        } else {
            distance = 0;
        }
        double pheromones = pher;
        double balance = alpha * pheromones + (1 - alpha) * distance;
        return balance;
    }

    @Override
    public double sumOfValues(Map<?, Double> m) {
        double sum = 0;
        for (Double elem : m.values()) {
            sum += elem;
        }
        return sum;
    }

//    @Override
//    public void step() {
//        if (finished) {
//            System.out.println("Tote Ameise, kann nicht laufen");
//        } else if (!finished) {
//            if (unvisitedNodes.isEmpty()) {
//                unvisitedNodes.add(path.get(0));
//            }
//
//            // calculation of balances
//            Map<Integer, Double> probabilities = balances();
//
//            if (probabilities.isEmpty()) {
//                finished = true;
//                System.out.println(this + " fertig");
//            } else {
//
//                /*
//                 * Adds up the balance-values; the sum and single values
//                 * are needed to calculate the probabilities in the next step
//                 */
//                double sum = sumOfValues(probabilities);
//
//                /*
//                 * maps the neighbors to a value between 0 and 1
//                 * Value = own probability + pre-calculated value
//                 * Ex.:
//                 * 
//                 * Node                 Probability             Value
//                 * 1			0.3                     0.3
//                 * 2			0.1                     0.4
//                 * 3			0.5			0.9
//                 * 4			0.1			1.0
//                 */
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
//
//                /*
//                 * Determines the random choice of a path by the ant using a random value (0.0~1.0)
//                 * and the calculated probabilities
//                 * ==> Chooses the ant's next node
//                 * 
//                 * You may want to set minNode to -1 for debugging purposes
//                 */
//                double value = Math.random();
//                int minNode = 1;
//                double minValue = 1;
//                for (Map.Entry<Integer, Double> e : probabilities.entrySet()) {
//                    if (e.getValue() >= value && e.getValue() <= minValue) {
//                        minNode = e.getKey();
//                        minValue = e.getValue();
//                    }
//                }
//
//                pathlength += graph.distance(position(), minNode);
//
//                path.add(minNode);
//                unvisitedNodes.remove(minNode);
//
//                if (unvisitedNodes.isEmpty() && path.get(path.size() - 1) == path.get(0)) {
//                    finished = true;
//                    System.out.println(this + " fertig");
//                }
//            }
//        }
//    }

    @Override
    public double pathLength() {
        return this.pathlength;
    }

    @Override
    public double alpha() {
        return this.alpha;
    }

    @Override
    public int prevPosition() {
        if (path.size() > 1) {
            return path.get(path.size() - 2);
        } else {
            return path.get(0);
        }
    }

    @Override
    public String toString() {
        return String.format("Ant %d", mynumber);
    }

	@Override
    public Set<Integer> getUnvisitedNodes() {
	    return unvisitedNodes;
    }
	
	@Override
	public List<Integer> getPath() {
		return path;
	}

	@Override
    public void setFinished(boolean b) {
		finished = b;
    }
	
	@Override
	public void setPathLength(double pathLength) {
		this.pathlength = pathLength;
	}
}
