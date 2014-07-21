package PNGDecoder.Chunks;

import util.formatting.TextFormat;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunkPLTE extends Chunk {

	private static int entriesPerRow = 8;

	public PLTEColor[] palette;

	public static void register() {
		ChunkRegistry.registerChunk(PLTE, ChunkPLTE.class);
		System.out.println("PLTE Chunk Registered.");
	}

	public ChunkPLTE(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		palette = new PLTEColor[data.length / 3];
		for (int i = 0; i < data.length / 3; i++)
			palette[i] = new PLTEColor(data, i * 3);
	}

	protected boolean valid() {
		return size % 3 == 0 && size <= 256 * 3 && size > 0;
	}

	protected String chunkDescription() {
		String s = "\tColor Palette:";
		for (int i = 0; i < palette.length; i++)
			s += (i % entriesPerRow == 0 ? "\n\t\t" : "\t\t") + i + ":\t" + palette[i];
		return s;
	}

	public static class PLTEColor {
		public byte r, g, b;

		public PLTEColor(byte r, byte g, byte b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public PLTEColor(byte[] color) {
			if (color.length != 3)
				throw new IllegalArgumentException("PLTEColor may only have three Components.");
			this.r = color[0];
			this.g = color[1];
			this.b = color[2];
		}

		public PLTEColor(byte[] color, int offset) {
			if (offset + 3 > color.length)
				throw new ArrayIndexOutOfBoundsException();
			this.r = color[offset];
			this.g = color[offset + 1];
			this.b = color[offset + 2];
		}

		public String toString() {
			return "0x" + TextFormat.toHex(r) + TextFormat.toHex(g) + TextFormat.toHex(b);
		}
	}

}
