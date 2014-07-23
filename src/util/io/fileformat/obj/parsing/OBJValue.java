package util.io.fileformat.obj.parsing;

import java.util.LinkedList;

import util.IntArray;
import util.LIntArray;
import util.RIntArray;

public class OBJValue {

	private final static LinkedList<OBJValue> values = new LinkedList<OBJValue>();

	public int i;
	public float f;

	public String s;
	public IntArray ints;

	public Object obj;

	protected OBJValue() {
	}

	public void freeValue() {
		s = null;
		ints = null;
		obj = null;
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

	public static OBJValue newValue(int[] ints, boolean left) {
		OBJValue val = getValue();
		if (left)
			val.ints = new LIntArray(ints);
		else
			val.ints = new RIntArray(ints);
		return val;
	}

	public static OBJValue newValue(Object obj) {
		OBJValue val = getValue();
		val.obj = obj;
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
