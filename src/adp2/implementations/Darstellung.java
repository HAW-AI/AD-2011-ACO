package adp2.implementations;

import javax.swing.JFrame;

import adp2.interfaces.Graph;

import com.mxgraph.swing.mxGraphComponent;

public class Darstellung extends JFrame {

	private static final long serialVersionUID = 1L;

	public Graph graph;

	public Darstellung(Graph g) {
	super("ACO");
	graph  = g;
	mxGraphComponent graphComponent = new mxGraphComponent((GraphImpl) graph);
	getContentPane().add(graphComponent);
	}
}
