package adp2.parser;

import adp2.app.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import adp2.implementations.Values;
import adp2.interfaces.Matrix;
import java.util.logging.*;

public class TspFile {
	private static final Logger logger = Main.logger;
	
    public static TspFile open(String filename) {
		logger.info("Lade TspFile " + filename);
        return new TspFile(filename);
    }
    private String[] content;
    private Integer dimension;
    private Double[] values;

    public TspFile(String filename) {
        dimension(head(content(filename)));
        lowerDiagonalRow(body(content(filename)));
    }

	public Matrix<Double> matrix() {
		return Values.immutableMatrix(dimension, Arrays.asList(values));
		
	}

    private Double[] lowerDiagonalRow(String[] body) {
        if (this.values == null) {
            Double[] v = new Double[dimension * dimension];
            String c = "";
            Double t = 0.0;
            int x = 0, y = 0;
            for (String line : body) {
                for (String e : line.replaceAll(" +", " ").trim().split(" ")) {
                    c = String.valueOf(e);
                    if (c.equals("-")) {
                        t = Double.POSITIVE_INFINITY;
                    } else {
                        t = Double.parseDouble(c);
                    }
                    v[x + (y * dimension)] = t;
                    v[y + (x * dimension)] = t;
                    x++;
                    if (c.equals("0")) {
                        y++;
                        x = 0;
                    }
                    if (x >= this.dimension) {
                        x = 0;
                    }
                }
            }
            this.values = v;
        }
        return this.values;
    }

    private String[] body(String[] lines) {
        List<String> result = new ArrayList<String>();
        boolean body = false;
        for (String l : lines) {
            if (l.contains("EDGE_WEIGHT_SECTION")) {
                body = true;
            } else if (l.contains("EOF")) {
                body = false;
            } else if (body) {
                result.add(l);
            }
        }
        return result.toArray(new String[0]);
    }

    private int dimension(String[] head) {
        if (this.dimension == null) {
            Integer result = null;
            for (String l : head) {
                if (l.contains("DIMENSION")) {
                    result = Integer.parseInt(l.substring(11));
                }
            }
            if (result == null) {
                throw new IllegalArgumentException("File misses DIMENSION");
            }
            this.dimension = result;
        }
        return this.dimension;
    }

    private String[] head(String[] lines) {
        List<String> result = new ArrayList<String>();
        for (String l : lines) {
            result.add(l);
        }
        return result.toArray(new String[0]);
    }

    private String[] content(String filename) {
        if (this.content == null) {
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(new File(filename)));
            } catch (FileNotFoundException e) {
                // XXX: maybe we should leave the exception as it is
				logger.severe(e.getMessage());
                throw new IllegalArgumentException(filename + " not found");
            }
            List<String> buffer = new ArrayList<String>();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    buffer.add(line);
                }
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
            this.content = buffer.toArray(new String[0]);
        }
        return this.content;
    }
}
