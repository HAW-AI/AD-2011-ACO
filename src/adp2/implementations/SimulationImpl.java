package adp2.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation{

		Graph graph;	
		List<Ant> antList;
		List<List<Integer>> pheromoneUpdateList;		

		int antsByStep = 1; // Anzahl der Ameisen die pro Step hinzufge�gt werden
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
	    
	    
	    public void start(){
	    	long startzeit = System.currentTimeMillis();
	    	while(System.currentTimeMillis()-startzeit < 2000){ //Abbruch nach 10Sec
	    		int i = 0;
	    		// Ants hinzuf�gen
	    		while (i < antList.size()){
	    			System.out.println(antList.get(i).position()+" -> "+antList.get(i).weglaenge());
	    			if (antList.get(i).hasFinished()) {
	    				if (antList.get(i).weglaenge() < bestDistance) {
	    					bestDistance = antList.get(i).weglaenge();
	    					bestPath = antList.get(i).traveledPath().waypoints();
	    				}
	    				antList.remove(i);
	    			} else {
	    				System.out.println("Und nochn Schritt!");
		    			if(antList.get(i).getWaitingTime() == 0){ //Befindlich auf Knoten
		    				antList.get(i).step(); //Entscheidungsalgorithmus
		    				System.out.println("H�pf");
		    				addPheromoneUpdate(antList.get(i).prePosition()-1,antList.get(i).position()-1,pheromoneIntensity); //F�ge neu betretene kante dem vaporise set hinzu
		    			}else{
		    				antList.get(i).step();

		    				System.out.println("Hopp!");
		    			}
	    				System.out.println(antList.get(i).traveledPath().toString());
		    			i++;
	    			}
	    		}
	    		
//	    		for(Ant ant : antList()){
//	    			
//	    			if(ant.getWaitingTime() == 0){ //Befindlich auf Knoten
//	    				ant.step(); //Entscheidungsalgorithmus
//	    				addPheromoneUpdate(ant.prePosition(),ant.position(),pheromoneIntensity); //F�ge neu betretene kante dem vaporise set hinzu
//	    			}else{
//	    				ant.step();
//	    			}
//	    		}
	    		graph.decrementPheromone(pheromonDecrease());
		    	graph.incrementPheromone(pheromoneUpdateList());
	    	}
	    	//Anzeige des Ergebnisses
	    	if(bestPath.size() > 0){
	    		System.out.println("Distance: " + bestDistance);
		    	
		    	for (Integer i : bestPath) {
		    		System.out.print(i.toString() + " --> ");
		    	}
		    	//System.out.print(bestPath.get(0));
		    	System.out.println();
	    	} else{
	    		System.out.println("NO WAY!");
	    	}
	    	
	    }
		

	    /*Getter and Setter*/
	    
	    private void setGraph(Graph graph){
	    	this.graph = graph;
	    }
	    private List<List<Integer>> pheromoneUpdateList(){
	    	return pheromoneUpdateList;
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
