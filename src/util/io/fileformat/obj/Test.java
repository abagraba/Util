package util.io.fileformat.obj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import util.io.fileformat.obj.parsing.OBJParser;



public class Test {

	public static void main(String[] args) {
		try {
			new OBJParser(new FileReader(new File("src/util/io/fileformat/obj/test.obj"))).run();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
