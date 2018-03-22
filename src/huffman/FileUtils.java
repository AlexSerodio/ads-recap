package huffman;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

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

	private static String createDirectory(String path, String separator) {
		File directory = new File(path + separator + "compressedFiles");
		if (!directory.exists()) {
			directory.mkdir();
		}

		return directory.getAbsolutePath();
	}

	public static String getTestFilesPath() {
		return CURRENTDIR_PATH + SEPARATOR + "testfiles";
	}

	private static File makeFile(String fileName) {
		String path = createDirectory(CURRENTDIR_PATH, SEPARATOR);
		return new File(path + SEPARATOR + "encodedFile-" + fileName + ".txt");
	}
	
	public static boolean createBinaryFile(String encodeMessage, String printTree, String fileName) {
		byte[] bytes = stringToByte(encodeMessage);
		try {
			FileOutputStream fos = new FileOutputStream(/*makeFile(fileName)*/new File(fileName));
			fos.write(bytes);
			fos.write('\n');
			fos.write(printTree.getBytes());
			fos.close();
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		}
		return true;
	}
	
	public static String[] readBinaryFile(String fileName) {
		String[] array = null;
		try {
			byte[] arrayBytes = Files.readAllBytes(Paths.get(new File(fileName).getAbsolutePath()));
			String content = new String(arrayBytes);
			array = content.split("\n");
			array[0] = byteToString(array[0].getBytes());
		} catch (IOException e) {
			e.getStackTrace();
		}
		return array;
	}

	public static byte[] stringToByte (String code) {
		byte[] values = new byte[code.length()];
		int index = 1;
		byte bit = 0;
		int i;
		for (i = 0; i < code.length(); i++, bit++) {			
			if ((bit == 8) && (i != 0)) {
				index++;
				bit = 0;
			}
			if (code.charAt(i) == '1')
				values[index-1] += (byte)Math.pow(2, bit);
		}
		byte[] result = Arrays.copyOfRange(values, 0, index+1);
		result[index] = (byte) (8 - bit);
		return result;
	}
	
	public static String byteToString (byte[] code) {
		String result = "";
		for (int i = 0; i < code.length-1; i++) {
			String temp = String.format("%8s", Integer.toBinaryString(code[i] & 0xFF)).replace(' ', '0');
			result += new StringBuilder(temp).reverse().toString();
		}
		int end = result.length() - code[code.length-1];
		return result.substring(0, end);
	}
}
