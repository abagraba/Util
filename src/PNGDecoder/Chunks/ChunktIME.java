package PNGDecoder.Chunks;

import java.util.Date;

import util.formatting.TextFormat;
import PNGDecoder.Chunk;
import PNGDecoder.ChunkRegistry;

public class ChunktIME extends Chunk {

	public Date date;
	
	public static void register() {
		ChunkRegistry.registerChunk(tIME, ChunktIME.class);
		System.out.println("tIME Chunk Registered.");
	}

	public ChunktIME(int size, byte[] header, byte[] data, int crc) {
		super(size, header, data, crc);
		if (parseState.containsKey(tIME)){
			valid = false;
			errorDetails = "There may only be one tIME Chunk.\n";
		}
		parseState.put(tIME, 0);
		date.setHours(hours);
	}

	protected boolean valid() {
		return size == 7;
	}

	protected String chunkDescription() {
		return TextFormat.pad("Last Modified:") + padtime((data[4] + 11) % 12 + 1) + ":" + padtime(data[5]) + ":" + padtime(data[6]) + " " + (data[4] < 12 ? "A" : "P") + "M UTC\t"
				+ tIMEMonth.get(data[2]) + " " + data[3] + ", " + (data[0] * 256 + data[1]);
	}

	private enum tIMEMonth {
		none("None"), jan("January"), feb("February"), mar("March"), apr("April"), may("May"), jun("June"), jul("July"), aug("August"), sep("September"), oct(
				"October"), nov("November"), dec("December");

		private String name;

		public static tIMEMonth get(int i) {
			if (i < 0 || i >= values().length)
				return null;
			return values()[i];
		}

		private tIMEMonth(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}
	}

	private String padtime(int i) {
		return i < 10 ? "0" + i : "" + i;
	}
}
