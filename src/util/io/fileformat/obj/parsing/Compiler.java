package util.io.fileformat.obj.parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;



public class Compiler {

	public static String	jflex		= "../../../../compiling/jflex.jar";
	public static String	yacc		= "src/util/compiling/yacc.exe";

	public static String	targetPath	= "src/util/io/fileformat/obj/parsing/";

	public static String	target		= "obj";

	static String[]			jflexcmd	= new String[] { "java", "-jar", jflex, "-v", "--noinputstreamctor", target + ".flex" };
	static String[]			yacccmd		= new String[] { yacc, "-Jclass=OBJParser", "-Jpackage=util.io.fileformat.obj.parsing",
			"-Jsemantic=OBJValue", target + ".y" };
	static String[]			cleancmd	= new String[] { "rm", "OBJLexer.java~" };

	public static void runProcess(String[] args) {
		System.out.println("Running process:");
		System.out.print('\t');
		for (String arg : args)
			System.out.print(arg + " ");
		System.out.println();
		ProcessBuilder builder = new ProcessBuilder(args);
		builder.directory(new File(targetPath));
		builder.redirectErrorStream(true);
		try {
			Process process = builder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null)
				System.out.println('\t' + line);
			if (process.waitFor() != 0)
				System.out.println("Terminated unexpectedly (" + process.exitValue() + ")");
		}
		catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Process terminated.");
		System.out.println();
	}
	
	public static void compile(){
		runProcess(jflexcmd);
		runProcess(yacccmd);
		runProcess(cleancmd);
	}

	public static void main(String[] args) {
		compile();
	}

}
