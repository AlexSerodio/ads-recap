package huffman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

	private static final String SEPARATOR = FileSystems.getDefault().getSeparator();
	private static final String CURRENTDIR_PATH = System.getProperty("user.dir");

	/**
	 * Creates file with encoded text based on Huffman coding.
	 * 
	 * @param encodeMessage
	 * @param printTree
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(String encodeMessage, String printTree, String fileName) {

		FileWriter writer;
		try {
			writer = new FileWriter(makeFile(fileName));
			writer.write(encodeMessage + "\n" + printTree);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Reads a given file.
	 * 
	 * @param file
	 * @return
	 */
	public static String[] readFile(File file) {
		String[] array = null;
		try {
			byte[] arrayBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			String content = new String(arrayBytes);
			array = content.split("\n");
		} catch (IOException e) {
			e.getStackTrace();
		}

		return array;
	}

	public static String readAllFile(File file) {
		String s = null;
		try {
			byte[] arrayBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			s = new String(arrayBytes);
		} catch (IOException e) {
			e.getStackTrace();
		}

		return s;
	}

	private static String createDirectory(String path, String separator) {
		File directory = new File(path + separator + "compressedFiles");
		if (!directory.exists()) {
			directory.mkdir();
		}

		return directory.getAbsolutePath();
	}

	public static String getEncodedFilesPath() {
		return CURRENTDIR_PATH + SEPARATOR + "compressedFiles";
	}

	public static String getTestFilesPath() {
		return CURRENTDIR_PATH + SEPARATOR + "testfiles";
	}

	private static File makeFile(String fileName) {
		String path = createDirectory(CURRENTDIR_PATH, SEPARATOR);
		return new File(path + SEPARATOR + fileName);
	}

}
