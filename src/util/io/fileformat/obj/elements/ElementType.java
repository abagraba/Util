package util.io.fileformat.obj.elements;

public enum ElementType {

	POINT("Point"), LINE("Line"), FACE("Face"), CURVE("Curve"), SURFACE("Surface");

	private final String	name;

	private ElementType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
