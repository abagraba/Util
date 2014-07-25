package util.io.fileformat.obj.parsing;

public class ObjLogging {

	public static int			VERTEX		= 0;
	public static int			ELEMENT		= 1;
	public static int			CSELEMENT	= 2;
	public static int			MATERIAL	= 3;

	public static String		int1		= "%d\n";
	public static String		int2		= "%d %d\n";
	public static String		int3		= "%d %d %d\n";

	public static String		float1		= "%-8.8f\n";
	public static String		float2		= "%-8.8f %-8.8f\n";
	public static String		float3		= "%-8.8f %-8.8f %-8.8f\n";
	public static String		float4		= "%-8.8f %-8.8f %-8.8f %-8.8f\n";

	private static boolean[]	loggable	= { true, true, true, true };

	private static boolean log(int type) {
		if (type >= 0 && type < loggable.length)
			return loggable[type];
		return true;
	}

	public static void logln(int type, String text) {
		if (log(type))
			System.out.println(text);
	}

	public static void logf(int type, String format, Object... args) {
		if (log(type))
			System.out.printf(format, args);
	}

	public static void tab(int type) {
		if (log(type))
			System.out.print("  ");
	}

	public static void logObject(int type, String name) {
		if (log(type))
			logf(type, "%-16s\n", name);
	}

	public static void logObject(int type, String name, String desc) {
		if (log(type))
			logf(type, "%-16s %s\n", name, desc);
	}

	public static void logObject(int type, String name, int number) {
		if (log(type))
			logf(type, "%-12s #%- 10d:\t", name, number);
	}

}
