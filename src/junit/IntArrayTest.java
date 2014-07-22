package junit;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.IntArray;
import util.LIntArray;
import util.RIntArray;

public class IntArrayTest {

	private int[] leftArray = new int[] { 0, 1, 2, 3, 4 };
	private int[] rightArray = new int[] { 5, 6, 7, 8, 9 };

	private int[] aleftArray = new int[] { 0 };
	private int[] bleftArray = new int[] { 1, 2, 3, 4 };
	private int[] abrightArray = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private int[] arightArray = new int[] { 5, 6, 7, 8 };
	private int[] brightArray = new int[] { 9 };
	private int[] ableftArray = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private int[] sourceArray = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLPrepend() {
		IntArray array;
		/**/
		array = new LIntArray(abrightArray);
		array.prepend(aleftArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(rightArray);
		array.prepend(leftArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(rightArray);
		array.prepend(new LIntArray(leftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(rightArray);
		array.prepend(new RIntArray(leftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(rightArray);
		array.prepend(new LIntArray(aleftArray), new RIntArray(bleftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
	}

	@Test
	public void testRPrepend() {
		IntArray array;
		/**/
		array = new RIntArray(abrightArray);
		array.prepend(aleftArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(rightArray);
		array.prepend(leftArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(rightArray);
		array.prepend(new LIntArray(leftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(rightArray);
		array.prepend(new RIntArray(leftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(rightArray);
		array.prepend(new LIntArray(aleftArray), new RIntArray(bleftArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
	}

	@Test
	public void testLAppend() {
		IntArray array;
		/**/
		array = new LIntArray(ableftArray);
		array.append(brightArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(leftArray);
		array.append(rightArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(leftArray);
		array.append(new LIntArray(rightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(leftArray);
		array.append(new RIntArray(rightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new LIntArray(leftArray);
		array.append(new LIntArray(arightArray), new RIntArray(brightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
	}

	@Test
	public void testRAppend() {
		IntArray array;
		/**/
		array = new RIntArray(ableftArray);
		array.append(brightArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(leftArray);
		array.append(rightArray);
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(leftArray);
		array.append(new LIntArray(rightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(leftArray);
		array.append(new RIntArray(rightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
		/**/
		array = new RIntArray(leftArray);
		array.append(new LIntArray(arightArray), new RIntArray(brightArray));
		assertTrue(array.size() == 10);
		assertTrue(Arrays.equals(array.toArray(), sourceArray));
	}
	
}
