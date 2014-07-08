package util.formatting;




public class TextFormat {

	public static int			tabs		= 3;
	public static int			tabwidth	= 8;
	private static String		delimiter	= " ";

	private static final char[]	hexchar		= new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHex(byte[] data, int offset, int length) {
		String s = "";
		for (int i = offset; i < offset + length; i++)
			s += TextFormat.toHex(data[i]) + " ";
		return s;
	}

	public static String toHex(byte b) {
		int i = (b + 256) % 256;
		return hexchar[i / 16] + "" + hexchar[i % 16];
	}

	public static String toHex(int i) {
		long l = PrimitiveFormat.unsign(i);
		return toHex(new byte[] { (byte) (l >> 24), (byte) (l >> 16), (byte) (l >> 8), (byte) l }, 0, 4);
	}

	public static String bytesToHex(byte[] bytes, int bytesPerLine) {
		if (bytes.length < 1)
			return "";
		StringBuilder string = new StringBuilder();
		int numLines = (bytes.length - 1) / bytesPerLine + 1;
		for (int i = 0; i < numLines; i++) {
			int len = i == numLines - 1 ? (bytes.length - 1) % bytesPerLine + 1 : bytesPerLine;
			string.append(i * bytesPerLine);
			string.append("\t");
			bytesToHex(bytes, numLines * bytesPerLine, len, string);
		}
		return string.toString();
	}

	private static void bytesToHex(byte[] bytes, int off, int len, StringBuilder string) {
		for (int i = 0; i < len - 1; i++) {
			string.append(toHex(bytes[off + i]));
			string.append(delimiter);
		}
		string.append(toHex(bytes[off + len - 1]));
	}

	/**
	 * Pads a string to be the same length as the specified number of tabs. String must not contain tabs.
	 * 
	 * @param text
	 * @param tabs
	 * @return
	 */
	public static String pad(String text, int tabs) {
		StringBuilder string = new StringBuilder(text);
		if (text.length() < tabs * tabwidth) {
			int i = tabs - text.length() / tabwidth;
			while (i-- > 0)
				string.append('\t');
		}
		return string.toString();
	}

}
