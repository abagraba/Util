package util;

/**
 * Left aligned FloatArray. Appends are very fast while prepends are extremely slow.
 */
public class LFloatArray extends FloatArray {

	public LFloatArray() {}

	public LFloatArray(float... data) {
		super(data);
	}

	@Override
	public void append(float f) {
		growTo(size + 1);
		data[size++] = f;
	}

	@Override
	public void prepend(float f) {
		float[] newData = newArray(size + 1);
		newData[0] = f;
		System.arraycopy(data, 0, newData, 1, size++);
		data = newData;
	}

	@Override
	public void append(float... floats) {
		growTo(size + floats.length);
		System.arraycopy(floats, 0, data, size, floats.length);
		size += floats.length;
	}

	@Override
	public void prepend(float... floats) {
		float[] newData = newArray(size + floats.length);
		System.arraycopy(floats, 0, newData, 0, floats.length);
		System.arraycopy(data, 0, newData, floats.length, size);
		size += floats.length;
		data = newData;
	}

	@Override
	public void append(FloatArray array) {
		growTo(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), data, size, array.size);
		size += array.size;
	}

	@Override
	public void prepend(FloatArray array) {
		float[] newData = newArray(size + array.size);
		System.arraycopy(array.data, array.getDataStart(), newData, 0, array.size);
		System.arraycopy(data, 0, newData, array.size, size);
		size += array.size;
		data = newData;
	}

	@Override
	public void append(FloatArray... arrays) {
		int totalSize = size;
		for (FloatArray array : arrays)
			totalSize += array.size;
		growTo(totalSize);
		for (FloatArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), data, size, array.size);
			size += array.size;
		}
	}

	@Override
	public void prepend(FloatArray... arrays) {
		int totalSize = size;
		for (FloatArray array : arrays)
			totalSize += array.size;
		float[] newData = newArray(totalSize);
		int i = 0;
		for (FloatArray array : arrays) {
			System.arraycopy(array.data, array.getDataStart(), newData, i, array.size);
			i += array.size;
		}
		System.arraycopy(data, 0, newData, i, size);
		size += i;
		size = totalSize;
		data = newData;
	}

	@Override
	public float removeFirst() {
		float i = data[0];
		float[] newData = newArray(size);
		System.arraycopy(data, 1, newData, 0, --size);
		data = newData;
		return i;
	}

	@Override
	public float removeLast() {
		return data[size-- - 1];
	}

	@Override
	protected void growTo(int min) {
		if (shouldGrow(min)) {
			float[] newData = newArray(min);
			System.arraycopy(data, 0, newData, 0, size);
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
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}

	@Override
	protected int getDataStart() {
		return 0;
	}

}
