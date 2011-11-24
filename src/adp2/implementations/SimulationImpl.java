package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation{

		Graph graph;	
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
	    }
	
	    private SimulationImpl(Graph graph, int antsQuantity, int antsByStep) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	setAntsByStep(antsByStep);
	    }
	    
	    @Override
	    public void start(){
	    	long startzeit = System.currentTimeMillis();
	    	while(System.currentTimeMillis()-startzeit < 10000){ //Abbruch nach 10Sec
	    		if(antsByStep() != 0){
	    			addAnts(antsByStep());
	    		}
	    		int i = 0;
	    		while (i < antList.size()){
	    			//System.out.println(antList.get(i).position()+" -> "+antList.get(i).weglaenge());
	    			if (antList.get(i).hasFinished()) {
	    				if (antList.get(i).weglaenge() < bestDistance()) {
	    					setBestDistance(antList.get(i).weglaenge());
	    					setBestPath(antList.get(i).traveledPath().waypoints());
	    				}
	    				antList.remove(i);
	    			} else {
	    				//System.out.println("Und nochn Schritt!");
		    			if(antList.get(i).getWaitingTime() == 0){ //Befindlich auf Knoten
		    				antList.get(i).step(); //Entscheidungsalgorithmus
		    				//System.out.println("Huepf");
		    				addPheromoneUpdate(antList.get(i).prePosition(),antList.get(i).position(),pheromoneIntensity); //F�ge neu betretene kante dem vaporise set hinzu
		    			}else{
		    				antList.get(i).step();

		    				//System.out.println("Hopp!");
		    			}
	    				//System.out.println(antList.get(i).traveledPath().toString());
		    			i++;
	    			}
	    		}
	    		
	    		pheromoneDecreament();
	    		pheromoneIncrement();
	    	}
	    	//Anzeige des Ergebnisses
	    	if(bestPath().size() > 0){
	    		System.out.println("Distance: " + bestDistance());
		    	
		    	for (Integer i : bestPath()) {
		    		System.out.print(i.toString() + " --> ");
		    	}
		    	System.out.println();
	    	} else{
	    		System.out.println("NO WAY!");
	    	}
	    	
	    }
		

	    /*Getter and Setter*/
	    
	    private void setGraph(Graph graph){
	    	this.graph = graph;
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
	    
	    private int pheromoneDecrease(){
	    	return pheromoneDecrease;
	    }
	    
	    private void pheromoneIncrement(){
	    	for(List<Integer> list: pheromoneUpdateList()){
	    		graph.incrementPheromone(list.get(0), list.get(1),list.get(2));
	    		graph.incrementPheromone(list.get(1), list.get(0),list.get(2));
	    	}
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
	    
	    private List<List<Integer>> pheromoneUpdateList(){
	    	return pheromoneUpdateList;
	    }
	         
	    private void pheromoneDecreament(){
	    	graph.decrementPheromone(pheromoneDecrease());
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
	    	return graph;
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
	    
	    private void removeAnt(Ant ant){
	    	antList().remove(ant);
	    }
	    

		@Override
		public Map<Path, Integer> frequencyMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Path minPath() {
			return Values.path(bestPath, bestDistance);
		}
}
