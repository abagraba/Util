package util.io.fileformat.obj.parsing;

import java.util.ArrayList;

import util.io.fileformat.obj.Element;



public class OBJRawData {
	public final static boolean	log			= true;

	private ArrayList<float[]>	vertices	= new ArrayList<float[]>();
	private ArrayList<float[]>	textures	= new ArrayList<float[]>();
	private ArrayList<float[]>	normals		= new ArrayList<float[]>();

	private ArrayList<Element>	elements	= new ArrayList<Element>();

	private ArrayList<String>	libraries	= new ArrayList<String>();

	private String				object		= "_DEFAULT_";
	private String				group		= "_DEFAULT_";
	private String				material	= "_DEFAULT_";

	/**/

	public void addVertex(OBJValue x, OBJValue y, OBJValue z) {
		vertices.add(new float[] { x.f, y.f, z.f, 1 });
		logObject("Vertex", vertices.size());
		logf("%-8.8f %-8.8f %-8.8f\n", x.f, y.f, z.f);
	}

	public void addVertex(OBJValue x, OBJValue y, OBJValue z, OBJValue w) {
		vertices.add(new float[] { x.f, y.f, z.f, w.f });
		logObject("Vertex", vertices.size());
		logf("%-8.8f %-8.8f %-8.8f\n", x.f, y.f, z.f);
	}

	public void addTexture(OBJValue u, OBJValue v) {
		textures.add(new float[] { u.f, v.f, 0 });
		logObject("Texture", textures.size());
		logf("%-8.8f %-8.8f\n", u.f, v.f);
	}

	public void addTexture(OBJValue u, OBJValue v, OBJValue w) {
		textures.add(new float[] { u.f, v.f, w.f });
		logObject("Texture", textures.size());
		logf("%-8.8f %-8.8f\n", u.f, v.f);
	}

	public void addNormal(OBJValue x, OBJValue y, OBJValue z) {
		normals.add(new float[] { x.f, y.f, z.f });
		logObject("Normal", normals.size());
		logf("%-8.8f %-8.8f %-8.8f\n", x.f, y.f, z.f);
	}

	public void addElement(Element e) {
		elements.add(e);
		tab();
		tab();
		tab();
		logObject("Element", elements.size());
		logln(e.toString());
	}

	/**/

	public void corruptVertex() {
		vertices.add(null);
		logObject("Vertex", vertices.size());
		logln("CORRUPT");
	}

	public void corruptTexture() {
		textures.add(null);
		logObject("Texture", textures.size());
		logln("CORRUPT");
	}

	public void corruptNormal() {
		normals.add(null);
		logObject("Normal", normals.size());
		logln("CORRUPT");
	}

	public void corruptElement() {
		elements.add(null);
		tab();
		tab();
		tab();
		logObject("Element", elements.size());
		logln("CORRUPT");
	}

	/**/

	public void setObject(OBJValue name) {
		object = name.s;
		logObject("Object", object);
	}

	public void setGroup(OBJValue name) {
		group = name.s;
		tab();
		logObject("Group", group);
	}

	public void setMaterial(OBJValue name) {
		material = name.s;
		tab();
		tab();
		logObject("Material", material);
	}

	public void addLibrary(OBJValue name) {
		libraries.add(name.s);
		logObject("Loading Library", name.s);
	}

	/**/

	private void logObject(String name, String desc) {
		logf("%-16s %s\n", name, desc);
	}

	private void logObject(String name, int number) {
		logf("%-12s #%- 10d:\t", name, number);
	}

	public static void tab() {
		if (log)
			System.out.print("  ");
	}

	public static void logln(String text) {
		if (log)
			System.out.println(text);
	}

	public static void logf(String format, Object... args) {
		if (log)
			System.out.printf(format, args);
	}

	public int evaluateVertex(OBJValue val) {
		if (val.i < 0)
			return vertices.size() + 1 + val.i;
		return val.i;
	}

	public int evaluateTexture(OBJValue val) {
		if (val.i < 0)
			return textures.size() + 1 + val.i;
		return val.i;
	}

	public int evaluateNormal(OBJValue val) {
		if (val.i < 0)
			return normals.size() + 1 + val.i;
		return val.i;
	}
}
