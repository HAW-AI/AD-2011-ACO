package adp2.implementations;

import javax.swing.JFrame;
import adp2.interfaces.Graph;
import adp2.interfaces.Simulation;
import adp2.parser.TspFile;

public class Main {
	public static int width = 1280;
	public static int height = 720;
	
	public static void main(String[] args) {
		TspFile t = null;
//		t = TspFile.open("samples/gr21.tsp");
//		t = TspFile.open("samples/ant2.tsp");		
//		t = TspFile.open("samples/ant5.tsp");
//		t = TspFile.open("samples/ant9.tsp");
		t = TspFile.open("samples/ant15.tsp");
		Graph g = GraphImpl.valueOf(t.matrix());
		Simulation sim = SimulationImpl.valueOf(g, 10, 3);
		sim.run();
		
    	//Anzeige des Ergebnisses
		int distance = 0;
    	if(sim.minPath().distance() > 0){
    		distance = sim.minPath().distance();
    		System.out.println("Distance: " + distance + " (MaxInt? " + (Integer.MAX_VALUE-distance==0 ? "true" : "false") + ")" );
	    	
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
