package adp2.app;

import adp2.algorithms.interfaces.TravelingSalesMan;
import javax.swing.JFrame;

import adp2.implementations.Display;
import adp2.implementations.Values;
import adp2.interfaces.Graph;
import adp2.parser.TspFile;
import java.util.logging.*;

public class Main {
	public final static Logger LOGGER = Logger.getLogger("Main");
    public final static int WIDTH = 1280;
    public final static int HEIGHT = 720;
	
	private int RUN_FOR_SECONDS = 30; // was 30 in code from group 2
	
	private final static Level LOGLEVEL = Level.INFO;
			
    public static void main(String[] args) {
		setUpLogging("%h/aco", false, true, true);
		
        TspFile t = null;
//		t = TspFile.open("samples/ant5.tsp");
		t = TspFile.open("samples/gr21.tsp");
//		t = TspFile.open("samples/ant2.tsp");		
//		t = TspFile.open("samples/ant9.tsp");
//		t = TspFile.open("samples/ant15.tsp");
//		t = TspFile.open("samples/ant5Incomplete.tsp");
//      t = TspFile.open("samples/ant5NoWay.tsp");
        Graph g = Values.graph(t.matrix());

		
		LOGGER.info("ACO");
		TravelingSalesMan aco = Values.tsmACO(g, 1000, 50, 0.4, 1, 10);
//		aco.run();		
//		aco.runForSeconds(RUN_FOR_SECONDS);
		aco.runForSteps(100);
		double distance = aco.minPath().distance();
        if (distance < Double.MAX_VALUE) {
            LOGGER.info("Distance: " + distance + ", Best Path: " + aco.minPath().waypoints().toString());	
        } else {
            LOGGER.info("No Way");	
        }
		displayTSP(aco, g);
		
//		LOGGER.info("BRUTE FORE");
//		TravelingSalesMan bf = Values.tsmBF(t.matrix());
//		bf.run();
//		LOGGER.info("Distance: " + bf.minPath().distance() + " Best Path: " + bf.minPath().waypoints().toString());	
//		displayTSP(bf, g);

    }
	
	private static void displayTSP(TravelingSalesMan tsm, Graph g) {
		Display frame;
        if (tsm.minPath().waypoints().size() > 20) {
            frame = Values.display(g, tsm.minPath());
        } else {
            frame = Values.display(g);
        }

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.graph.highlightPath(tsm.minPath());
	}
	
	/*
	 * Creates a new Logger to log to File and Console
	 * @param
	 */
	private static void setUpLogging(String logFile, boolean file, boolean console, boolean html) {
		LOGGER.setLevel(LOGLEVEL);
		
		// Logger
		Formatter textformat = new adp2.logger.SimpleTextFormatter();
		Formatter htmlformat = new adp2.logger.SimpleHtmlFormatter();

		// Console
		if (console) {
			Handler ch = new ConsoleHandler();
			ch.setFormatter(textformat);
			LOGGER.addHandler(ch);
		}

		// File
		if (file || html) {
			try {
				if ((file)) {
					Handler fh = new FileHandler(logFile + ".log");
					fh.setFormatter(textformat);
					LOGGER.addHandler(fh);
				}
				if (html) {
					Handler hh = new FileHandler(logFile + ".html");
					hh.setFormatter(htmlformat);
					LOGGER.addHandler(hh);
				}
			} catch (java.io.IOException e) {
				LOGGER.severe(e.getMessage());
			}
		}
	}
}
