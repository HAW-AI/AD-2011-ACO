package adp2.implementations;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static adp2.implementations.Values.*;
import adp2.interfaces.*;

public class AntImplTest {
	Graph graph2x2;
	
	@Before
	public void setUp() throws Exception {
		graph2x2=graphFromList(0.,4.,4.,0.);
	}

	@Test
	public void testValueOf() {
		//Test ant(int , double ,graph)
		assertTrue(!(ant(1,0.1,graph2x2) instanceof NaA));
		
		assertTrue((ant(1,0.1,null) instanceof NaA));
		
		assertTrue((ant(1,0.1,NaG()) instanceof NaA));
		assertTrue((ant(1,-0.1,graph2x2) instanceof NaA));
		assertTrue((ant(1,1.1,graph2x2) instanceof NaA));
		assertTrue((ant(3,0.1,graph2x2) instanceof NaA));
		assertTrue((ant(-1,0.1,graph2x2) instanceof NaA));
		
		//Test ant( double ,graph)
		assertTrue(!(ant(0.1,graph2x2) instanceof NaA));
		
		assertTrue((ant(0.1,null) instanceof NaA));
		
		assertTrue((ant(0.1,NaG()) instanceof NaA));
		assertTrue((ant(-0.1,graph2x2) instanceof NaA));
		assertTrue((ant(1.1,graph2x2) instanceof NaA));
	}

	@Test
	public void testTraveledPath() {
		Ant ant=ant(1,0.2,graph2x2);
		assertEquals(1,ant.traveledPath().waypoints().size());
		ant.step();
		ant.step();
		ant.step();
		assertEquals(2,ant.traveledPath().waypoints().size());
		ant.step();
		ant.step();
		ant.step();
		assertEquals(3,ant.traveledPath().waypoints().size());
	}


	@Test
	public void testPrePosition() {
		Ant ant=ant(1,0.2,graph2x2);
		assertEquals(1, ant.prevPosition());
		ant.step();
		assertEquals(1, ant.prevPosition());
		ant.step();
		ant.step();
		ant.step();
		ant.step();
		ant.step();
		assertEquals(2, ant.prevPosition());
	}

}
