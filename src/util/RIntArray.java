package util;

import java.util.Arrays;

/**
 * Left aligned IntArray. Appends are very fast while prepends are extremely slow.
 */
public class RIntArray extends IntArray {

	public RIntArray(int... data) {
		super(data);
	}

	@Override
	public void append(int i) {
		int[] newData = newArray(size + 1);
		newData[newData.length - 1] = i;
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - 1, size++);
		data = newData;
	}

	@Override
	public void prepend(int i) {
		growTo(size + 1);
		data[getDataStart() - 1] = i;
		size++;
	}

	@Override
	public void append(int... ints) {
		int[] newData = newArray(size + ints.length);
		System.arraycopy(ints, 0, newData, newData.length - ints.length, ints.length);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - ints.length, size);
		data = newData;
		size += ints.length;
	}

	@Override
	public void prepend(int... ints) {
		growTo(size + ints.length);
		System.arraycopy(ints, 0, data, getDataStart() - ints.length, ints.length);
		size += ints.length;
	}

	@Override
	public void append(IntArray array) {
		int[] newData = newArray(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), newData, newData.length - array.size, array.size);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - array.size, size);
		size += array.size;
		data = newData;
	}

	@Override
	public void prepend(IntArray array) {
		growTo(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), data, getDataStart() - array.size, array.size);
		size += array.size;
	}

	@Override
	public void append(IntArray... arrays) {
		int totalSize = size;
		for (IntArray array : arrays)
			totalSize += array.size;
		int[] newData = newArray(totalSize);
		int i = newData.length - totalSize;
		System.arraycopy(data, getDataStart(), newData, i, size);
		i += size;
		for (IntArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), newData, i, array.size);
			i += array.size;
		}
		size = totalSize;
		data = newData;
	}

	@Override
	public void prepend(IntArray... arrays) {
		int totalSize = size;
		for (IntArray array : arrays)
			totalSize += array.size;
		growTo(totalSize);
		int i = data.length - totalSize;
		for (IntArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), data, i, array.size);
			i += array.size;
		}
		size = totalSize;
	}

	@Override
	public int removeFirst() {
		return data[data.length - size--];
	}

	@Override
	public int removeLast() {
		int i = data[data.length - 1];
		int[] newData = newArray(size);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) + 1, --size);
		data = newData;
		return i;
	}

	@Override
	protected void growTo(int min) {
		if (shouldGrow(min)) {
			int[] newData = newArray(min);
			System.arraycopy(data, getDataStart(), newData, getDataStart(newData), size);
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
		if (data.length != size){
			int[] newData = new int[size];
			System.arraycopy(data, getDataStart(), newData, 0, size);
			data = newData;
		}
	}

	@Override
	protected int getDataStart() {
		return data.length - size;
	}

	protected int getDataStart(int[] newData) {
		return newData.length - size;
	}

}