package adp2.implementations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.Path;

import com.mxgraph.swing.mxGraphComponent;

public class Darstellung extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static int width = 1280;
	public final static int height = 720;

	public Graph graph;

	public Darstellung(Graph g) {
	super("ACO");
	graph  = g;
	mxGraphComponent graphComponent = new mxGraphComponent((GraphImpl) graph);
	getContentPane().add(graphComponent);
	}
	
	
	

	public static void main(String[] args) {
		
		//TestGraph von Andi und Basti
		List<Integer> dist2 = Arrays.asList(0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0, 1, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 1, 0, 3, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 3, 0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0, 3, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 3, 0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0, 4, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 4, 0, 1, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 1, 0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0, 3, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 3, 0, 1, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 1, 0, 1, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 1, 0, 2, 20, 20, 20, 20, 20, 20, 20,
				20, 20, 20, 20, 20, 20, 2, 0);
		

		Matrix<Integer> distM = Values.matrix(15, 15, dist2);

		Darstellung frame = new Darstellung(GraphImpl.valueOf(distM));
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		Path p = Values.path(Arrays.asList(0, 1, 2, 3, 5, 9), 0); 
		frame.graph.highlightPath(p);					// <-- minDist Path da rein
	}

}