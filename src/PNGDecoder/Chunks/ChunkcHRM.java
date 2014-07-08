package PNGDecoder.Chunks;

import static util.formatting.PrimitiveFormat.toInt;
import static util.formatting.PrimitiveFormat.unsign;
import static util.formatting.TextFormat.pad;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;



public class ChunkcHRM extends Chunk {

	public long	whitePointX;
	public long	whitePointY;
	public long	redX;
	public long	redY;
	public long	greenX;
	public long	greenY;
	public long	blueX;
	public long	blueY;

	public static void register() {
		ChunkRegistry.registerChunk(cHRM, ChunkcHRM.class);
		System.out.println("cHRM Chunk Registered.");
	}

	public ChunkcHRM(int size, byte[] header, byte[] data, int crc) {
		super(size, header, crc);
		whitePointX = unsign(toInt(data, 0));
		whitePointY = unsign(toInt(data, 4));
		redX = unsign(toInt(data, 8));
		redY = unsign(toInt(data, 12));
		greenX = unsign(toInt(data, 16));
		greenY = unsign(toInt(data, 20));
		blueX = unsign(toInt(data, 24));
		blueY = unsign(toInt(data, 28));
	}

	@Override
	protected boolean valid() {
		if (size == 32)
			return true;
		errorDetails = "Invalid size.";
		return false;
	}

	@Override
	protected String chunkDescription() {
		return /**/
		pad("White Point:", 3) + "(" + whitePointX + ", " + whitePointY + ")\n" + /**/
		pad("Red:", 3) + "(" + redX + ", " + redY + ")\n" + /**/
		pad("Green:", 3) + "(" + greenX + ", " + greenY + ")\n" + /**/
		pad("Blue:", 3) + "(" + blueX + ", " + blueY + ")";
	}

}
