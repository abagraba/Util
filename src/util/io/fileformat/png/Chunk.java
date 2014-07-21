package PNGDecoder;

import static util.logging.Logger.indent;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import util.formatting.PrimitiveFormat;
import util.formatting.TextFormat;
import util.logging.Loggable;
import util.logging.Logger;
import PNGDecoder.Chunks.ChunkIHDR;
import PNGDecoder.Chunks.ChunkPLTE;
import PNGDecoder.Chunks.ChunkcHRM;
import PNGDecoder.Chunks.ChunkgAMA;
import PNGDecoder.Chunks.ChunkpHYs;
import PNGDecoder.Chunks.ChunksRGB;
import PNGDecoder.Chunks.ChunktIME;



public class Chunk implements Loggable {

	@SuppressWarnings("unchecked")
	private static final Class<? extends Chunk>[]	chunks			= new Class[] { ChunkIHDR.class, ChunksRGB.class, ChunkgAMA.class,
			ChunkPLTE.class, ChunkcHRM.class, ChunkpHYs.class, ChunktIME.class };
	// iCCP sBIT tEXt iTXt zTXt bKGD hIST sPLT

	protected static final String					IHDR			= "IHDR";
	protected static final String					sRGB			= "sRGB";
	protected static final String					gAMA			= "gAMA";
	protected static final String					PLTE			= "PLTE";
	protected static final String					cHRM			= "cHRM";
	protected static final String					pHYs			= "pHYs";
	protected static final String					tIME			= "tIME";
	protected static final String					iCCP			= "iCCP";
	protected static final String					sBIT			= "sBIT";
	protected static final String					IDAT			= "IDAT";

	private byte									headerMask		= 0x20;

	private byte									criticalMask	= 0x8;
	private byte									publicMask		= 0x4;
	private byte									validMask		= 0x2;
	private byte									unsafeMask		= 0x1;

	protected int									size, crc;
	protected String								header;
	protected byte[]								data;
	protected boolean								valid			= true;

	private byte									chunkSpecs		= 0;

	private boolean									badHeader		= false;

	protected String								errorDetails	= "Unknown Error";

	protected static HashMap<String, Integer>		parseState		= new HashMap<String, Integer>();

	public static void register() {
		for (Class<? extends Chunk> c : chunks)
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

	protected Chunk(int size, byte[] header, int crc) {
		this.size = size;
		this.crc = crc;
		this.header = new String(header);
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

	public Chunk(int size, byte[] header, byte[] data, int crc) {
		this(size, header, crc);
		this.data = data;
	}

	@Override
	public void log() {
		if (badHeader) {
			Logger.log("Invalid Chunk: Bad Header.");
			return;
		}
		Logger.log("Chunk: [" + header + "]");
		indent();
		if (isCritical())
			Logger.log("Critical Chunk");
		else
			Logger.log("Ancillary Chunk");
		if (isPublic())
			Logger.log("Public Chunk");
		else
			Logger.log("Private Chunk");
		if (isUnsafe())
			Logger.log("Unsafe to Copy");
		else
			Logger.log("Safe to Copy");
		Logger.log(TextFormat.pad("Size:", 3) + TextFormat.toHex(size) + "\t" + PrimitiveFormat.unsign(size));
		Logger.log(TextFormat.pad("CRC:", 3) + TextFormat.toHex(crc) + "\t" + PrimitiveFormat.unsign(crc));
		if (valid() && valid)
			Logger.log(chunkDescription());
		else {
			Logger.log("[Invalid " + header + " Chunk]");
			Logger.log(errorDetails);
		}
	}

	protected boolean valid() {
		return true;
	}

	public boolean isCritical() {
		return (chunkSpecs & criticalMask) == criticalMask;
	}

	public boolean isPublic() {
		return (chunkSpecs & publicMask) == publicMask;
	}

	public boolean isUnsafe() {
		return (chunkSpecs & unsafeMask) == unsafeMask;
	}

	protected String chunkDescription() {
		String desc = "\tRaw Data:\n";
		return desc + TextFormat.bytesToHex(data, 80);
	}

	public boolean hdrChunk() {
		return "IHDR".equals(header);
	}

	public boolean endChunk() {
		return "IEND".equals(header);
	}
}
