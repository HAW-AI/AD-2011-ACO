package adp2.implementations;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import adp2.interfaces.*;

import com.mxgraph.swing.mxGraphComponent;

public class Display extends JFrame {

    private static final long serialVersionUID = 1L;
    public PrintGraph graph;

    protected static Display create(Graph g) {
        return new Display(g);
    }

    /**
     * @see Values.darstellung

     */
    	protected static Display create(Graph g, Path p){
	       List<Double> values = new ArrayList<Double>();
	       int temp;
	       double dist; 
	       for(int x = 1; x <= ((GraphImpl) g).distanceVar().size(); x++) {
	           for (int y = 1; y <= ((GraphImpl) g).distanceVar().size(); y++) {
	               temp = p.waypoints().indexOf(x);
	               // -1 wenn x nicht in waypoints
	               if(temp != -1 && y == p.waypoints().get(temp+1)){
	                   dist = g.distance(x, y);
	               } else {
	                   dist = 0;
	               }
	               values.add(dist);
	           }
	       }
	       Matrix<Double> m = Values.matrix(g.allNodes().size(), values);
	        return new Display(Values.graph(m));
    	}

    private Display(Graph g) {
        super("ACO");
        graph = Values.printableGraph(g);
        mxGraphComponent graphComponent = new mxGraphComponent((PrintGraph) graph);
        getContentPane().add(graphComponent);
    }
}
