package util.io.fileformat.obj;

public class Element {

	public final Type type;
	public final int[] data;

	public Element(Type type, int[] data) {
		this.type = type;
		this.data = data;
	}

	public String toString() {
		return type.toString() + " [" + data.length + "]";
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
