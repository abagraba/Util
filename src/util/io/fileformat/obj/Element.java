package util.io.fileformat.obj;

public class Element {

	public final Type type;

	public Element(Type type) {
		this.type = type;
	}

	public String toString() {
		return type.toString();
	}

	public static class Point extends Element {
		public int[] vertex;

		public Point(int[] vertex) {
			super(Type.POINT);
			this.vertex = vertex;
		}

		public String toString() {
			return super.toString() + " [" + vertex.length + "]";
		}
	}

	public static class Line extends Element {
		public int[] vertices;

		public Line(int[] vertices) {
			super(Type.LINE);
			this.vertices = vertices;
		}

		public String toString() {
			return super.toString() + " [" + vertices.length + "]";
		}
	}
	
	public static class Face extends Element {
		public int[] vertices;

		public Face(int[] vertices) {
			super(Type.FACEV);
			this.vertices = vertices;
		}

		public String toString() {
			return super.toString() + " [" + vertices.length + "]";
		}
	}

	public static enum Type {

		POINT(0, "Point"), LINE(1, "Line"), FACEV(1, "Face [Vertex]"), FACEVT(2, "Face [Vertex, Texture]"), FACEVN(2, "Face [Vertex, Normal]"), FACEVTN(3,
				"Face [Vertex, Texture, Normal]");

		public final int stride;
		private final String name;

		private Type(int stride, String name) {
			this.stride = stride;
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

}
