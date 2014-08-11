package util;

/**
 * Left aligned FloatArray. Appends are very fast while prepends are extremely slow.
 */
public class RFloatArray extends FloatArray {

	public RFloatArray() {}

	public RFloatArray(float... data) {
		super(data);
	}

	@Override
	public void append(float f) {
		float[] newData = newArray(size + 1);
		newData[newData.length - 1] = f;
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - 1, size++);
		data = newData;
	}

	@Override
	public void prepend(float f) {
		growTo(size + 1);
		data[getDataStart() - 1] = f;
		size++;
	}

	@Override
	public void append(float... floats) {
		float[] newData = newArray(size + floats.length);
		System.arraycopy(floats, 0, newData, newData.length - floats.length, floats.length);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - floats.length, size);
		data = newData;
		size += floats.length;
	}

	@Override
	public void prepend(float... floats) {
		growTo(size + floats.length);
		System.arraycopy(floats, 0, data, getDataStart() - floats.length, floats.length);
		size += floats.length;
	}

	@Override
	public void append(FloatArray array) {
		float[] newData = newArray(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), newData, newData.length - array.size, array.size);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) - array.size, size);
		size += array.size;
		data = newData;
	}

	@Override
	public void prepend(FloatArray array) {
		growTo(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), data, getDataStart() - array.size, array.size);
		size += array.size;
	}

	@Override
	public void append(FloatArray... arrays) {
		int totalSize = size;
		for (FloatArray array : arrays)
			totalSize += array.size;
		float[] newData = newArray(totalSize);
		int i = newData.length - totalSize;
		System.arraycopy(data, getDataStart(), newData, i, size);
		i += size;
		for (FloatArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), newData, i, array.size);
			i += array.size;
		}
		size = totalSize;
		data = newData;
	}

	@Override
	public void prepend(FloatArray... arrays) {
		int totalSize = size;
		for (FloatArray array : arrays)
			totalSize += array.size;
		growTo(totalSize);
		int i = data.length - totalSize;
		for (FloatArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), data, i, array.size);
			i += array.size;
		}
		size = totalSize;
	}

	@Override
	public float removeFirst() {
		return data[data.length - size--];
	}

	@Override
	public float removeLast() {
		float i = data[data.length - 1];
		float[] newData = newArray(size);
		System.arraycopy(data, getDataStart(), newData, getDataStart(newData) + 1, --size);
		data = newData;
		return i;
	}

	@Override
	protected void growTo(int min) {
		if (shouldGrow(min)) {
			float[] newData = newArray(min);
			System.arraycopy(data, getDataStart(), newData, getDataStart(newData), size);
			data = newData;
		}
	}

	@Override
	public float[] toArray() {
		trim();
		return data;
	}

	@Override
	public void trim() {
		if (data.length != size) {
			float[] newData = new float[size];
			System.arraycopy(data, getDataStart(), newData, 0, size);
			data = newData;
		}
	}

	@Override
	protected int getDataStart() {
		return data.length - size;
	}

	protected int getDataStart(float[] newData) {
		return newData.length - size;
	}

}
