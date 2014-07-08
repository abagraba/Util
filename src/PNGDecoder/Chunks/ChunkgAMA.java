package PNGDecoder.Chunks;

import util.formatting.PrimitiveFormat;
import util.formatting.TextFormat;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunkgAMA extends Chunk {

	public float gamma;
	
	public static void register() {
		ChunkRegistry.registerChunk(gAMA, ChunkgAMA.class);
		System.out.println("gAMA Chunk Registered.");
	}

	public ChunkgAMA(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		gamma = PrimitiveFormat.unsign(PrimitiveFormat.toInt(data, 0)) * 0.00001f;
	}

	protected boolean valid() {
		return size == 4;
	}

	protected String chunkDescription() {
		return TextFormat.pad("Gamma Correction:") + gamma + (gamma != 0 ? "\t\t1/" + (1 / gamma) : "");
	}
}
