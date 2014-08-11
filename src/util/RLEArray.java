package util;

import java.util.Arrays;
import java.util.NoSuchElementException;



public class RLEArray {

	protected static final int	DEFAULT_CAPACITY	= 8;
	protected int[]				data;
	private int					encodings			= 0;
	private int					size;

	public RLEArray() {
		data = new int[DEFAULT_CAPACITY];
	}

	private int encodingValue(int encoding) {
		return data[encoding * 2];
	}

	private int encodingCount(int encoding) {
		return data[encoding * 2 + 1];
	}

	private void incrementCount(int encoding) {
		data[encoding * 2 + 1]++;
	}

	private void decrementCount(int encoding) {
		data[encoding * 2 + 1]--;
	}

	public void append(int i) {
		if (encodings > 0 && i == encodingValue(encodings - 1))
			incrementCount(encodings - 1);
		else {
			growTo(encodings * 2 + 2);
			data[encodings * 2] = i;
			data[encodings++ * 2 + 1] = 1;
		}
		size++;
	}

	public void prepend(int i) {
		if (encodings > 0 && i == encodingValue(0))
			incrementCount(0);
		else {
			int[] newData = newArray(encodings * 2 + 2);
			newData[0] = i;
			newData[1] = 1;
			System.arraycopy(data, 0, newData, 2, encodings++ * 2);
			data = newData;
		}
		size++;
	}

	public void append(int... ints) {
		for (int i : ints)
			append(i);
	}

	public void prepend(int... ints) {
		for (int i : ints)
			prepend(i);
	}

	public int removeFirst() {
		if (encodings == 0)
			throw new NoSuchElementException();
		int i = encodingValue(0);
		decrementCount(0);
		if (encodingCount(0) == 0) {
			int[] newData = newArray(encodings * 2 - 2);
			System.arraycopy(data, 2, newData, 0, --encodings * 2);
			data = newData;
		}
		size--;
		return i;
	}

	public int removeLast() {
		if (encodings == 0)
			throw new NoSuchElementException();
		int i = encodingValue(encodings - 1);
		decrementCount(encodings - 1);
		if (encodingCount(encodings - 1) == 0)
			encodings--;
		size--;
		return i;
	}

	public int size() {
		return size;
	}

	public int[] toArray() {
		int[] array = new int[size];
		int i = 0;
		for (int e = 0; e < encodings; e++) {
			int value = data[e * 2];
			int count = data[e * 2 + 1];
			for (int c = 0; c < count; c++)
				array[i++] = value;
		}
		return array;
	}

	protected void growTo(int min) {
		data = Arrays.copyOf(data, growToSize(min));
	}

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
