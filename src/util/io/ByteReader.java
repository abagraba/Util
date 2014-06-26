package util.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class ByteReader {

	private InputStream in;

	public ByteReader(InputStream in) {
		this.in = in;
		if (in == null)
			throw new NullPointerException("InputStream cannot be null.");
	}

	/**
	 * Reads a single byte from the InputStream.
	 * 
	 * @return the read byte.
	 * @throws IOException
	 *             Thrown when InputStream cannot be read from or when EOF is encountered.
	 */
	public byte read() throws IOException {
		int read = in.read();
		if (read == -1)
			throw new EOFException("Unexpected EOF");
		return (byte) read;
	}

	/**
	 * Reads from InputStream until specified portion of data is filled.
	 * 
	 * @param data
	 *            Array to be filled with data.
	 * @param off
	 *            Offset into the array.
	 * @param len
	 *            Number of bytes to be read.
	 * @return data.
	 * @throws IOException
	 *             Thrown when InputStream cannot be read from or when EOF is encountered.
	 */
	public byte[] readBytes(byte[] data, int off, int len) throws IOException {
		int totalRead = 0;
		int read;
		while (totalRead < len) {
			read = in.read(data, off + totalRead, len - totalRead);
			if (read == -1)
				throw new EOFException("Unexpected EOF");
			totalRead += read;
		}
		return data;
	}

	/**
	 * Reads from InputStream until data is fully filled.
	 * 
	 * @param data
	 *            Array to be filled with data.
	 * @return data.
	 * @throws IOException
	 *             Thrown when InputStream cannot be read from or when EOF is encountered.
	 */

	public byte[] readBytes(byte[] data) throws IOException {
		return readBytes(data, 0, data.length);
	}

	/**
	 * Reads from InputStream until data is fully filled.
	 * 
	 * @param length
	 *            Number of bytes to be read.
	 * @return byte[] containing requested data.
	 * @throws IOException
	 *             Thrown when InputStream cannot be read from or when EOF is encountered.
	 */
	public byte[] readBytes(int length) throws IOException {
		byte[] data = new byte[length];
		return readBytes(data);
	}

}
