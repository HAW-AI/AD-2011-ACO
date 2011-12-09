package adp2.implementations;

import adp2.app.Main;
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
    private Graph graph;
    boolean finished = false;

    private AntImpl(int startNode, Graph g) {
        mynumber = number++;
        path = new ArrayList<Integer>();
        path.add(startNode);
		pathlength = 0;
        graph = g;
        unvisitedNodes = g.neighbors(startNode);
    }

    private AntImpl(Graph g) {
        mynumber = number++;
        path = new ArrayList<Integer>();
        pathlength = 0;
        int startNode = (mynumber % g.allNodes().size()) + 1;
        path.add(startNode);
        graph = g;
        unvisitedNodes = g.neighbors(startNode);
    }

    protected static Ant create(int startNode, Graph g) {
        if (g == null || g instanceof NaG || !g.allNodes().contains(startNode)) {
            return Values.NaA();
        }
        return new AntImpl(startNode, g);
    }

    protected static Ant create(Graph g) {
        if (g == null || g instanceof NaG) {
            return Values.NaA();
        }
        return new AntImpl(g);
    }

    public Path traveledPath() {
        return Values.path(this.path, this.pathlength);
    }

    public boolean hasFinished() {
        return finished;
    }

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
     * Balances are calculated as:
     * (alpha * (pheromone/maxPheromone) + ((1 - alpha) * (1 - (distance/maxDistance)))) * 1000 + 1
     * wherein  Pheromone & Distance are the values for the current edge
     * and the maxValues are the highest values of all checked edges connected to the current node
     */
    public Map<Integer, Double> balances(double alpha) {
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
        Main.LOGGER.finest("maxDist " + maxDist + " maxPher " + maxPher);

        // Calculate balace-values and put them into the map
        for (Integer elem : this.unvisitedNeighbors()) {
            result.put(elem, balance1(maxDist, maxPher, graph.intensity(position(), elem), graph.distance(position(), elem), alpha));
        }
        Main.LOGGER.finer(result.toString());
        return result;
    }

    // (alpha * (pheromone/maxPheromone) + ((1 - alpha) * (1 - (distance/maxDistance)))) * 1000 + 1
    // Returns a balance-value between 1 and 1001
    private double balance1(double maxDist, double maxPher, double pher, double dist, double alpha) {
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
        return ((balance * 1000 + 1) / 1000); // balance * 1000, so the +1 doesn't change the balance too much
    }

    // Creates a balance-value as alpha * pheromones + (1-alpha) * (maxDist -distance)
    private double balance2(double maxDist, double maxPher, double pher, double dist, double alpha) {
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
    
//    private double balance3(double maxDist, double maxPher, double pher, double dist) {
//        double distance;
//        if (!(maxDist == 0)) {
//            distance = 1 - (dist / maxDist);
//        } else {
//            distance = 1;
//        }
//
//        double pheromones;
//        if (!(maxPher == 0)) {
//            pheromones = pher / maxPher;
//        } else {
//            pheromones = 0;
//        }
//        
//        double soludtion;
//    }

    public double pathLength() {
        return this.pathlength;
    }

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

    public Set<Integer> getUnvisitedNodes() {
	    return unvisitedNodes;
    }
	
	public List<Integer> getPath() {
		return path;
	}

	public void finish() {
		finished = true;
	}
	
	public void updatePathLength(int minNode) {
		this.pathlength = this.pathlength + this.graph.distance(this.position(), minNode);
		adp2.app.Main.LOGGER.fine(this.toString() + ": " + path.toString() + " (" + pathlength + ")");
	}
}
