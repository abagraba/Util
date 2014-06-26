package PNGDecoder.Chunks;

import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunkcHRM extends Chunk {

	public long whitePointX;
	public long whitePointY;
	public long redX;
	public long redY;
	public long greenX;
	public long greenY;
	public long blueX;
	public long blueY;

	public static void register() {
		ChunkRegistry.registerChunk(cHRM, ChunkcHRM.class);
		System.out.println("cHRM Chunk Registered.");
	}

	public ChunkcHRM(int size, byte[] header, byte[] data, int crc) {
		super(size, crc);
		whitePointX = unsign(toInt(data, 0));
		whitePointY = unsign(toInt(data, 4));
		redX = unsign(toInt(data, 8));
		redY = unsign(toInt(data, 12));
		greenX = unsign(toInt(data, 16));
		greenY = unsign(toInt(data, 20));
		blueX = unsign(toInt(data, 24));
		blueY = unsign(toInt(data, 28));
	}

	protected boolean valid() {
		return size == 32;
	}

	protected String chunkDescription() {
		return /**/
		pad("White Point:") + "(" + whitePointX + ", " + whitePointY + ")\n" + /**/
		pad("Red:") + "(" + redX + ", " + redY + ")\n" + /**/
		pad("Green:") + "(" + greenX + ", " + greenY + ")\n" + /**/
		pad("Blue:") + "(" + blueX + ", " + blueY + ")";
	}

}
