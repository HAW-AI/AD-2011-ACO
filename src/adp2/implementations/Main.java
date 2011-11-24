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
	
	public static void main(String[] args) {
		List<Integer> dist = Arrays.asList(0, 2, 10, 1, 20, 1, 20,
				3, 20, 2, 0, 2, 20, 20, 20, 20, 20, 20,
				10, 2, 0, 1, 14, 20, 20, 20, 20, 1, 20,
				1, 0, 20, 20, 20, 20, 20, 20, 20, 14, 20,
				0, 2, 20, 20, 20, 1, 20, 20, 20, 2, 0,
				3, 20, 20, 20, 20, 20, 20, 20, 3, 0, 10,
				2, 3, 20, 20, 20, 20, 20, 10, 0, 4, 20,
				20, 20, 20, 20, 20, 2, 4, 0);
		List<Integer> graphy = Arrays.asList(  // 5 x 5 matrix
				0, 1, 2, 4, 5,
				1, 0, 6, 2, 3,
				2, 6, 0, 9, 1,
				4, 2, 9, 0, 3,
				5, 3, 1, 3, 0);
		
		List<Integer> dist2 = Arrays.asList(
				0, 2, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 
				2, 0, 2, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 
				20, 2, 0, 1, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 0,
				3, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 3, 0, 2, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 2, 0,
				3, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 3, 0, 2, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 2, 0,
				4, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 4, 0, 1, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 0,
				2, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 2, 0, 3, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 3, 0,
				1, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 1, 0, 1, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 20, 20, 20, 1, 0,
				2, 20, 20, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 2, 0);
		List<Integer> easy = Arrays.asList(
				0,4,
				4,0);
		Matrix<Integer> distM = ImmutableMatrixImpl.valueOf(5, 5, graphy);
		Graph g = GraphImpl.valueOf(distM);
		Simulation s1 = SimulationImpl.valueOf(g, 100);
		s1.start();
		
		Darstellung frame = new Darstellung(g);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.graph.highlightPath(s1.minPath());	
		
	}
}
