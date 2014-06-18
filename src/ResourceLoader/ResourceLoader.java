package ResourceLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;



public class ResourceLoader {

	private static int					chunk		= 2048;
	String						root;
	File						tmp			= new File(System.getProperty("java.io.tmpdir"));

	String						tempSuffix	= ".part";

	private static final char[]	hex			= { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public ResourceLoader(String root) {
		this.root = root;
	}

	private boolean update(String directory, String filename, String hash, InputStream src) throws NoSuchAlgorithmException, FileNotFoundException,
			IOException
	{
		if (src == null){
			System.out.println("Source file does not exist.");
			return false;
		}
		File dst = null;
		try {
			dst = createTempFile(directory, filename);
		}
		catch (IOException e) {
			System.out.println("Failed to create destination file. Check to see if you have proper permissions.");
			return false;
		}
		if (dst == null){
			System.out.println("Failed to create destination file. Check to see if you have proper permissions.");
			return false;
		}
		String currhash = md5(new FileInputStream(dst));
		if (currhash.equals(hash))
			return true;
		new Downloader(dst, src).start();
		return true;
	}


	public String md5(InputStream file) throws NoSuchAlgorithmException, IOException {
		if (file == null)
			return "null";
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		DigestInputStream dis = new DigestInputStream(file, md5);
		while (dis.read() != -1)
			;
		byte[] hashVal = md5.digest();
		String s = "";
		for (int i = 0; i < hashVal.length; i++) {
			s += hex[hashVal[i] / 16];
			s += hex[hashVal[i] % 16];
		}
		return s;
	}

	public File createTempFile(String directory, String filename) throws IOException {
		File temp = new File(tmp, directory);
		File tempfile = new File(temp, filename);
		if (!createTempDirectory(directory))
			return null;
		if (tempfile.exists())
			return tempfile;
		if (tempfile.createNewFile())
			return tempfile;
		return null;
	}

	public boolean createTempDirectory(String directory) throws IOException {
		File temp = new File(tmp, directory);
		if (!temp.exists()) {
			if (!temp.mkdirs()) {
				System.err.println("Cannot create temporary directory [" + directory + "].");
				return false;
			}
		}
		else if (!temp.isDirectory()) {
			System.err.println("Cannot create temporary directory [" + directory + "].");
			return false;
		}
		return true;
	}

	public static class Downloader extends Thread {
		private static ConcurrentLinkedQueue<File> map = new ConcurrentLinkedQueue<File>();
		
		private File		dst;
		private InputStream	src;

		public Downloader(File dst, InputStream src) {
			super();
			this.dst = dst;
			this.src = src;
		}

		public void run() {
			if (map.contains(dst))
				return;
			map.add(dst);
			try {
				download(dst, src);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void download(File dest, InputStream file) throws IOException {
			FileOutputStream out = new FileOutputStream(dest);
			byte[] buffer = new byte[chunk];
			int read;
			while ((read = file.read(buffer)) != -1)
				out.write(buffer, 0, read);
			out.flush();
			file.close();
			out.close();
			map.remove(dst);
		}

	}

}
