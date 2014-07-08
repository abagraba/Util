package util.formatting;

public class PrimitiveFormat {

	public static long unsign(int i) {
		long l = (long) Integer.MAX_VALUE - Integer.MIN_VALUE + 1;
		return (i + l) % l;
	}

	public static int unsign(byte b) {
		int i = (int) Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
		return (b + i) % i;
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
