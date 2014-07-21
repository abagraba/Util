package util.io.fileformat.obj.parsing;

import java.util.Arrays;



public class IntArray {

	private static final int	INITIAL_CAPACITY	= 8;

	private int[]				data				= new int[INITIAL_CAPACITY];
	private int					size;

	public IntArray(int[] data) {
		this.data = Arrays.copyOf(data, data.length);
		size = data.length;
	}

	public void add(int i) {
		if (size - data.length >= 0)
			grow();
		data[size++] = i;
	}

	public int remove() {
		if (size == 0)
			throw new ArrayIndexOutOfBoundsException("Attempted removal on an empty IntArray.");
		return data[--size];
	}

	public int size() {
		return size;
	}

	public int[] toArray() {
		return Arrays.copyOf(data, size);
	}

	private void grow() {
		data = Arrays.copyOf(data, data.length + 1 + data.length >> 1);
	}

	public void trim() {
		data = Arrays.copyOf(data, size);
	}

}
