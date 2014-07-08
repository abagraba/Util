package util.logging;

import java.util.ArrayList;



public class Logger {

	private static String				tab		= "\t";
	private static int					indent	= 0;
	private static ArrayList<String>	indents	= new ArrayList<String>();

	public static void setIndentString(String indent) {
		tab = indent;
		indents = new ArrayList<String>();
		indents.add("");
	}

	public static void indent() {
		indent++;
	}

	public static void unindent() {
		if (indent > 0)
			indent--;
	}

	public static void log(String string) {
		System.out.print(getIndent());
		System.out.println(string);
	}

	public static void log(Loggable loggable) {
		loggable.log();
	}

	public static String getIndent() {
		if (indent >= indents.size())
			for (int i = indents.size(); i <= indent; i++)
				indents.add(indents.get(i - 1) + tab);
		return indents.get(indent);
	}
}
