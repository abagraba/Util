package util;

import java.util.Arrays;

public class IntArray {

	private static final int DEFAULT_CAPACITY = 8;

	private int[] data = new int[DEFAULT_CAPACITY];
	private int size;

	public IntArray(int... data) {
		this.data = data;
		size = data.length;
	}

	public void add(int i) {
		if (size + 1 >= data.length)
			grow(1);
		data[size++] = i;
	}

	public void add(int... ints) {
		if (size + ints.length >= data.length)
			grow(ints.length);
		for (int i = 0; i < ints.length; i++)
			data[size + i] = ints[i];
		size += ints.length;
	}

	public void append(IntArray ints) {
		ensureSize(size + ints.size);
		for (int i = 0; i < ints.size; i++)
			data[size + i] = ints.data[i];
		size += ints.size;
	}

	public void append(IntArray... arrays) {
		int size = this.size;
		for (IntArray array : arrays)
			size += array.size;
		ensureSize(size);
		for (IntArray array : arrays) {
			for (int i = 0; i < array.size; i++)
				data[this.size + i] = array.data[i];
			this.size += array.size;
		}
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
		if (data.length == size)
			return data;
		return Arrays.copyOf(data, size);
	}

	private void grow(int i) {
		data = Arrays.copyOf(data, data.length + i + (data.length >> 1));
	}

	public void ensureSize(int min) {
		if (data.length < min)
			data = Arrays.copyOf(data, min);
	}

	public void trim() {
		if (data.length != size)
			data = Arrays.copyOf(data, size);
	}

}
