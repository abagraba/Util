package util.io.fileformat.obj.elements;

import util.io.fileformat.obj.parsing.CSData;



public class Element {

	public final ElementType	type;

	public Element(ElementType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type.toString();
	}

	public static class Point extends Element {
		public int[]	vertex;

		public Point(int[] vertex) {
			super(ElementType.POINT);
			this.vertex = vertex;
		}

		@Override
		public String toString() {
			return super.toString() + " (" + vertex[0] + ", " + vertex[1] + ", " + vertex[2] + ", " + vertex[3] + ")";
		}
	}

	public static class Line extends Element {
		public int[]	vertices;

		public Line(int[] vertices) {
			super(ElementType.LINE);
			this.vertices = vertices;
		}

		@Override
		public String toString() {
			return super.toString() + " [" + vertices.length + "]";
		}
	}

	public static class Face extends Element {
		public int[]	vertices;

		public Face(int[] vertices) {
			super(ElementType.FACE);
			this.vertices = vertices;
		}

		@Override
		public String toString() {
			return super.toString() + " [" + vertices.length + "]";
		}
	}

	public static class Curve extends Element {

		public boolean	rat;
		public int		deg, step;
		public int[]	mat;

		public float	t0, t1;
		public int[]	vertices;

		public static Curve makeCurve(CSData csdata, float t0, float t1, int[] vertices) {
			if (!csdata.validateCurve())
				return null;
			return new Curve(csdata, t0, t1, vertices);
		}

		private Curve(CSData csdata, float t0, float t1, int[] vertices) {
			super(ElementType.CURVE);
			this.t0 = t0;
			this.t1 = t1;
			this.vertices = vertices;
			deg = csdata.udeg;
			step = csdata.ustep;
			mat = csdata.umat;
			rat = csdata.rat;
		}

	}

	public static class Surface extends Element {

		public boolean	rat;
		public int		udeg, vdeg, ustep, vstep;
		public int[]	umat, vmat;

		public float	s0, s1, t0, t1;
		public int[]	vertices;

		public static Surface makeSurface(CSData csdata, float s0, float s1, float t0, float t1, int[] vertices) {
			if (!csdata.validateSurface())
				return null;
			return new Surface(csdata, s0, s1, t0, t1, vertices);
		}

		private Surface(CSData csdata, float s0, float s1, float t0, float t1, int[] vertices) {
			super(ElementType.SURFACE);
			this.s0 = s0;
			this.s1 = s1;
			this.t0 = t0;
			this.t1 = t1;
			this.vertices = vertices;
			udeg = csdata.udeg;
			vdeg = csdata.vdeg;
			ustep = csdata.ustep;
			vstep = csdata.vstep;
			umat = csdata.umat;
			vmat = csdata.vmat;
			rat = csdata.rat;
		}

	}

}
