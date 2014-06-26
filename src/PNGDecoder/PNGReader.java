package PNGDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PNGReader {

	private static final byte[] pngsig = new byte[] { (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };

	public static void readPNG(File f) throws IOException {
		InputStream is = new FileInputStream(f);
		Chunk chunk;
		boolean hdr = false;
		boolean end = false;
		byte[] sig = new byte[pngsig.length];
		Chunker.readBytes(is, sig);
		for (int i = 0; i < sig.length; i++)
			if (sig[i] != pngsig[i]) {
				System.err.println("Invalid PNG Sginature");
				System.exit(-1);
			}
		while (!end && (chunk = Chunker.nextChunk(is)) != null) {
			System.out.println(chunk);
			if (chunk.hdrChunk())
				hdr = true;
			if (chunk.endChunk())
				end = true;
			if (!hdr){
				System.err.println("First PNG chunk is not an IHDR Chunk.");
				System.exit(-1);
			}
			chunk = null;
		}
		byte[] buffer = new byte[1024];
		int bytesRead=0;
		int i;
		while ((i = is.read(buffer)) != -1)
			bytesRead += i;
		if (bytesRead != 0)
			System.err.println("Extra data after IEND Chunk. " + bytesRead + " bytes discarded.");
		is.close();
	}

	public static void main(String[] args) {
		Chunk.register();
		System.out.println();
		File img = new File(System.getProperty("user.home") + "/../mzhu/Desktop/cat.png");
		try {
			readPNG(img);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
