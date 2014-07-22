package util;

import java.util.Arrays;

/**
 * Left aligned IntArray. Appends are very fast while prepends are extremely slow.
 */
public class LIntArray extends IntArray {

	public LIntArray(int... data) {
		super(data);
	}

	@Override
	public void append(int i) {
		growTo(size + 1);
		data[size++] = i;
	}

	@Override
	public void prepend(int i) {
		int[] newData = newArray(size + 1);
		newData[0] = i;
		System.arraycopy(data, 0, newData, 1, size++);
		data = newData;
	}

	@Override
	public void append(int... ints) {
		growTo(size + ints.length);
		System.arraycopy(ints, 0, data, size, ints.length);
		size += ints.length;
	}

	@Override
	public void prepend(int... ints) {
		int[] newData = newArray(size + ints.length);
		System.arraycopy(ints, 0, newData, 0, ints.length);
		System.arraycopy(data, 0, newData, ints.length, size);
		size += ints.length;
		data = newData;
	}

	@Override
	public void append(IntArray array) {
		growTo(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), data, size, array.size);
		size += array.size;
	}

	@Override
	public void prepend(IntArray array) {
		int[] newData = newArray(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), newData, 0, array.size);
		System.arraycopy(data, 0, newData, array.size, size);
		size += array.size;
		data = newData;
	}

	@Override
	public void append(IntArray... arrays) {
		int totalSize = size;
		for (IntArray array : arrays)
			totalSize += array.size;
		growTo(totalSize);
		for (IntArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), data, size, array.size);
			size += array.size;
		}
	}

	@Override
	public void prepend(IntArray... arrays) {
		int totalSize = size;
		for (IntArray array : arrays)
			totalSize += array.size;
		int[] newData = newArray(totalSize);
		int i = 0;
		for (IntArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), newData, i, array.size);
			i += array.size;
		}
		System.arraycopy(data, 0, newData, i, size);
		size += i;
		size = totalSize;
		data = newData;
	}

	@Override
	public int removeFirst() {
		int i = data[0];
		int[] newData = newArray(size);
		System.arraycopy(data, 1, newData, 0, --size);
		data = newData;
		return i;
	}

	@Override
	public int removeLast() {
		return data[size-- - 1];
	}

	@Override
	protected void growTo(int min) {
		if (shouldGrow(min)) {
			int[] newData = newArray(min);
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}

	@Override
	public int[] toArray() {
		trim();
		return data;
	}

	@Override
	public void trim() {
		if (data.length != size) {
			int[] newData = new int[size];
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}

	@Override
	protected int getDataStart() {
		return 0;
	}

}