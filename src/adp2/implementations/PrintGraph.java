package adp2.implementations;

import adp2.app.Main;
import adp2.interfaces.Graph;
import adp2.interfaces.Path;

import com.mxgraph.view.mxGraph;

import java.util.HashMap;
import java.util.List;

public class PrintGraph extends mxGraph {

    private final int NoOfVertexs;
    private static final HashMap<Integer, Object> VertexObjectList = new HashMap<Integer, Object>();
    private static final HashMap<Integer, Object> EdgeObjectList = new HashMap<Integer, Object>();

    private PrintGraph(Graph graph) {
        NoOfVertexs = graph.distanceVar().size();
        double step = 360 / NoOfVertexs;


        //May contain errors due to rounding

        for (Double temp = 1.0; temp <= NoOfVertexs; temp++) {

            double hyp = (((Main.HEIGHT / 2) - 50) * Math.cos(((90 - ((step * temp) / 2)) / 180 * Math.PI)) * 2);
            double gk = (Math.cos((90 - ((step * temp) / 2)) / 180 * Math.PI) * hyp);
            double x, y;
            if (temp > (NoOfVertexs / 2)) {
                x = ((Main.WIDTH / 2) - 50) + Math.sqrt((hyp * hyp) - (gk * gk));
            } else {
                x = (Main.WIDTH / 2) - 50 - Math.sqrt((hyp * hyp) - (gk * gk));
            }
            y = (Main.HEIGHT) - 100 - gk;

            VertexObjectList.put(temp.intValue(), insertVertex(getDefaultParent(), null, ((Integer) (temp).intValue()).toString(), x, y, 40, 20));
        }

        Integer edgesTemp = 1;
        for (Integer i1 = 0; i1 < NoOfVertexs; i1++) {
            for (Integer i2 = 0; i2 < NoOfVertexs; i2++) {
                if ((((GraphImpl) graph).distanceVar().get(i1, i2)) > 0 && (((GraphImpl) graph).distanceVar().get(i1, i2)) < Double.POSITIVE_INFINITY) {
                    EdgeObjectList.put(edgesTemp, insertEdge(getDefaultParent(), null, ((GraphImpl) graph).distanceVar().get(i1, i2).toString() + "       ", VertexObjectList.get(i1 + 1), VertexObjectList.get(i2 + 1), "strokeColor=black;fillColor=black"));
                    edgesTemp++;
                    EdgeObjectList.put(edgesTemp, insertEdge(getDefaultParent(), null, ((GraphImpl) graph).distanceVar().get(i1, i2).toString() + "       ", VertexObjectList.get(i2 + 1), VertexObjectList.get(i1 + 1), "strokeColor=black;fillColor=black"));
                    edgesTemp++;
                }
            }
        }
        setCellsMovable(false);
    }

    protected static PrintGraph create(Graph g) {
        return new PrintGraph(g);
    }

    public void highlightPath(Path p) {
        List<Integer> tl = p.waypoints();

        if (tl.isEmpty()) {
            return;
        }
        for (Integer i = 1; i < tl.size(); i++) {

            setCellStyle("strokeColor=green;fillColor=green;fontColor=red", getEdgesBetween(VertexObjectList.get(tl.get(i - 1)), VertexObjectList.get(tl.get(i))));

        }
        setCellStyle("strokeColor=red;fillColor=green;fontColor=red", getEdgesBetween(VertexObjectList.get(tl.get(0)), VertexObjectList.get(tl.get(tl.size() - 1))));
    }
}
