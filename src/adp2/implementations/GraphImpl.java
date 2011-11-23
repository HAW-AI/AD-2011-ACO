package adp2.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mxgraph.view.mxGraph;

import adp2.interfaces.Graph;
import adp2.interfaces.Matrix;
import adp2.interfaces.Path;

public class GraphImpl extends mxGraph implements Graph {

	private final Matrix<Integer> distance;
	private Matrix<Double> pheromones;
	private Double[][] dm;
	private Double[][] tm;
	private final HashMap<Integer,Object> VertexObjectList = new HashMap<Integer,Object>();
	private final HashMap<Integer,Object> EdgeObjectList = new HashMap<Integer,Object>();
	private final int NoOfVertexs;

	private GraphImpl(Matrix<Integer> distance, Matrix<Double> pheromones) {
		this.distance = distance;
		this.pheromones = pheromones;
		dm = new Double[distance.width()][distance.height()];
		tm = new Double[distance.width()][distance.height()];
		floydWarshall();
		

		NoOfVertexs = this.distance.height();
		double schritt = 360/NoOfVertexs;
		
		
		//B�se Rundungsfehler, kann sich vielleicht nochmal jemand angucken
		
		for (Double temp = 0.0; temp<NoOfVertexs;temp++) {
			
			double hyp =  (((Darstellung.height/2)-50) * Math.cos(((90-((schritt*temp)/2))/ 180 * Math.PI)) * 2);
			double gk = ( Math.cos((90-((schritt*temp)/2))/ 180 * Math.PI) * hyp);
			double x,y;
			if(temp > (NoOfVertexs/2)){
			x = ((Darstellung.width/2)-50)+Math.sqrt((hyp*hyp) - (gk*gk));
			}else {
			x = (Darstellung.width/2)-50-Math.sqrt((hyp*hyp) - (gk*gk));
			}
			y = (Darstellung.height)-100-gk;

			VertexObjectList.put(temp.intValue(),insertVertex(getDefaultParent(), null, ((Integer)temp.intValue()).toString(), x, y, 40,20));
		}

		Integer edgesTemp = 0;
		for(Integer i1=0;i1<NoOfVertexs;i1++){
			for(Integer i2=0;i2<NoOfVertexs;i2++){
				if(distance.get(i1,i2) >0){
				EdgeObjectList.put(edgesTemp,insertEdge(getDefaultParent(), null, distance.get(i1,i2).toString()+"       ", VertexObjectList.get(i1), VertexObjectList.get(i2),"strokeColor=black;fillColor=black"));
				edgesTemp++;
				EdgeObjectList.put(edgesTemp,insertEdge(getDefaultParent(), null, distance.get(i1,i2).toString()+"       ", VertexObjectList.get(i2), VertexObjectList.get(i1),"strokeColor=black;fillColor=black"));
				edgesTemp++;
				}
			}
		}
	}

	public static Graph valueOf(Matrix<Integer> distance, Matrix<Double> pheromones) {
		if (distance == null ||  pheromones == null) {
			return Values.NaG();
		}
		return new GraphImpl(distance, pheromones);
	}	
	
	public void highlightPath(Path p) {
		List<Integer> tl= p.waypoints();
		for(Integer i = 1; i < tl.size();i++){
			setCellStyle("strokeColor=red;fillColor=green;fontColor=red", getEdgesBetween(VertexObjectList.get(tl.get(i-1)),VertexObjectList.get(tl.get(i)))) ;
		}
		setCellStyle("strokeColor=red;fillColor=green;fontColor=red",getEdgesBetween(VertexObjectList.get(tl.get(0)),VertexObjectList.get(tl.get(tl.size()-1)))) ;
	}

	@Override
	public int distance(int von, int nach) {
		int d;
		try {
			d = distance.get(von - 1, nach - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			d = -1;
		}
		return d;
	}

	@Override
	public Set<Integer> neighbors(int node) {
		Set<Integer> result = new HashSet<Integer>();
		if (node <= 0 || node > distance.height()) {
			return result;
		}

		for (int i = 0; i < distance.width(); i++) {
			if (distance.get(node - 1, i) != -1 && i != node - 1) {
				result.add(i + 1);
			}
		}
		return result;
	}

	@Override
	public Set<Integer> allNodes() {
		Set<Integer> result = new HashSet<Integer>();
		for (int i = 1; i <= distance.height(); i++) {
			result.add(i);
		}
		return result;
	}

	@Override
	public double intensity(int von, int nach) {
		double p;
		try {
			p = pheromones.get(von - 1, nach - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			p = -1;
		}
		return p;
	}

	@Override
	public void decrementPheromone(int value) {
		for (int i = 0; i < pheromones.width(); i++) {
			for (int j = 0; j < pheromones.height(); j++) {
				if (pheromones.get(i, j) - value < 0) {
					pheromones.set(i, j, 0.);
				} else {
					pheromones.set(i, j, pheromones.get(i, j) - value);
				}
			}
		}
	}
	
	@Override
	public void incrementPheromone(List<List<Integer>> pheromoneUpdateList){
		for(List<Integer> update : pheromoneUpdateList){
			pheromones.set(update.get(0), update.get(1),pheromones.get(update.get(0), update.get(1))+update.get(2));
			pheromones.set(update.get(1), update.get(0),pheromones.get(update.get(1), update.get(0))+update.get(2));
		}
	}

	private void floydWarshall() {
		for (int i = 0; i < distance.width(); i++) {
			for (int j = 0; j < distance.height(); j++) {
				tm[i][j] = 0.0;
				if (!(i == j)) {
					if (distance.get(i, j) == -1) {
						dm[i][j] = Double.POSITIVE_INFINITY;
					} else {
						dm[i][j] = (double) distance.get(i, j);
					}
				} else {
					dm[i][j] = 0.0;
				}
			}
		}

		for (int i = 0; i < distance.width(); i++) {
			for (int j = 0; j < distance.height(); j++) {
				if (!(i == j)) {
					for (int k = 0; k < distance.width(); k++) {
						if (!(j == k)) {
							if (dm[i][k] != Math.min(dm[i][k],
									(dm[i][j] + dm[j][k]))) {
								dm[i][k] = Math.min(dm[i][k],
										(dm[i][j] + dm[j][k]));
								tm[i][k] = (double) j;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<Integer> pointsBetween(int von, int bis) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		if (von == bis) {
			return res;
		}
		res.add(von);
		res.addAll(pointsBetween_(von, bis));
		res.add(bis);
		return res;
	}

	private List<Integer> pointsBetween_(int von, int bis) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		int i = tm[von][bis].intValue();
		if (i == 0) {
			return res;
		} else {
			res.addAll(pointsBetween_(von, i));
			res.add(i);
			res.addAll(pointsBetween_(i, bis));
			return res;
		}

	}

	@Override
	public int minDist(int von, int bis) {
		return dm[von][bis].intValue();
	}
}
