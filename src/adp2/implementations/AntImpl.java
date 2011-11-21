package adp2.implementations;

import adp2.interfaces.Graph;
import adp2.interfaces.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
	
	

public class AntImpl implements Ant {
	private List<Integer> path; // der aktuelle Weg
	private int weglaenge;	// die aktuelle Weglaenge
	private int startNode;	//startknoten
	private Set<Integer> unvisitedNodes;	//alle Knoten des Graphen, bei dem Ameise noch nicht war
	private double alpha; 
	private Graph g;
	boolean finished;
	
	
	private AntImpl(int startNode, double alpha, Graph g){
		path = new ArrayList<Integer>();
		weglaenge = 0;
		this.startNode = startNode;
		this.g = g;
		unvisitedNodes = g.allNodes();
		this.alpha = alpha;
		finished = false;
	}
	
	public static Ant valueOf(int startNode, double alpha, Graph g){
		return new AntImpl(startNode, alpha, g);
	}
	
	
	@Override
	public List<Integer> traveledPath() {
		return this.path;
	}
	
	public boolean hasFinished(){
		return finished;
	}

	@Override
	public int position() {
		if(path.size() == 0) return -1;
		return this.path.get(path.size()-1);
	}
	
	private double balance(double pher, double dist){
		double result = (alpha * pher) + ((1-alpha) * (10.0/dist));
		return result;
	}

	@Override
	public void step() {
		
		 /*bildet die Nachbarn einen Wert ab, der sich aus Pheromongehalt und Weglaenge ergibt
		  *  ==> balance aus den beiden Werten
		  *  die Entfernung geht mit 1/Enfernung ein, da kleinere Entfernungen besser als größere
		  */
		Map<Integer,Double> probability = new HashMap<Integer,Double>();
		
		for(Integer elem : g.neighbors(position())){
				probability.put(elem,balance(g.intensity(position(), elem), g.distance(position(), elem)));
		}
		
		
		/*
		 * bildet die Summe aus allen Balancewerten ==> aus den einzelnen Werten und der Summe wird im 
		 * naechsten Schritt die Wahrscheinlichkeit gebildet
		 */
		double summe = 0;
		for(Double elem : probability.values()){
			summe += elem;
		}
		
		
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
		System.out.println("Prob: " + probability);
		
		
		/*
		 * Integriert die zufaellig Auswahl des Weges der Ameise durch verrechnen eines
		 * Random Wertes (0.0 - 1.0) mit der vorher errechneten Wahrscheinlichkeit
		 * ==> Wahl des naechsten Punktes auf dem Weg 
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
		
		
		weglaenge += g.distance(position(), minNode);
		path.add(minNode);
		unvisitedNodes.remove(minNode);
		
		if(unvisitedNodes.isEmpty() && path.get(path.size() - 1) == startNode){
			finished = true;;
		}
		
	}

	@Override
	public int weglaenge() {
		return this.weglaenge;
	}

	
	
	public double alpha(){
		return this.alpha;
	}
	
	
	

}
