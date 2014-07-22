package util;

import java.awt.Point;

/**
 * Object that stores an array of ints in a contiguous region of an int[]. Meant for quick and efficient data aggregation. Once this is constructed from an
 * array, the array should be considered invalid and never used again. Once toArray is called to extract the data, this object should not be used again as the
 * actions likely will not have effect on the array.
 */
public abstract class IntArray {

	protected static final int DEFAULT_CAPACITY = 8;

	protected int[] data;
	protected int size;

	public IntArray() {
		this.data = new int[DEFAULT_CAPACITY];
	}

	/**
	 * Creates a new IntArray backed by the provided array. Modifying the array may render this IntArray invalid and vice-versa. Thus the provided array should
	 * no longer be modified after being provided to this constructor.
	 */
	public IntArray(int... data) {
		this.data = data;
		size = data.length;
	}

	public abstract void append(int i);

	public abstract void prepend(int i);

	public abstract void append(int... ints);

	public abstract void prepend(int... ints);

	/**
	 * Gets the index of the first piece of data. The data will be in the range from [getDataStart(), getDataStart() + size - 1].
	 */
	protected abstract int getDataStart();

	/**
	 * Appends the IntArray provided to the end of this IntArray.
	 */
	public abstract void append(IntArray array);

	/**
	 * Appends the IntArray provided to the beginning of this IntArray.
	 */
	public abstract void prepend(IntArray array);

	/**
	 * Appends the IntArrays provided to the end of this IntArray.
	 */
	public abstract void append(IntArray... arrays);

	/**
	 * Appends the IntArrays provided to the beginning of this IntArray.
	 */
	public abstract void prepend(IntArray... arrays);

	public abstract int removeFirst();

	public abstract int removeLast();

	/**
	 * Returns the number of elements held in this IntArray.
	 */
	public int size() {
		return size;
	}

	/**
	 * Trims the backing array to tightly fit the data.
	 */
	public abstract void trim();

	/**
	 * Trim and returns the backing array. Modifying the array may render this IntArray invalid and vice-versa.
	 */
	public abstract int[] toArray();

	/**
	 * Grows the backing array so that it can hold at least min elements. Does nothing if the backing array is already large enough.
	 */
	protected abstract void growTo(int min);

	/**
	 * Returns a new int[] capable of holding at least min elements. Returns a new array of length size if min < size.
	 */
	protected int[] newArray(int min) {
		if (shouldGrow(min))
			return new int[growToSize(min)];
		return new int[data.length];
	}

	protected boolean shouldGrow(int min) {
		return data.length < min;
	}

	private int growSize(int i) {
		return 1 + i + (i >> 1);
	}

	private int growToSize(int min) {
		int x = data.length;
		while (x < min)
			x = growSize(x);
		return x;
	}

}
