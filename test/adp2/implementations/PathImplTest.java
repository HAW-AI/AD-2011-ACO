package adp2.implementations;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class PathImplTest {

	@Test
	public void testPreconditions() {
		assertTrue(Values.path(null, 0) instanceof NaP);

		List<Integer> list = new ArrayList<Integer>();
		assertTrue(Values.path(list, 0) instanceof PathImpl);

		assertTrue(Values.path(list, -1) instanceof NaP);

		list.add(null);
		assertTrue(Values.path(list, 0) instanceof NaP);
	}

	@Test
	public void testDistance() {
		List<Integer> list = new ArrayList<Integer>();
		assertEquals(0, Values.path(list, 0).distance());
		assertEquals(42, Values.path(list, 42).distance());
	}

	@Test
	public void testWaypoints() {
		List<Integer> list = new ArrayList<Integer>();
		assertEquals(list, Values.path(list, 0).waypoints());

		list.add(0);
		list.add(42);
		assertEquals(list, Values.path(list, 0).waypoints());
	}

	@Test
	public void testEquals() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		assertTrue(Values.path(list1, 0).equals(Values.path(list2, 0)));
		assertFalse(Values.path(list1, 0).equals(Values.path(list2, 1)));

		list1.add(42);
		assertFalse(Values.path(list1, 0).equals(Values.path(list2, 0)));

		list2.add(42);
		assertTrue(Values.path(list1, 0).equals(Values.path(list2, 0)));
	}

	@Test
	public void testHashCode() {
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		assertTrue(Values.path(list1, 0).hashCode()==Values.path(list2, 0).hashCode());
		assertFalse(Values.path(list1, 0).hashCode()==Values.path(list2, 1).hashCode());

		list1.add(42);
		assertFalse(Values.path(list1, 0).hashCode()==Values.path(list2, 0).hashCode());

		list2.add(42);
		assertTrue(Values.path(list1, 0).hashCode()==Values.path(list2, 0).hashCode());

	}
}
