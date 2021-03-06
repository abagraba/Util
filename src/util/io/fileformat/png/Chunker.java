package PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import util.formatting.PrimitiveFormat;
import util.formatting.TextFormat;
 
public class Chunker {

	private static byte[] size = new byte[4];
	private static byte[] header = new byte[4];
	private static byte[] crc = new byte[4];

	public static Chunk nextChunk(InputStream src) {
		try {
			readBytes(src, size);
			readBytes(src, header);
		} catch (IOException e) {
			e.printStackTrace();
			return new Chunk(0, "IEND".getBytes(), new byte[] {}, -1371381886);
		}
		int chunkSize = PrimitiveFormat.toInt(size);
		if (chunkSize < 0) {
			System.err.println("Negative chunk size: \t"
					+ TextFormat.toHex(size, 0, 4) + "\t" + chunkSize);
			return new Chunk(0, "IEND".getBytes(), new byte[] {}, -1371381886);
		}
		byte[] chunkData = new byte[chunkSize];
		try {
			readBytes(src, chunkData);
			readBytes(src, crc);
		} catch (IOException e) {
			e.printStackTrace();
			return new Chunk(0, "IEND".getBytes(), new byte[] {}, -1371381886);
		}
		int crcval = PrimitiveFormat.toInt(crc);

		Class<? extends Chunk> impl = ChunkRegistry
				.getChunkImplementation(new String(header));
		try {
			Constructor<? extends Chunk> ctor = impl.getDeclaredConstructor(
					int.class, byte[].class, byte[].class, int.class);
			return ctor.newInstance(new Object[] { chunkSize, header,
					chunkData, crcval });
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void readBytes(InputStream src, byte[] data)
			throws IOException {
		int bytesRead = 0;
		while (bytesRead < data.length) {
			int i = src.read(data, bytesRead, data.length - bytesRead);
			if (i == -1) {
				System.err.println("Invalid Stream Read: Unexpected EOF.");
				System.exit(-1);
			}
			bytesRead += i;
		}
	}

}
