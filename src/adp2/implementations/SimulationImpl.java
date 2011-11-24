package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation{

		Graph currentGraph;
		List<Graph> graphStates;
		List<Ant> antList;
		List<List<Integer>> pheromoneUpdateList;		

		int antQuantity;
		int antsByStep = 0; //Anzahl der Ameisen die pro Step hinzugefuegt werden
		double antAlpha = 0.5;
		int startPoint = 1;
		int pheromoneDecrease = 1;
		int pheromoneIntensity = 10;
		
		int bestDistance = Integer.MAX_VALUE;
		public List<Integer> bestPath = new ArrayList<Integer>();

		//Ameisen werden alle direkt rein geworfen
	    public static Simulation valueOf(Graph graph, int antsQuantity) {
			return new SimulationImpl(graph, antsQuantity);
		}
	    
	    //Ameisen werden in Wellen rein geworfen
	    public static Simulation valueOf(Graph graph, int antsQuantity, int antsByStep) {
			return new SimulationImpl(graph, antsQuantity, antsByStep);
		}
	    
	    private SimulationImpl(Graph graph, int antsQuantity) {
	    	antList = new ArrayList<Ant>();
	    	pheromoneUpdateList = new ArrayList<List<Integer>>();
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	addAnts(antsQuantity);
	    	graphStates = new ArrayList<Graph>();
	    }
	
	    private SimulationImpl(Graph graph, int antsQuantity, int antsByStep) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	setAntsByStep(antsByStep);
	    }
	    
	    @Override
	    public void start(){
	    	long startzeit = System.currentTimeMillis();
	    	while(System.currentTimeMillis()-startzeit < 10000){ //Abbruch nach 10Sec, weitere Bedingungen kommen
	    		if(antsByStep() != 0){
	    			addAnts(antsByStep());
	    		}
	    		int i = 0;
	    		while (i < antList.size()){
	    			if (antList.get(i).hasFinished()) {
	    				if (antList.get(i).weglaenge() < bestDistance()) {
	    					setBestDistance(antList.get(i).weglaenge());
	    					setBestPath(antList.get(i).traveledPath().waypoints());
	    				}
	    				removeAnt(i); 
	    			} else {
		    			if(antList.get(i).getWaitingTime() == 0){ 	//Befindlich auf Knoten
		    				antList.get(i).step(); 					//Entscheidungsalgorithmus und einen Schritt gehen
		    				addPheromoneUpdate(antList.get(i).prePosition(),antList.get(i).position(),pheromoneIntensity); //Pheromonverteilung vorbereiten
		    			}else{
		    				antList.get(i).step();
		    			}
		    			i++;
	    			}
	    		}
	    		pheromoneDecreament();
	    		pheromoneIncrement();
	    		graphStates.add(currentGraph);
	    		/*
	    		 * For every Step this adds the current Graph to the list of graphStates
	    		 * Doing so gives us the ability to replay the Graph transformations. 
	    		 */
	    		currentGraph = currentGraph.deepClone();
	    	}	    	
	    }
		

	    /*Getter and Setter*/
	    
	    private void setGraph(Graph graph){
	    	this.currentGraph = graph;
	    }
	    
	    private void setAntQuantity(int quantity){
	    	antQuantity = quantity;
	    }
	    
	    private int antQuantity(){
	    	return antQuantity;
	    }
	    
	    private void setAntsByStep(int quantity){
	    	antsByStep = quantity;
	    }
	    
	    private int antsByStep(){
	    	return antsByStep;
	    }
	    
	    public int bestDistance(){
	    	return bestDistance;
	    }
	    
	    private void setBestDistance(int distance){
	    	bestDistance = distance;
	    }
	    
	    public List<Integer> bestPath(){
	    	return bestPath;
	    }
	    
	    private void setBestPath(List<Integer> path){
	    	bestPath = path;
	    }
	    	    
	    private List<Ant> antList(){
	    	return antList;
	    }
	    	    
	    private double antAlpha(){
	    	return antAlpha;
	    }
	    
	    private int startPoint(){
	    	return startPoint;
	    }
	    
	    private Graph graph(){
	    	return currentGraph;
	    }
	    
	    private int pheromoneDecrease(){
	    	return pheromoneDecrease;
	    }
	    
	    private List<List<Integer>> pheromoneUpdateList(){
	    	return pheromoneUpdateList;
	    }
	    
	    
	    /*Functions*/
	    
	    private void addAnt(){
	    	antList.add(AntImpl.valueOf(startPoint(), antAlpha(), graph())); /////START POINT?
	    }
	    
	    private void addAnts(int quantity){
	    	for(int i=0; i <= quantity; i++){
	    		addAnt();
	    	}
	    }
	    
	    private void removeAnt(int antIndex){
	    	antList().remove(antIndex);
	    }
	    
	    private void addPheromoneUpdate(int from,int to, int intensity){
	    	List<Integer> pheromoneElement = new ArrayList<Integer>();
	    	pheromoneElement.add(from);
	    	pheromoneElement.add(to);
	    	pheromoneElement.add(intensity);
	    	if (from != to){
	    		pheromoneUpdateList.add(pheromoneElement);
	    	}
	    }
	    
	    private void pheromoneIncrement(){
	    	for(List<Integer> list: pheromoneUpdateList()){
	    		currentGraph.incrementPheromone(list.get(0), list.get(1),list.get(2));
	    		currentGraph.incrementPheromone(list.get(1), list.get(0),list.get(2));
	    	}
	    }
	    
	    private void pheromoneDecreament(){
	    	currentGraph.decrementPheromone(pheromoneDecrease());
	    }

		@Override
		public Map<Path, Integer> frequencyMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Path minPath() {
			return Values.path(bestPath(), bestDistance());
		}
}
