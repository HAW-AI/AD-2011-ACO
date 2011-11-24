package adp2.app;

import adp2.parser.Tsp;

public class Controller {
	/**
	 * @param args
	 */
	public static void main(String[] args) {;
		Tsp t = Tsp.open("samples/gr21.tsp");
		System.out.println(t.matrix());
	}
}
