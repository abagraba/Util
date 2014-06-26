package PNGDecoder.Chunks;

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
		gamma = unsign(toInt(data, 0)) * 0.00001f;
	}

	protected boolean valid() {
		return size == 4;
	}

	protected String chunkDescription() {
		return pad("Gamma Correction:") + gamma + (gamma != 0 ? "\t\t1/" + (1 / gamma) : "");
	}
}
