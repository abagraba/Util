package PNGDecoder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import PNGDecoder.Chunks.ChunkIHDR;
import PNGDecoder.Chunks.ChunkcHRM;
import PNGDecoder.Chunks.ChunkgAMA;
import PNGDecoder.Chunks.ChunkPLTE;
import PNGDecoder.Chunks.ChunkpHYs;
import PNGDecoder.Chunks.ChunksRGB;
import PNGDecoder.Chunks.ChunktIME;

public class Chunk {
	
	@SuppressWarnings("unchecked")
	private static final Class<? extends Chunk>[] chunks = new Class[] { ChunkIHDR.class, ChunksRGB.class, ChunkgAMA.class, ChunkPLTE.class, ChunkcHRM.class,
			ChunkpHYs.class, ChunktIME.class };
	// iCCP sBIT tEXt iTXt zTXt bKGD hIST sPLT

	protected static final String IHDR = "IHDR";
	protected static final String sRGB = "sRGB";
	protected static final String gAMA = "gAMA";
	protected static final String PLTE = "PLTE";
	protected static final String cHRM = "cHRM";
	protected static final String pHYs = "pHYs";
	protected static final String tIME = "tIME";
	protected static final String iCCP = "iCCP";
	protected static final String sBIT = "sBIT";
	protected static final String IDAT = "IDAT";
	
	private static int tabs = 3;
	private static int tabwidth = 8;
	private static final char[] hexchar = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private byte headerMask = 0x20;

	private byte criticalMask = 0x8;
	private byte publicMask = 0x4;
	private byte validMask = 0x2;
	private byte unsafeMask = 0x1;

	protected int size, crc;
	protected String header;
	protected byte[] data;
	protected boolean valid = true;

	private byte chunkSpecs = 0;

	private boolean badHeader = false;

	protected String errorDetails = "Invalid size.\n";

	protected static HashMap<String, Integer> parseState = new HashMap<String, Integer>();

	public static void register() {
		for (Class<? extends Chunk> c : chunks) {
			try {
				c.getMethod("register").invoke(null);
			}
			catch (NoSuchMethodException e) {
				System.err.println("Failed to register Chunk. register() method not found.");
			}
			catch (SecurityException e) {
				System.err.println("Failed to register Chunk. Security Policy does not allow it.");
			}
			catch (IllegalAccessException e) {
				System.err.println("Failed to register Chunk. Illegal Access.");
			}
			catch (IllegalArgumentException e) {
				System.err.println("Failed to register Chunk. Somehow received invalid arguments to method with no arguments.");
			}
			catch (InvocationTargetException e) {
				System.err.println("Failed to register Chunk. Failed to invoke register() method.");
			}
		}
	}
	
	protected Chunk(int size, int crc){
		this.size = size;
		this.crc = crc;
	}

	public Chunk(int size, byte[] header, byte[] data, int crc) {
		this.size = size;
		this.header = new String(header);
		this.data = data;
		this.crc = crc;
		if ((header[0] & headerMask) != headerMask)
			chunkSpecs |= criticalMask;
		if ((header[1] & headerMask) != headerMask)
			chunkSpecs |= publicMask;
		if ((header[2] & headerMask) != headerMask)
			chunkSpecs |= validMask;
		else
			badHeader = true;
		if ((header[3] & headerMask) != headerMask)
			chunkSpecs |= unsafeMask;
	}

	public String toString() {
		if (badHeader)
			return "Invalid Chunk: Bad Header.";
		return "Chunk: [" + header + "]\n\t" + ((chunkSpecs & criticalMask) == criticalMask ? "Critical Chunk" : "Ancillary Chunk") + "\n\t"
				+ ((chunkSpecs & publicMask) == publicMask ? "Public Chunk" : "Private Chunk") + "\n\t"
				+ ((chunkSpecs & unsafeMask) == unsafeMask ? "Unsafe to Copy" : "Safe to Copy") + "\n" + pad("Size:") + toHex(size) + "\t" + unsign(size)
				+ "\n" + pad("CRC:") + toHex(crc) + "\t" + unsign(crc)
				+ (valid()&&valid ? ("\n\n" + chunkDescription() + "\n") : "\n[Invalid " + header + " Chunk]\n" + errorDetails );
	}

	protected boolean valid() {
		return true;
	}

	protected String chunkDescription() {
		if (size == 0)
			return "";
		int bytesPerLine = 80;
		String desc = "\tRaw Data:\n";
		for (int i = 0; i <= (data.length - 1) / bytesPerLine; i++) {
			int len = data.length - i * bytesPerLine;
			if (len > bytesPerLine)
				len = bytesPerLine;
			desc += "\t\t" + i * bytesPerLine + "\t";
			desc += toHex(data, i * bytesPerLine, len);
			desc += "\n";
		}
		return desc;
	}

	public boolean hdrChunk() {
		return "IHDR".equals(header);
	}
	
	public boolean endChunk() {
		return "IEND".equals(header);
	}

	public static String pad(String text) {
		String s = "\t" + text;
		if (text.length() < tabs * tabwidth) {
			int i = tabs - text.length() / tabwidth;
			while (i-- > 0)
				s += '\t';
		}
		return s;
	}

	public static long unsign(int i) {
		long l = (long) Integer.MAX_VALUE - Integer.MIN_VALUE + 1;
		return (i + l) % l;
	}

	public static int unsign(byte b) {
		int i = (int) Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
		return (b + i) % i;
	}

	public static String toHex(byte[] data, int offset, int length) {
		String s = "";
		for (int i = offset; i < offset + length; i++)
			s += toHex(data[i]) + " ";
		return s;
	}

	public static String toHex(byte b) {
		int i = (b + 256) % 256;
		return hexchar[i / 16] + "" + hexchar[i % 16];
	}

	public static String toHex(int i) {
		long l = unsign(i);
		return toHex(new byte[] { (byte) (l >> 24), (byte) (l >> 16), (byte) (l >> 8), (byte) l }, 0, 4);
	}

	public static int toInt(byte[] b) {
		if (b.length != 4)
			return -1;
		return (unsign(b[0]) << 24) + (unsign(b[1]) << 16) + (unsign(b[2]) << 8) + (unsign(b[3]));
	}

	public static int toInt(byte[] b, int start) {
		if (b.length < start + 4)
			return -1;
		return (unsign(b[start]) << 24) + (unsign(b[start + 1]) << 16) + (unsign(b[start + 2]) << 8) + (unsign(b[start + 3]));
	}
}