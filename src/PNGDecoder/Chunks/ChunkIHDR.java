package PNGDecoder.Chunks;

import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunkIHDR extends Chunk {

	public long width;
	public long height;
	public IHDRColorType colorType;
	public IHDRCompressionType compressionType;
	public IHDRInterlaceType interlaceType;

	public static void register() {
		ChunkRegistry.registerChunk(IHDR, ChunkIHDR.class);
		System.out.println("IHDR Chunk Registered.");
	}

	public ChunkIHDR(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		width = unsign(toInt(data, 0));
		height = unsign(toInt(data, 4));
		colorType = IHDRColorType.get(data[9]);
		compressionType = IHDRCompressionType.get(data[10]);
		interlaceType = IHDRInterlaceType.get(data[12]);
	}

	protected boolean valid() {
		return size == 13;
	}

	protected String chunkDescription() {
		return /**/
		pad("Width:") + width + " pixels\n" + /**/
		pad("Height:") + height + " pixels\n" + /**/
		pad("Bit Depth:") + data[8] + " bits\t\t" + (validBitDepth(data[8], data[9]) ? "" : "[Invalid/Unrecognized bit depth]") + "\n" + /**/
		pad("Color Type:") + colorType + "\n" + /**/
		pad("Compression Method:") + compressionType + "\n" + /**/
		pad("Filter Method:") + data[11] + "\n" + /**/
		pad("Interlace Method:") + interlaceType;
	}

	private static boolean validBitDepth(int depth, int color) {
		switch (depth) {
		case 1:
		case 2:
		case 4:
			return color == 0 || color == 3;
		case 8:
			return color == 0 || color == 2 || color == 3 || color == 4 || color == 6;
		case 16:
			return color == 0 || color == 2 || color == 4 || color == 6;
		default:
			return false;
		}
	}

	public static enum IHDRColorType {
		grey("Greyscale"), null1("null"), truc("True Color"), indx("Indexed Color"), gray("Greycale with Alpha"), null2("null"), trua("True Color with Alpha");

		private String name;

		public static IHDRColorType get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private IHDRColorType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	public static enum IHDRCompressionType {
		def("Deflate/Inflate");

		private String name;

		public static IHDRCompressionType get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private IHDRCompressionType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	public static enum IHDRInterlaceType {
		none("No Interlacing"), adam("Adam7 Interlacing");

		private String name;

		public static IHDRInterlaceType get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private IHDRInterlaceType(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

}
