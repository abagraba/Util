package junit;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.RLEArray;



public class RLEArrayTest {

	private int[]	sourceArray1	= new int[] { 1, 1, 1 };
	private int[]	sourceArray2	= new int[] { 2, 2, 1, 1, 1, 2, 2, 2 };
	private int[]	sourceArray3	= new int[] { 1, 1, 2, 2 };

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void test() {
		RLEArray array = new RLEArray();
		/**/
		array.append(1);
		array.append(1);
		array.append(1);
		assertTrue(array.size() == 3);
		assertTrue(Arrays.equals(array.toArray(), sourceArray1));
		/**/
		array.prepend(2);
		array.prepend(2);
		array.append(2);
		array.append(2);
		array.append(2);
		assertTrue(array.size() == 8);
		assertTrue(Arrays.equals(array.toArray(), sourceArray2));
		/**/
		assertTrue(array.removeFirst() == 2);
		assertTrue(array.removeFirst() == 2);
		assertTrue(array.removeFirst() == 1);
		assertTrue(array.removeLast() == 2);
		assertTrue(array.size() == 4);
		assertTrue(Arrays.equals(array.toArray(), sourceArray3));
	}

}
