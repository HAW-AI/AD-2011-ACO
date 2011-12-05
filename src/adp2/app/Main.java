package adp2.app;

import javax.swing.JFrame;

import adp2.implementations.Display;
import adp2.implementations.Values;
import adp2.interfaces.Graph;
import adp2.interfaces.Simulation;
import adp2.parser.TspFile;

public class Main {

    public static int width = 1280;
    public static int height = 720;
    public static final int ANT_QUANTITIY = 1000;
    public static final int ANTS_PER_STEP = 5;
    public static final int RUN_FOR_SECONDS = 30;
    public static final int RUN_FOR_STEPS = 30;

    public static void main(String[] args) {
        TspFile t = null;
//		t = TspFile.open("samples/gr21.tsp");
//		t = TspFile.open("samples/ant2.tsp");		
		t = TspFile.open("samples/ant5.tsp");
//		t = TspFile.open("samples/ant9.tsp");
//		t = TspFile.open("samples/ant15.tsp");
//		t = TspFile.open("samples/ant5Incomplete.tsp");
//        t = TspFile.open("samples/ant5NoWay.tsp");
        Graph g = Values.graph(t.matrix());
        Simulation sim = Values.simulation(g, ANT_QUANTITIY, ANTS_PER_STEP);
//        sim.runForSeconds(RUN_FOR_SECONDS);
        sim.runForSteps(RUN_FOR_STEPS);

        //Anzeige des Ergebnisses
        double distance = 0;
        if (sim.minPath().distance() < Double.MAX_VALUE) {
            distance = sim.minPath().distance();
            System.out.println("Distance: " + distance + " (MaxInt? " + (Integer.MAX_VALUE - distance == 0 ? "true" : "false") + ")");

            for (Integer point : sim.minPath().waypoints()) {
                System.out.print(point.toString() + " --> ");
            }
            System.out.println("DEAD END");
        } else {
            System.out.println("NO WAY!");
        }
        Display frame;
        if (sim.minPath().waypoints().size() > 20) {
            frame = Values.display(g, sim.minPath());
        } else {
            frame = Values.display(g);
        }

        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.graph.highlightPath(sim.minPath());



    }
}
