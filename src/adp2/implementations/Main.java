package adp2.implementations;

import java.util.List;
import java.util.Arrays;

import javax.swing.JFrame;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.Simulation;

public class Main {
	public static int width = 1280;
	public static int height = 720;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		List<Double> dist = Arrays.asList(0.0, 2.0, 10.0, 1.0, 20.0, 1.0, 20.0,
				3.0, 20.0, 2.0, 0.0, 2.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				10.0, 2.0, 0.0, 1.0, 14.0, 20.0, 20.0, 20.0, 20.0, 1.0, 20.0,
				1.0, 0.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 14.0, 20.0,
				0.0, 2.0, 20.0, 20.0, 20.0, 1.0, 20.0, 20.0, 20.0, 2.0, 0.0,
				3.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 3.0, 0.0, 10.0,
				2.0, 3.0, 20.0, 20.0, 20.0, 20.0, 20.0, 10.0, 0.0, 4.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 2.0, 4.0, 0.0);
		List<Double> graphy = Arrays.asList(  // 5 x 5 matrix
				0.0, 1.0, 2.0, 4.0, 5.0,
				1.0, 0.0, 6.0, 2.0, 3.0,
				2.0, 6.0, 0.0, 9.0, 1.0,
				4.0, 2.0, 9.0, 0.0, 3.0,
				5.0, 3.0, 1.0, 3.0, 0.0);
		
		List<Double> dist2 = Arrays.asList(
				0.0, 2.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 
				2.0, 0.0, 2.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 
				20.0, 2.0, 0.0, 1.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 1.0, 0.0,
				3.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 3.0, 0.0, 2.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 2.0, 0.0,
				3.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 3.0, 0.0, 2.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 2.0, 0.0,
				4.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 4.0, 0.0, 1.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 1.0, 0.0,
				2.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 2.0, 0.0, 3.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 3.0, 0.0,
				1.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 1.0, 0.0, 1.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 1.0, 0.0,
				2.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0,
				20.0, 20.0, 20.0, 20.0, 2.0, 0.0);
		List<Double> easy = Arrays.asList(
				0.0,4.0,
				4.0,0.0);
		Matrix<Double> distM = ImmutableMatrixImpl.valueOf(5, 5, graphy);
		Graph g = GraphImpl.valueOf(distM);
		Simulation sim = SimulationImpl.valueOf(g, 10, 3);
		sim.start();
		
    	//Anzeige des Ergebnisses
    	if(sim.minPath().distance() > 0){
    		System.out.println("Distance: " + sim.minPath().distance());
	    	
	    	for (Integer point : sim.minPath().waypoints()) {
	    		System.out.print(point.toString() + " --> ");
	    	}
	    	System.out.println("DEAD END");
    	} else{
    		System.out.println("NO WAY!");
    	}
		
		
		Darstellung frame = new Darstellung(g);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.graph.highlightPath(sim.minPath());	
		
	}
}
