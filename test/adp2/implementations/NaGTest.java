package adp2.implementations;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static adp2.implementations.Values.*;
import adp2.interfaces.*;

public class NaGTest {
	Graph myNaG;
	
	@Before
	public void setUp() throws Exception {
		myNaG = Values.NaG();
	}

	@Test
	public void testCreator() {
		assertTrue((graphFromList(1.0, 2.0, 3.0) instanceof NaG));
		assertTrue((graphFromList(1.0, 2.0, 3.0, 4.0, 5.0) instanceof NaG));
		assertFalse((graphFromList(1.0, 2.0, 3.0, 4.0) instanceof NaG));
	}
	
	@Test
	public void testMethodes() {
		assertEquals(-1,myNaG.intensity(1,1),0.001);
		assertEquals(-1,myNaG.intensity(1,1),0.001);
	}
}