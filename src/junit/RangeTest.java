package junit;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.MultiRange;



public class RangeTest {

	private int[]	base			= new int[] { 5, 9, 20, 22 };

	private int[]	outleft			= new int[] { 0, 1, 5, 9, 20, 22 };
	private int[]	outright		= new int[] { 5, 9, 11, 12, 20, 22 };

	private int[]	crossleftleft	= new int[] { 4, 9, 20, 22 };
	private int[]	crossleftright	= new int[] { 5, 11, 20, 22 };
	private int[]	crossrightleft	= new int[] { 5, 9, 11, 22 };
	private int[]	crossrightright	= new int[] { 5, 9, 20, 25 };

	private int[]	arightArray		= new int[] { 5, 6, 7, 8 };
	private int[]	brightArray		= new int[] { 9 };
	private int[]	ableftArray		= new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	private int[]	sourceArray		= new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	private MultiRange range() {
		MultiRange range = new MultiRange();
		range.addRange(5, 9);
		range.addRange(20, 22);
		assertTrue(Arrays.equals(range.allRanges(), base));
		return range;
	}

	@Test
	public void addToOutside() {
		MultiRange range;
		/**/
		range = range();
		range.addRange(0, 1);
		assertTrue(Arrays.equals(range.allRanges(), outleft));
		/**/
		range = range();
		range.addRange(11, 12);
		assertTrue(Arrays.equals(range.allRanges(), outright));
	}

	@Test
	public void addToInside() {
		MultiRange range;
		/**/
		range = range();
		range.addRange(6, 7);
		assertTrue(Arrays.equals(range.allRanges(), base));
		/**/
		range = range();
		range.addRange(8, 9);
		assertTrue(Arrays.equals(range.allRanges(), base));
	}

	@Test
	public void addToBetween() {
		MultiRange range;
		/**/
		range = range();
		range.addRange(6, 7);
		assertTrue(Arrays.equals(range.allRanges(), base));
		/**/
		range = range();
		range.addRange(20, 22);
		assertTrue(Arrays.equals(range.allRanges(), base));
	}

	@Test
	public void addToCross() {
		MultiRange range;
		/**/
		range = range();
		range.addRange(4, 5);
		assertTrue(Arrays.equals(range.allRanges(), crossleftleft));
		/**/
		range = range();
		range.addRange(5, 11);
		assertTrue(Arrays.equals(range.allRanges(), crossleftright));
		/**/
		range = range();
		range.addRange(11, 21);
		assertTrue(Arrays.equals(range.allRanges(), crossrightleft));
		/**/
		range = range();
		range.addRange(22, 25);
		assertTrue(Arrays.equals(range.allRanges(), crossrightright));
	}
}
