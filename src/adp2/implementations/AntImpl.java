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
	private List<Integer> path; // der aktuelle Weg
	private double weglaenge;	// die aktuelle Weglaenge
	private Set<Integer> unvisitedNodes;	//alle Knoten des Graphen, bei dem Ameise noch nicht war
	private double alpha; 
	private Graph graph;
	boolean finished;
	
	
	private AntImpl(int startNode, double alpha, Graph g){
		mynumber = number++;
		path = new ArrayList<Integer>();
		weglaenge = 0;
		path.add(startNode);
		this.graph = g;
		unvisitedNodes = g.neighbors(startNode);
		this.alpha = alpha;
		finished = false;
	}
	
	private AntImpl(double alpha, Graph g){
		mynumber = number++;
		path = new ArrayList<Integer>();
		weglaenge = 0;
		int startNode = (mynumber % g.allNodes().size()) + 1;
		path.add(startNode);
		this.graph = g;
		unvisitedNodes = g.neighbors(startNode);
		this.alpha = alpha;
		finished = false;
	}
	
	protected static Ant create(int startNode, double alpha, Graph g){
		if (g == null || g instanceof NaG || alpha < 0 || alpha > 1 || !g.allNodes().contains(startNode)) {
			return Values.NaA();
		}
		return new AntImpl(startNode, alpha, g);
	}
	
	protected static Ant create(double alpha, Graph g){
		if (g == null || g instanceof NaG || alpha < 0 || alpha > 1) {
			return Values.NaA();
		}
		return new AntImpl(alpha, g);
	}
	
	
	@Override
	public Path traveledPath() {
		return Values.path(this.path, this.weglaenge);
	}
	
	public boolean hasFinished(){
		return finished;
	}

	@Override
	public int position() {
		return this.path.get(path.size()-1);
	}
	
	
	private Set<Integer> unvisitedNeighbors(){
		Set<Integer> neighbors = graph.neighbors(position());
		neighbors.retainAll(unvisitedNodes);
		return neighbors;
	}
	
	
	/*bildet zur aktuellen Position alle unbesuchten Nachbarn dieses Knotens auf einen Balance-Wert ab
	 * 
	 * Formel zur Berechnung der Balance: [alpha * (Pheromon/maxPheromon) + [(1- alpha) * (Distanz/maxDistanz)]
	 * 			dabei geben Pheromon und Distanz die jeweiligen Werte der aktuellen Kante zurueck
	 * 			die MaxWerte die maximalen Werte aller inzidenten Kanten des aktuellen Knotens
	 */
	private Map<Integer, Double> balances(){
		Map<Integer,Double> result = new HashMap<Integer,Double>();
		//suche maximale Entfernung zu allen Nachbarn + 
		//suche maximalen Pheromongehalt auf den Kanten zu allen Nachbarn
		double maxDist = -1.0;
		double maxPher = -1.0;
		for(Integer elem : this.unvisitedNeighbors()){
			if(graph.distance(position(), elem) > maxDist){
				maxDist = graph.distance(position(), elem);
			}
			if(graph.intensity(position(), elem) > maxPher){
				maxPher = graph.intensity(position(), elem);
			}
		}
		
		// berechne die balances und speichere sie in der Map
		for(Integer elem : this.unvisitedNeighbors()){
			result.put(elem,balance1(maxDist, maxPher, graph.intensity(position(), elem), graph.distance(position(), elem)));
		}
		return result;
		
		
	}
	
	// bildet eine balance zwischen 1 und 1001
	private double balance1(double maxDist, double maxPher, double pher, double dist){
		double distanz;
		if(!(maxDist == 0)){
			distanz = 1 - (dist/maxDist);
		}
		else{
			distanz = 1;
		}
		
		double pheromon;
		if(!(maxPher == 0)){
			pheromon = pher/maxPher;
		}else{
			pheromon = 0;
		}
		
		//double balance = alpha * (pheromon/10) + (1-alpha) * distanz;
		double balance = alpha * pheromon + (1-alpha) * distanz;
		return balance * 1000 + 1; // balance * 1000, damit die +1 nicht stark ins Gewicht faellt
	}
	
	// Bestimmt die Balance mit alpha * pheromon + (1-aplha) * (maxDist -distanz)
	private double balance2(double maxDist, double maxPher, double pher, double dist){
		double distanz;
		if(!(maxDist == 0)){
			distanz = maxDist-dist;
		}
		else{
			distanz = 0;
		}
		
		double pheromon=pher;
		
		double balance = alpha * pheromon + (1-alpha) * distanz;
		return balance;
	}
	
	private double sumOfValues(Map<?,Double> m){
		double summe = 0;
		for(Double elem : m.values()){
			summe += elem;
		}
		return summe;
	}

	@Override
	public void step() {
		if(finished) System.out.println("Tote Ameise, kann nicht laufen");
		else if (!finished){
			if(unvisitedNodes.isEmpty()){
				unvisitedNodes.add(path.get(0));
			}
			
			
			 
			// berechnung der balances
			Map<Integer,Double> probability = balances();		
			
			if (probability.isEmpty()) {
				finished = true;
				System.out.println(this + " fertig");
			} else {
			
			/*
			 * bildet die Summe aus allen Balancewerten ==> aus den einzelnen Werten und der Summe wird im 
			 * naechsten Schritt die Wahrscheinlichkeit gebildet
			 */
			double summe = sumOfValues(probability);
			
			
			
			/*bildet die Nachbarn auf einen Wert zwischen 0 und 1 ab
			 * Wert = eigene Wahrscheinlichkeit + vorher berechneter Wert
			 * Bsp:
			 * 
			 * Node			Wahrscheinlichkeit		Wert
			 * 1			0.3						0.3
			 * 2			0.1						0.4
			 * 3			0.5						0.9
			 * 4			0.1						1.0
			 */
			double vorgaenger = 0;
			
			for(Map.Entry<Integer,Double> entry : probability.entrySet()){
				double wahrscheinlichkeit = entry.getValue()/summe + vorgaenger;
				if(wahrscheinlichkeit > 1.0) wahrscheinlichkeit = 1;
				probability.put(entry.getKey(), wahrscheinlichkeit);
				vorgaenger = wahrscheinlichkeit;
			}
			
			
			/*
			 * Integriert die zufaellig Auswahl des Weges der Ameise durch verrechnen eines
			 * Random Wertes (0.0 - 1.0) mit der vorher errechneten Wahrscheinlichkeit
			 * ==> Wahl des naechsten Punktes auf dem Weg 
			 * 
			 * 
			 * minNode evtl auf -1 setzen ==> fehlerbehandlung danach moeglich
			 */
			double wert = Math.random();
			int minNode = 1;
			double minValue = 1;
			for(Map.Entry<Integer, Double> e : probability.entrySet()){
				if(e.getValue() >= wert && e.getValue() <= minValue){
					minNode = e.getKey();
					minValue = e.getValue();
				}
			}
			
			
			weglaenge += graph.distance(position(), minNode);

			path.add(minNode);
			unvisitedNodes.remove(minNode);
			
			if(unvisitedNodes.isEmpty() && path.get(path.size() - 1) == path.get(0)){
				finished = true;
				System.out.println(this + " fertig");
			}
			
			}
			
			
		}

		
	}

	@Override
	public double weglaenge() {
		return this.weglaenge;
	}

		
	public double alpha(){
		return this.alpha;
	}
	

	
	public int prevPosition(){
		if(path.size()>1) return path.get(path.size()-2);
		else return path.get(0);
	}
	
	public String toString() {
		return String.format("Ameise %d", mynumber);
	}
}
