package PNGDecoder.Chunks;

import util.formatting.TextFormat;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunksRGB extends Chunk {

	public sRGBIntent intent;

	public static void register() {
		ChunkRegistry.registerChunk(sRGB, ChunksRGB.class);
		System.out.println("sRGB Chunk Registered.");
	}

	public ChunksRGB(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		intent = sRGBIntent.get(data[0]);

	}

	protected boolean valid() {
		return size == 1;
	}

	protected String chunkDescription() {
		return TextFormat.pad("Rendering Intent:") + intent;
	}

	public static enum sRGBIntent {
		perc("Perceptual"), relc("Relative Colorimetric"), satu("Saturation"), absc("Absolute Colorimetric");

		private String name;

		public static sRGBIntent get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private sRGBIntent(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

}
