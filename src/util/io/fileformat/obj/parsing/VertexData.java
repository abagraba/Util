package util.io.fileformat.obj.parsing;

import java.util.ArrayList;

import util.LFloatArray;
import util.io.fileformat.obj.elements.Element;



public class VertexData {
	public final static int		VERTEX		= ObjLogging.VERTEX;
	public final static int		ELEMENT		= ObjLogging.ELEMENT;
	public final static int		MATERIAL	= ObjLogging.MATERIAL;
	public final static int		GROUP		= ObjLogging.GROUP;

	private LFloatArray			vertices	= new LFloatArray();
	private LFloatArray			textures	= new LFloatArray();
	private LFloatArray			normals		= new LFloatArray();
	private LFloatArray			parameters	= new LFloatArray();

	private ArrayList<Element>	elements	= new ArrayList<Element>();

	private ArrayList<String>	libraries	= new ArrayList<String>();

	private String				object		= "default";
	private String				group		= "default";
	private int					smooth		= 0;
	private int					merge		= 0;
	private int					mergeres	= 0;
	private String				material	= null;

	/**********/

	public void addVertex(OBJValue x, OBJValue y, OBJValue z) {
		addVertex(x.f, y.f, z.f, 1);
		x.freeValue();
		y.freeValue();
		z.freeValue();
	}

	public void addVertex(OBJValue x, OBJValue y, OBJValue z, OBJValue w) {
		addVertex(x.f, y.f, z.f, w.f);
		x.freeValue();
		y.freeValue();
		z.freeValue();
		w.freeValue();
	}

	private void addVertex(float x, float y, float z, float w) {
		vertices.append(x, y, z, w);
		ObjLogging.logObject(VERTEX, "Vertex", vertices.size());
		ObjLogging.logf(VERTEX, ObjLogging.float4, x, y, z, w);
	}

	public void addTexture(OBJValue u) {
		addTexture(u.f, 0, 0);
		u.freeValue();
	}

	public void addTexture(OBJValue u, OBJValue v) {
		addTexture(u.f, v.f, 0);
		u.freeValue();
		v.freeValue();
	}

	public void addTexture(OBJValue u, OBJValue v, OBJValue w) {
		addTexture(u.f, v.f, w.f);
		u.freeValue();
		v.freeValue();
		w.freeValue();
	}

	private void addTexture(float u, float v, float w) {
		textures.append(u, v, w, 1);
		ObjLogging.logObject(VERTEX, "Texture", textures.size());
		ObjLogging.logf(VERTEX, ObjLogging.float3, u, v, w);
	}

	public void addNormal(OBJValue x, OBJValue y, OBJValue z) {
		normals.append(x.f, y.f, z.f, 1);
		ObjLogging.logObject(VERTEX, "Normal", normals.size());
		ObjLogging.logf(VERTEX, ObjLogging.float3, x.f, y.f, z.f);
		x.freeValue();
		y.freeValue();
		z.freeValue();
	}

	public void addParameter(OBJValue u) {
		addParameter(u.f, 0, 1);
		u.freeValue();
	}

	public void addParameter(OBJValue u, OBJValue v) {
		addParameter(u.f, v.f, 1);
		u.freeValue();
		v.freeValue();
	}

	public void addParameter(OBJValue u, OBJValue v, OBJValue w) {
		addParameter(u.f, v.f, w.f);
		u.freeValue();
		v.freeValue();
		w.freeValue();
	}

	private void addParameter(float u, float v, float w) {
		parameters.append(u, v, w, 1);
		ObjLogging.logObject(VERTEX, "Parameter", parameters.size());
		ObjLogging.logf(VERTEX, ObjLogging.float3, u, v, w);
	}

	/**********/

	public void corruptVertex() {
		vertices.append(0, 0, 0, 1);
		ObjLogging.logObject(VERTEX, "Vertex", vertices.size());
		ObjLogging.logln(VERTEX, "CORRUPT");
	}

	public void corruptTexture() {
		textures.append(0, 0, 0, 1);
		ObjLogging.logObject(VERTEX, "Texture", textures.size());
		ObjLogging.logln(VERTEX, "CORRUPT");
	}

	public void corruptNormal() {
		normals.append(0, 0, 0, 1);
		ObjLogging.logObject(VERTEX, "Normal", normals.size());
		ObjLogging.logln(VERTEX, "CORRUPT");
	}

	public void corruptParameter() {
		parameters.append(0, 0, 0, 1);
		ObjLogging.logObject(VERTEX, "Parameter", parameters.size());
		ObjLogging.logln(VERTEX, "CORRUPT");
	}

	/**********/

	public void addElement(Element e) {
		elements.add(e);
		ObjLogging.tab(ELEMENT);
		ObjLogging.tab(ELEMENT);
		ObjLogging.tab(MATERIAL);
		ObjLogging.logObject(ELEMENT, "Element", elements.size());
		ObjLogging.logln(ELEMENT, e.toString());
	}

	public void corruptElement() {
		elements.add(null);
		ObjLogging.tab(ELEMENT);
		ObjLogging.tab(ELEMENT);
		ObjLogging.tab(MATERIAL);
		ObjLogging.logObject(ELEMENT, "Element", elements.size());
		ObjLogging.logln(ELEMENT, "CORRUPT");
	}

	/**********/

	public void setObject(OBJValue name) {
		object = name.s;
		ObjLogging.logObject(GROUP, "Object", object);
		name.freeValue();
	}

	public void setGroup(OBJValue name) {
		group = name.s;
		ObjLogging.tab(GROUP);
		ObjLogging.logObject(GROUP, "Group", group);
		name.freeValue();
	}

	public void setSmooth(OBJValue name) {
		smooth = name != null ? name.i : 0;
		ObjLogging.tab(GROUP);
		ObjLogging.logObject(GROUP, "Smooth Group", smooth);
		ObjLogging.logln(GROUP, "");
		if (name != null)
			name.freeValue();
	}

	public void setMerge(OBJValue name, OBJValue res) {
		merge = name != null ? name.i : 0;
		mergeres = res != null ? res.i : 0;
		ObjLogging.tab(GROUP);
		ObjLogging.logObject(GROUP, "Merge Group", merge);
		ObjLogging.logln(GROUP, "");
		if (merge != 0 && mergeres <= 0)
			ObjLogging.logln(GROUP, "Merge Group resolution must be > 0.");
		if (name != null)
			name.freeValue();
		if (res != null)
			res.freeValue();
	}

	public void setMaterial(OBJValue name) {
		material = name.s;
		ObjLogging.tab(MATERIAL);
		ObjLogging.tab(MATERIAL);
		ObjLogging.logObject(MATERIAL, "Material", material);
	}

	public void addLibrary(OBJValue name) {
		libraries.add(name.s);
		ObjLogging.logObject(MATERIAL, "Loading Library", name.s);
	}

	/**********/

	public int evaluateVertex(OBJValue val) {
		if (val.i < 0)
			return vertices.size() >> 2 + 1 + val.i;
		return val.i;
	}

	public int evaluateTexture(OBJValue val) {
		if (val.i < 0)
			return textures.size() >> 2 + 1 + val.i;
		return val.i;
	}

	public int evaluateNormal(OBJValue val) {
		if (val.i < 0)
			return normals.size() >> 2 + 1 + val.i;
		return val.i;
	}

	public int evaluateParameter(OBJValue val) {
		if (val.i < 0)
			return parameters.size() >> 2 + 1 + val.i;
		return val.i;
	}
}
