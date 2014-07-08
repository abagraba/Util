package PNGDecoder.Chunks;

import util.formatting.PrimitiveFormat;
import util.formatting.TextFormat;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunkpHYs extends Chunk {

	public long xaxis;
	public long yaxis;
	public pHYsUnit unit;

	public static void register() {
		ChunkRegistry.registerChunk(pHYs, ChunkpHYs.class);
		System.out.println("pHYs Chunk Registered.");
	}

	public ChunkpHYs(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		xaxis = PrimitiveFormat.unsign(PrimitiveFormat.toInt(data, 0));
		yaxis = PrimitiveFormat.unsign(PrimitiveFormat.toInt(data, 4));
		unit = pHYsUnit.get(data[8]);
	}

	protected boolean valid() {
		return size == 9;
	}

	protected String chunkDescription() {
		return /**/
		TextFormat.pad("X-axis:") + xaxis + "\n" + /**/
		TextFormat.pad("Y-axis:") + yaxis + "\n" + /**/
		TextFormat.pad("Unit:") + unit;
	}

	public static enum pHYsUnit {
		ratio("Ratio"), meter("Meters");

		private String name;

		public static pHYsUnit get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private pHYsUnit(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

}
