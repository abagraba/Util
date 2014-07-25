package util.io.fileformat.obj;

import util.io.fileformat.obj.parsing.OBJValue;

public class CurveSurfaceData {

	public Type type;
	public boolean rat;
	public int udeg = -1;
	public int vdeg = -1;
	public int[] umat;
	public int[] vmat;
	public int ustep = -1;
	public int vstep = -1;

	public CurveSurfaceData(OBJValue keyword, boolean rat) {
		type = Type.getType(keyword.s);
		this.rat = rat;
	}

	public void setDegree(OBJValue u) {
		if (udeg != -1)
			warn("Overwriting curve degree values.");
		udeg = u.i;
		u.freeValue();
	}

	public void setDegree(OBJValue u, OBJValue v) {
		if (udeg != -1 || vdeg != -1)
			warn("Overwriting surface degree values.");
		udeg = u.i;
		vdeg = v.i;
		if (type == Type.CARDINAL)
			vdeg = 3;
		u.freeValue();
		v.freeValue();
	}

	public void setUMatrix(OBJValue u) {
		if (udeg == -1) {
			warn("Degree not specified when defining basis matrix. Ignoring matrix.");
			return;
		}
		int size = (udeg + 1) * (udeg + 1);
		int[] t = u.ints.toArray();
		if (size != t.length) {
			warn("Matrix size invalid. Expected size [" + size + "]. Got size [" + t.length + "]");
			return;
		}
		umat = t;
		u.freeValue();
	}
	
	public void setVMatrix(OBJValue v) {
		if (udeg == -1) {
			warn("Degree not specified when defining basis matrix. Ignoring matrix.");
			return;
		}
		int size = (udeg + 1) * (udeg + 1);
		int[] t = v.ints.toArray();
		if (size != t.length) {
			warn("Matrix size invalid. Expected size [" + size + "]. Got size [" + t.length + "]");
			return;
		}
		umat = t;
		v.freeValue();
	}
	
	public void setStep(OBJValue u) {
		if (ustep != -1)
			warn("Overwriting curve step values.");
		ustep = u.i;
		u.freeValue();
	}

	public void setStep(OBJValue u, OBJValue v) {
		if (ustep != -1 || vstep != -1)
			warn("Overwriting surface step values.");
		ustep = u.i;
		vstep = v.i;
		u.freeValue();
		v.freeValue();
	}

	private void warn(String string) {
		System.out.println(string);
	}

	private static enum Type {
		BEZIER("bezier"), BSPLINE("bspline"), BMAT("bmatrix"), CARDINAL("cardinal"), TAYLOR("taylor");

		private String keyword;

		private Type(String keyword) {
			this.keyword = keyword;
		}

		public static Type getType(String keyword) {
			for (Type type : values())
				if (type.keyword.equals(keyword))
					return type;
			return null;
		}
	}

}
