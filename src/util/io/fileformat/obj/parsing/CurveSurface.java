package util.io.fileformat.obj.parsing;

public class CurveSurface {

	private boolean	rat;
	private Type	type;
	private int		degu;
	private int		degv;
	private float	stepu;
	private float	stepv;
	private int[]	uMatrix;
	private int[]	vMatrix;

	public CurveSurface(OBJValue name) {
		type = Type.getType(name.s);
	}

	public void setRational(boolean rat) {
		this.rat = rat;
	}

	public void setDegree(OBJValue u, OBJValue v) {
		if (degu != 0 || degv != 0)
			System.out.println("Warning: Overwriting previous degree statement.");
		degu = u.i;
		if (v != null)
			degv = v.i;
	}

	public void setStep(OBJValue u, OBJValue v) {
		if (stepu != 0 || stepv != 0)
			System.out.println("Warning: Overwriting previous step statement.");
		stepu = u.i;
		if (v != null)
			stepv = v.i;
	}

	public void setUMatrix(OBJValue matrix) {
		if (type != Type.BMatrix) {
			System.out.println("Warning: Defining matrix for non-matrix curve.");
			return;
		}
		if (degu == 0) {
			System.out.println("Error: Degree must be defined before matrix.");
			return;
		}
		uMatrix = matrix.ints.toArray();
		if (uMatrix.length != (degu + 1) * (degu + 1)) {
			System.out.println("Error: Incorrect dimensions of u matrix. (" + uMatrix.length + ")");
			uMatrix = null;
		}
	}

	public void setVMatrix(OBJValue matrix) {
		if (type != Type.BMatrix) {
			System.out.println("Warning: Defining matrix for non-matrix curve.");
			return;
		}
		if (degv == 0) {
			System.out.println("Error: Degree must be defined before matrix.");
			return;
		}
		vMatrix = matrix.ints.toArray();
		if (vMatrix.length != (degv + 1) * (degv + 1)) {
			System.out.println("Error: Incorrect dimensions of v matrix. (" + vMatrix.length + ")");
			uMatrix = null;
		}
	}

	private static enum Type {
		BMatrix("bmatrix"), Bezier("bezier"), BSpline("bspline"), Cardinal("cardinal"), Taylor("taylor");

		private String	name;

		private Type(String name) {
			this.name = name;
		}

		public static Type getType(String name) {
			for (Type type : values())
				if (type.name.equals(name))
					return type;
			System.out.println("Invalid Curve/Surface type: [" + name + "]");
			return null;
		}
	}

}
