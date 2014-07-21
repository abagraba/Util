package util.io.fileformat.obj.parsing;

import java.util.ArrayList;



public class OBJRawData {
	public final static boolean	log			= true;

	private ArrayList<float[]>	vertices	= new ArrayList<float[]>();
	private ArrayList<float[]>	textures	= new ArrayList<float[]>();
	private ArrayList<float[]>	normals		= new ArrayList<float[]>();

	public void addVertex(OBJValue x, OBJValue y, OBJValue z) {
		vertices.add(new float[] { x.f, y.f, z.f });
		logf("Vertex #%- 10d:\t%- 8.8f %- 8.8f %- 8.8f", vertices.size(), x.f, y.f, z.f);
	}

	public void addTexture(OBJValue u, OBJValue v) {
		textures.add(new float[] { u.f, v.f });
		logf("Texture #%- 10d:\t%- 8.8f %- 8.8f", vertices.size(), u.f, v.f);
	}

	public void addNormal(OBJValue x, OBJValue y, OBJValue z) {
		normals.add(new float[] { x.f, y.f, z.f });
		logf("Vertex #%- 10d:\t%- 8.8f %- 8.8f %- 8.8f", vertices.size(), x.f, y.f, z.f);
	}

	public static void log(String text) {
		if (log)
			System.out.println(text);
	}

	public static void logf(String format, Object... args) {
		if (log)
			System.out.printf(format, args);
	}
}
