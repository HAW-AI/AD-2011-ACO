package antalgo_abstract.interface_algo;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * this algo 
 *
 */
public class AntAlgo {

	/**
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
					//ADDED: if ant found the right way or state then 
					//ADDED: 	solution found 
					//ADDED: end if 
				end if
			end for
			evaporate pheromone
		until termination criterion satisfied
	 */
	
	/**
	 * step in here to get a solution
	 */
	public static void untilAllAntsAreDeath(final List<Ant> ants, final int maxAnts, final Graph graph,
			final AntFactory antFactory, final PheromoneUpdateEnum type) {
		while(step(ants, maxAnts, graph, antFactory, type)){
			//someAntsAreAlive --> nextStep
		}
	}
	
	/**
	 * 
	 * @return if someAntsAreAlive is true
	 */
	public static boolean timed(final List<Ant> ants, final int maxAnts, final Graph graph,
			final AntFactory antFactory, final PheromoneUpdateEnum type, final int millisToRun) {
		
		Runnable networkingStuff = new Runnable() {
			
			@Override
			public void run() {
				while(!Thread.interrupted() && step(ants, maxAnts, graph, antFactory, type)){
					//System.out.println("=)");
				}
			}
		};
		
		Thread thread = new Thread(networkingStuff);
		thread.start();
		try {
			thread.join(millisToRun);
			if(thread.isAlive()) {
				thread.interrupt();
			}
		} catch (InterruptedException e) {
			// catch here the interruption
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return if someAntsAreAlive is true
	 */
	public static boolean numSteps(final List<Ant> ants, final int maxAnts, final Graph graph,
			final AntFactory antFactory, final PheromoneUpdateEnum type, final int numSteps) {
		for(int i=0; i<numSteps; ++i){
			boolean alive = step(ants, maxAnts, graph, antFactory, type);
			if(!alive) return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @return if someAntsAreAlive is true
	 */
	public static boolean step(final List<Ant> ants, final int maxAnts, final Graph graph,
			final AntFactory antFactory, final PheromoneUpdateEnum type) {
		
		boolean someAntsAreAlive = false;
		
		if(ants.size() < maxAnts){
			Ant ant = antFactory.antAlgo_create(graph.getStartState());// create a new ant (intern set initial)
			ants.add(ant);
		}
		
		for(Ant ant : ants){
			if(ant.isDeath()) continue; //it'll be faster to remove ant's from the list, but then they're away..
			
			someAntsAreAlive = true;
			
			//determine all feasible neighbor states
			State state = ant.getState();
			Set<State> noSecoundVisitStates = ant.getNoSecoundVisitStates();
			Set<State> neighborStates = graph.getNeighborStates(state);
			neighborStates.removeAll(noSecoundVisitStates);
			
			//if solution found or no feasible neighbor state then
			if(ant.solutionFound() || neighborStates.isEmpty()){
				//kill ant
				ant.kill();
				if(type == PheromoneUpdateEnum.delayed){
					//evaluate solution //TODO: not done here
					//deposit pheromone on all used edges according to a a strategy
					List<State> path = ant.getPath();
					graph.depositPheromone_Delayed(path);
				}
			}else{
				//stochastically select a feasible neighbor state
				SortedMap<Double, State> weightMap = new TreeMap<Double, State>();
				for(State _state : neighborStates){
					double distance = graph.getDistance(state, _state);
					double pheromon = graph.getPheromon(state, _state);
					double result = antFactory.antAlgo_getWeight(distance, pheromon);
					weightMap.put(result, _state);
				}
				
				Double resKey = antFactory.antAlgo_selectResult(weightMap.keySet());
				if(!weightMap.containsKey(resKey)) throw new IllegalArgumentException("StateWeighter illegal weight");
				State nextState = weightMap.get(resKey);
				
				if(type == PheromoneUpdateEnum.stepByStep){
					graph.depositPheromone_StepByStep(state, nextState); //deposit pheromone on the used edge
				}
				
				ant.nextState(nextState);
				
				if(graph.isSolution(ant.getPath())){
					ant.setSolutionFound();
				}
			}
		}
		graph.evaporatePheromone();
		
		return someAntsAreAlive;
	}
	
}
