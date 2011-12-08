package antalgo_abstract.main;

import java.util.ArrayList;
import java.util.List;

import antalgo_abstract.impl_demo.MrAnt;
import antalgo_abstract.impl_demo.MrGraph;
import antalgo_abstract.interface_algo.Ant;
import antalgo_abstract.interface_algo.AntAlgo;
import antalgo_abstract.interface_algo.AntFactory;
import antalgo_abstract.interface_algo.Graph;
import antalgo_abstract.interface_algo.PheromoneUpdateEnum;

public class MainAntAlgoAbstract {

	/**
	 * it is only an example of the ACO algorithm
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("'impl_demo' will ownly show 'how to use the abstract_algo'.");
		System.out.println("This test will run 5 seconds and will stop after it without any output.");
		
		List<Ant> ants = new ArrayList<Ant>();
		int maxAnts = Short.MAX_VALUE;
		Graph graph = new MrGraph();
		AntFactory antFactory = new MrAnt(null);
		PheromoneUpdateEnum type = PheromoneUpdateEnum.stepByStep;
		int millisToRun = 5000;
		
		AntAlgo.timed(ants, maxAnts, graph, antFactory, type, millisToRun);
	}
	
}
