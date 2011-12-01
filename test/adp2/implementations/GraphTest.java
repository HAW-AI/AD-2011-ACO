package adp2.implementations;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static adp2.implementations.Values.*;
import adp2.interfaces.*;

public class GraphTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testNaG() {
		assertTrue((graphFromList(1.0, 2.0, 3.0) instanceof NaG));
		assertTrue((graphFromList(1.0, 2.0, 3.0, 4.0, 5.0) instanceof NaG));
		assertFalse((graphFromList(1.0, 2.0, 3.0, 4.0) instanceof NaG));
	}
}