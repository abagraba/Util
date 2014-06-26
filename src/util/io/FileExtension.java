package util.io;

import java.io.File;
import java.util.regex.Pattern;

public enum FileExtension {

	JPG("jpe?g"), GIF("gif"), PNG("png"), // Image Formats
	MP3("mp3"), MIDI("mid"), WAV("wave?"), WMA("wma"), AIFF("aif(:?c|f)?"), FLAC("flac"), M4A("m4a"), // Audio Formats
	WMV("wmv"), MP4("mp4"), MPG("mpe?g"), AVI("avi"), FLV("flv"), SWF("swf"), QTFF("(:?mov)|(:?qt)"), M4V("m4v"), // Video formats
	OGG("ogg"), OGA("oga"), OGV("ogv"), OGX("ogx"), SPX("spx"), OPUS("opus"), // Ogg formats
	DOC("docx?"), XLS("xlsx?"), PPT("pptx?"), // Microsoft formats
	PDF("pdf"), // PDF
	TXT("txt"), CSV("csv"), JSON("json"), DAT("dat"), LOG("log"), INI("ini"), // Raw text/data
	XML("xml"), HTML("html?"), // Markup Languages
	ZIP("zip"), RAR("rar"), ZIP7("7z") // Archives
	;

	private final Pattern filename;

	private FileExtension(String regex) {
		filename = Pattern.compile(".*\\." + regex, Pattern.CASE_INSENSITIVE);
	}

	public static FileExtension getType(File f) {
		for (FileExtension ext : values())
			if (ext.filename.matcher(f.getName()).matches())
				return ext;
		return null;
	}

}
