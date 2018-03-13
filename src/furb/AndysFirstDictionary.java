package furb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AndysFirstDictionary {

	private static final Pattern UNWANTED_SYMBOLS = Pattern.compile("[^a-zA-Z0-9]");

	/**
	 * Creates a dictionary (Set<String>) to add to all the words.
	 * 
	 * @param lines
	 * @return
	 */
	private Set<String> createDictionary(String[] lines) {
		Set<String> dictionary = new TreeSet<>();
		Matcher unwantedMatcher = null;
		String[] words;
		String line = "";
		String word = "";
		for (int i = 0; i < lines.length; i++) {
			line = lines[i];
			if (line != null && !line.isEmpty()) {
				words = line.split("\\s+");
				for (int j = 0; j < words.length; j++) {
					word = words[j];
					if (word != null && !word.isEmpty()) {
						unwantedMatcher = UNWANTED_SYMBOLS.matcher(word);
						word = unwantedMatcher.replaceAll("");
						dictionary.add(word.trim().toLowerCase());
					}
				}
			}
		}

		return dictionary;
	}

	/**
	 * Checks if there is any text and, if so, prints the dictionary (Set<String>)
	 * of words.
	 * 
	 * @param text
	 */
	public void getWords(String[] text) {
		if (text == null) {
			throw new NullPointerException("There is no text so Andy can create his dictionary!");
		} else {
			Set<String> dictionary = this.createDictionary(text);
			if (!dictionary.isEmpty()) {
				Iterator<String> iterator = dictionary.iterator();
				while (iterator.hasNext()) {
					System.out.println(iterator.next());
				}
			} else {
				throw new RuntimeException("You gave Andy an empty text! Try again.");
			}
		}
	}

	/**
	 * Reads a given file.
	 * 
	 * @param file
	 * @return
	 */
	public static String[] readFile(File file) {

		if (file == null) {
			throw new NullPointerException("Please insert a file to Andy's First Dictionary!");
		}

		String[] text = null;
		try {
			byte[] arrayBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
			String content = new String(arrayBytes);
			text = content.split("\n");
		} catch (IOException e) {
			e.getStackTrace();
		}

		return text;

	}

}