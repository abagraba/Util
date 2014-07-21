package util.io.fileformat.obj.parsing;

import java.util.LinkedList;

public class OBJValue {

	private final static LinkedList<OBJValue> values = new LinkedList<OBJValue>();

	public int i;
	public float f;

	public String s;
	public IntArray ints;

	protected OBJValue() {
	}

	public void freeValue() {
		ints = null;
		values.add(this);
	}

	public static OBJValue newValue(int i) {
		OBJValue val = getValue();
		val.i = i;
		return val;
	}

	public static OBJValue newValue(String s) {
		OBJValue val = getValue();
		val.s = s;
		return val;
	}
	
	public static OBJValue newValue(float f) {
		OBJValue val = getValue();
		val.f = f;
		return val;
	}

	public static OBJValue newValue(int[] ints) {
		OBJValue val = getValue();
		val.ints = new IntArray(ints);
		return val;
	}

	private static OBJValue getValue() {
		if (values.isEmpty())
			return new OBJValue();
		return values.removeFirst();
	}

	public static void clean() {
		values.clear();
	}

}
