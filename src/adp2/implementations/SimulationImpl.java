package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation{

		Graph graph;	
		List<Ant> antList;
		List<List<Integer>> pheromoneUpdateList;		

		int antsByStep = 1; //Anzahl der Ameisen die pro Step hinzugefuegt werden
		double antAlpha = 0.5;
		int startPoint = 1;
		int pheromoneDecrease = 1;
		int pheromoneIntensity = 10;
		
		int bestDistance = Integer.MAX_VALUE;
		List<Integer> bestPath = new ArrayList<Integer>();

		
	    public static Simulation valueOf(Graph graph, int AntsQuantity) {
			return new SimulationImpl(graph, AntsQuantity);
		}
	
	    public static Simulation valueOf(Graph graph, int AntsQuantity, int AntsByStep) {
			return new SimulationImpl(graph, AntsQuantity, AntsByStep);
		}
	    
	    private SimulationImpl(Graph graph, int AntsQuantity) {
	    	antList = new ArrayList<Ant>();
	    	pheromoneUpdateList = new ArrayList<List<Integer>>();
	    	setGraph(graph);
	    	graph.toString();
	    	addAnts(AntsQuantity);
	    }
	
	    private SimulationImpl(Graph graph, int AntsQuantity, int AntsByStep) {
	    	setGraph(graph);
	    	addAnts(AntsQuantity);
	    	addAnts(AntsByStep);
	    }
	    
	    @Override
	    public void start(){
	    	long startzeit = System.currentTimeMillis();
	    	while(System.currentTimeMillis()-startzeit < 10000){ //Abbruch nach 10Sec
	    		int i = 0;
	    		// Ants hinzufuegen
	    		while (i < antList.size()){
	    			//System.out.println(antList.get(i).position()+" -> "+antList.get(i).weglaenge());
	    			if (antList.get(i).hasFinished()) {
	    				if (antList.get(i).weglaenge() < bestDistance) {
	    					bestDistance = antList.get(i).weglaenge();
	    					bestPath = antList.get(i).traveledPath().waypoints();
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
	    		
	    		graph.decrementPheromone(pheromonDecrease());
	    		pheromoneUpdate();
//		    	graph.incrementPheromone(pheromoneUpdateList());
	    	}
	    	//Anzeige des Ergebnisses
	    	if(bestPath.size() > 0){
	    		System.out.println("Distance: " + bestDistance);
		    	
		    	for (Integer i : bestPath) {
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
//	    private List<List<Integer>> pheromoneUpdateList(){
//	    	return pheromoneUpdateList;
//	    }
	    
	    private void pheromoneUpdate(){
	    	for(List<Integer> list: pheromoneUpdateList){
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
	         
	    private int pheromonDecrease(){
	    	return pheromoneDecrease;
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
			// TODO Auto-generated method stub
			return null;
		}
}
