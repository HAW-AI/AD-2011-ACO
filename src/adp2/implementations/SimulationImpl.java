package adp2.implementations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import static adp2.implementations.Values.*;

import adp2.interfaces.*;

public class SimulationImpl implements Simulation{

		Graph currentGraph;
		List<Graph> graphStates;
		List<Ant> antList = new ArrayList<Ant>();
		List<PheromoneElement> pheromoneUpdateList = new ArrayList<PheromoneElement>();		
		File file = new File("..\\test.log");
		
		int antQuantity;
		int antsLaunched = 0;
		int antsByStep = 0; //Anzahl der Ameisen die pro Step hinzugefuegt werden
		double antAlpha = 0.3;
		int startPoint = 1;
		double pheromoneDecrease = 1.0;
		double pheromoneIntensity = 10.0;
		// The Simulation should not log the states of all Graphs by default
		final boolean logStates;
		
		double bestDistance = Double.MAX_VALUE;
		public List<Integer> bestPath = new ArrayList<Integer>();

		//Ameisen werden alle direkt rein geworfen
	protected static Simulation create(Graph graph, int antsQuantity) {
			return new SimulationImpl(graph, antsQuantity);
		}
	    
	    //Ameisen werden in Wellen rein geworfen
	protected static Simulation create(Graph graph, int antsQuantity,
			int antsByStep) {
			return new SimulationImpl(graph, antsQuantity, antsByStep);
		}
	    
		//Ameisen werden alle direkt rein geworfen
	    // Log States
	protected static Simulation create(Graph graph, int antsQuantity,
			boolean logStates) {
			return new SimulationImpl(graph, antsQuantity, logStates);
		}
	    
	    //Ameisen werden in Wellen rein geworfen
	    // Log States
	protected static Simulation create(Graph graph, int antsQuantity,
			int antsByStep, boolean logStates) {
			return new SimulationImpl(graph, antsQuantity, antsByStep, logStates);
		}
	    
	    private SimulationImpl(Graph graph, int antsQuantity) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	addAnts(antsQuantity);
	    	graphStates = new ArrayList<Graph>();
	    	logStates = false;
	    }
	
	    private SimulationImpl(Graph graph, int antsQuantity, int antsByStep) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	setAntsByStep(antsByStep);
	    	graphStates = new ArrayList<Graph>();
	    	logStates = false;
	    }
	    
	    private SimulationImpl(Graph graph, int antsQuantity, boolean logStates) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	addAnts(antsQuantity);
	    	graphStates = new ArrayList<Graph>();
	    	this.logStates = logStates;
	    }
	
	private SimulationImpl(Graph graph, int antsQuantity, int antsByStep,
			boolean logStates) {
	    	setGraph(graph);
	    	setAntQuantity(antsQuantity);
	    	setAntsByStep(antsByStep);
	    	graphStates = new ArrayList<Graph>();
	    	this.logStates = logStates;
	    }
	    
	    @Override
	    public void run(){
	        file.delete();
	    	boolean run = true;
	    	while(run){
	    		run = simulate();
	    		writeToFile();
	    		writeWay();
	    	}
	    }
		
	    @Override
	    public void runForSteps(int simulationSteps){
	        file.delete();
	    	boolean run = true;
	    	antsLaunched = 0;
	    	int loops = 0;
	    	while(run && loops < simulationSteps){
	    		run = simulate();
	    		writeToFile();
                writeWay();
	    		loops++;
	    	}
	    }
	    
	    private void writeToFile() {
	        try
            {
             if(!bestPath().isEmpty()){
                
                FileWriter fw = new FileWriter( file.getPath() , true );
                
                PrintWriter pw = new PrintWriter( fw );
                pw.println("\n");
                pw.println("Best path: " + bestPath());
                pw.println("Shortest way: " + bestDistance());
                pw.println("-------------------------------------");
                
                fw.flush();
                fw.close();
                
                pw.flush();
                pw.close();
             }
    }
    catch( IOException e )
    {
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
	    public void runForSeconds(int runtimeInS){
	        file.delete();
	    	boolean run = true;
	    	antsLaunched = 0;
	    	long timeStart = System.currentTimeMillis();
	    	long timeStop = runtimeInS * 1000;
	    	while(run && System.currentTimeMillis() - timeStart < timeStop){
	    		run = simulate();
	    		writeToFile();
                writeWay();
	    	}
	    }
	    
	    private boolean simulate(){
	    	/*
		 * antList = Ameisen aktuell im Graphen antsByStep = Ameisen die
		 * hinzugefuegt werden pro Step (wenn angegeben) antQuantity = Anzahl
		 * der Ameisen über maximal in den Graphen laufen
		 */ 
       
    
    		if((antsByStep() != 0) && (antsLaunched < antQuantity())){
			if ((antsLaunched + antsByStep()) <= antQuantity()) { // Ameisen um
																	// antsByStep
																	// erhoehen
    				antsLaunched += antsByStep();
    				addAnts(antsByStep());
			} else if (antsLaunched < antQuantity()) { // Ameisen um Rest <
														// antsByStep erhï¿½hen
    				antsLaunched += antQuantity()-antList().size();
    				addAnts(antQuantity()-antList().size());
    			}
    		}
    		int i = 0;
    		while (i < antList().size()){
    			Ant ant = antList().get(i);
    			if (ant.hasFinished()) {
    				if (ant.traveledPath().waypoints().size() == (currentGraph.allNodes().size() + 1) && ant.weglaenge() < bestDistance()) {
    					setBestDistance(ant.weglaenge());
    					setBestPath(ant.traveledPath().waypoints());
    					
    				}
    				removeAnt(i); 
    			} else {
				if (ant.getWaitingTime() == 0) { // Befindlich auf
																// Knoten
					ant.step(); // Entscheidungsalgorithmus und
												// einen Schritt gehen
					addPheromoneUpdate(ant.prevPosition(),
							ant.position(), pheromoneIntensity()); // Pheromonverteilung
																				// vorbereiten
	    			}else{
	    				ant.step();
	    			}
	    			i++;
    			}
    		}
    		pheromoneDecreament();
    		pheromoneIncrement();
    		if (logStates) {
	    		graphStates.add(currentGraph);
	    		/*
			 * For every Step this adds the current Graph to the list of
			 * graphStates Doing so gives us the ability to replay the Graph
			 * transformations.
	    		 */
	    		currentGraph = currentGraph.deepClone();
    		}
		// Ende wenn alle Ameisen durchgelaufen sind und keine mehr kommen (per
		// antsByStep)

        
    		
    		return !(antList().isEmpty() && (antsLaunched == antQuantity()));
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
	    
	    private double bestDistance(){
	    	return bestDistance;
	    }
	    
	    private void setBestDistance(double distance){
	    	bestDistance = distance;
	    }
	    
	    private List<Integer> bestPath(){
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
	    
	    private double pheromoneIntensity(){
	    	return pheromoneIntensity;
	    }
	    
	    private double pheromoneDecrease(){
	    	return pheromoneDecrease;
	    }
	    
	    private List<PheromoneElement> pheromoneUpdateList(){
	    	return pheromoneUpdateList;
	    }
	    
	    
	    /*Functions*/
	    
	    private void addAnt(){
	    	antList().add(Values.ant(antAlpha(), graph())); 
	    }
	    
	    private void addAnts(int quantity){
	    	for(int i=1; i <= quantity; i++){
	    		addAnt();
	    	}
	    }
	    
	    private void removeAnt(int antIndex){
	    	antList().remove(antIndex);
	    }
	    
	    private void addPheromoneUpdate(int from,int to, double d){
	    	PheromoneElement pheromoneElem = pheromoneElement(from, to, d);
//	    	pheromoneElement.add(from);
//	    	pheromoneElement.add(to);
//	    	pheromoneElement.add(d);s
	    	if (from != to){
	    		pheromoneUpdateList().add(pheromoneElem);
	    	}
	    }
	    
	    private void pheromoneIncrement(){
	    	for(PheromoneElement elem: pheromoneUpdateList()){
			currentGraph.incrementPheromone(elem.from(), elem.to(),
					elem.pheromone());
			currentGraph.incrementPheromone(elem.to(), elem.from(),
			        elem.pheromone());
	    	}
    		pheromoneUpdateList().clear();
	    }
	    
	    private void pheromoneDecreament(){
	    	currentGraph.decrementPheromone(pheromoneDecrease());
	    }

		

		@Override
		public Path minPath() {
		return Values.path(bestPath(), bestDistance());
	}

		
}
