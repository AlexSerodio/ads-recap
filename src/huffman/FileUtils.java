package huffman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

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
			writer = new FileWriter(new File("encodedFile-" + fileName + ".txt"));
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

}
