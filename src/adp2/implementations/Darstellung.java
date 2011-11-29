package adp2.implementations;

import javax.swing.JFrame;

import adp2.interfaces.Graph;

import com.mxgraph.swing.mxGraphComponent;

public class Darstellung extends JFrame {

	private static final long serialVersionUID = 1L;

	public PrintGraph graph;
	
	protected static Darstellung create(Graph g){
		return new Darstellung(g);
	}

	private Darstellung(Graph g) {
	super("ACO");
	graph  = Values.printableGraph(g);
	mxGraphComponent graphComponent = new mxGraphComponent((PrintGraph) graph);
	getContentPane().add(graphComponent);
	}
}
