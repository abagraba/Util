package util.io.fileformat.obj.parsing;

public class CurveSurfaceData {

	public Type				type;
	public boolean			rat;
	public int				udeg		= -1;
	public int				vdeg		= -1;
	public int[]			umat;
	public int[]			vmat;
	public int				ustep		= -1;
	public int				vstep		= -1;

	public static final int	CSELEMENT	= ObjLogging.CSELEMENT;

	public CurveSurfaceData(OBJValue keyword, boolean rat) {
		type = Type.getType(keyword.s);
		this.rat = rat;
		ObjLogging.logObject(CSELEMENT, "Curve/Surface Type", type.toString());
	}

	public void setDegree(OBJValue u) {
		if (udeg != -1)
			ObjLogging.logln(CSELEMENT, "Overwriting curve degree values.");
		udeg = u.i;
		ObjLogging.logObject(CSELEMENT, "Degree");
		ObjLogging.logf(CSELEMENT, ObjLogging.int1, udeg);
		u.freeValue();
	}

	public void setDegree(OBJValue u, OBJValue v) {
		if (udeg != -1 || vdeg != -1)
			ObjLogging.logln(CSELEMENT, "Overwriting surface degree values.");
		udeg = u.i;
		vdeg = v.i;
		if (type == Type.CARDINAL)
			vdeg = 3;
		ObjLogging.logObject(CSELEMENT, "Degree");
		ObjLogging.logf(CSELEMENT, ObjLogging.int2, udeg, vdeg);
		u.freeValue();
		v.freeValue();
	}

	public void setUMatrix(OBJValue u) {
		if (udeg == -1) {
			ObjLogging.logln(CSELEMENT, "Degree not specified when defining basis matrix. Ignoring matrix.");
			return;
		}
		int size = (udeg + 1) * (udeg + 1);
		int[] t = u.ints.toArray();
		if (size != t.length) {
			ObjLogging.logln(CSELEMENT, "Matrix size invalid. Expected size [" + size + "]. Got size [" + t.length + "]");
			return;
		}
		umat = t;
		u.freeValue();
	}

	public void setVMatrix(OBJValue v) {
		if (udeg == -1) {
			ObjLogging.logln(CSELEMENT, "Degree not specified when defining basis matrix. Ignoring matrix.");
			return;
		}
		int size = (udeg + 1) * (udeg + 1);
		int[] t = v.ints.toArray();
		if (size != t.length) {
			ObjLogging.logln(CSELEMENT, "Matrix size invalid. Expected size [" + size + "]. Got size [" + t.length + "]");
			return;
		}
		umat = t;
		v.freeValue();
	}

	public void setStep(OBJValue u) {
		if (ustep != -1)
			ObjLogging.logln(CSELEMENT, "Overwriting curve step values.");
		ustep = u.i;
		ObjLogging.logObject(CSELEMENT, "Step");
		ObjLogging.logf(CSELEMENT, ObjLogging.int1, ustep);
		u.freeValue();
	}

	public void setStep(OBJValue u, OBJValue v) {
		if (ustep != -1 || vstep != -1)
			ObjLogging.logln(CSELEMENT, "Overwriting surface step values.");
		ustep = u.i;
		vstep = v.i;
		ObjLogging.logObject(CSELEMENT, "Step");
		ObjLogging.logf(CSELEMENT, ObjLogging.int2, ustep, vstep);
		u.freeValue();
		v.freeValue();
	}

	private static enum Type {
		BEZIER("bezier"), BSPLINE("bspline"), BMAT("bmatrix"), CARDINAL("cardinal"), TAYLOR("taylor");

		private String	keyword;

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
