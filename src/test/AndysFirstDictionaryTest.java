package test;

import java.util.Iterator;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import andys.AndysFirstDictionary;

public class AndysFirstDictionaryTest {

	private AndysFirstDictionary fd;

	@Test
	public void test04() {
		fd = new AndysFirstDictionary();
		String[] lines = { "vader", "\n", "no", "\t i", "am", "your", "father" };

		Set<String> dictionary = fd.getWords(lines);
		Iterator<String> iterator = dictionary.iterator();
		String s;
		int i = 0;
		String[] words = { "am", "father", "i", "no", "vader", "your" };
		while (iterator.hasNext()) {
			s = iterator.next();
			Assert.assertEquals(words[i].toLowerCase(), s);
			i++;
		}
	}

	@Test
	public void test03() {
		fd = new AndysFirstDictionary();
		String[] lines = { "vader", "no", "i", "am", "your", "father" };

		Set<String> dictionary = fd.getWords(lines);
		Iterator<String> iterator = dictionary.iterator();
		String s;
		int i = 0;
		String[] words = { "am", "father", "i", "no", "vader", "your" };
		while (iterator.hasNext()) {
			s = iterator.next();
			Assert.assertEquals(words[i].toLowerCase(), s);
			i++;
		}
	}

	@Test
	public void test02() {
		fd = new AndysFirstDictionary();
		String[] lines = { "luke", "he", "told", "me", "enough", "it", "was", "you", "who", "killed", "him" };

		Set<String> dictionary = fd.getWords(lines);
		Iterator<String> iterator = dictionary.iterator();
		String s;
		int i = 0;
		String[] words = { "enough", "he", "him", "it", "killed", "luke", "me", "told", "was", "who", "you" };
		while (iterator.hasNext()) {
			s = iterator.next();
			Assert.assertEquals(words[i].toLowerCase(), s);
			i++;
		}
	}

	@Test
	public void test01() {
		fd = new AndysFirstDictionary();
		String[] lines = { "vader", "if", "you", "only", "knew", "the", "power", "of", "the", "dark", "side", "Obi-Wan",
				"never", "told", "you", "what", "happened", "to", "your", "father" };

		Set<String> dictionary = fd.getWords(lines);
		Iterator<String> iterator = dictionary.iterator();
		String s;
		int i = 0;
		String[] words = { "dark", "father", "happened", "if", "knew", "never", "obi-wan", "of", "only", "power",
				"side", "the", "to", "told", "vader", "what", "you", "your" };
		while (iterator.hasNext()) {
			s = iterator.next();
			Assert.assertEquals(words[i].toLowerCase(), s);
			i++;
		}
	}

	@Test
	public void test00() {

		fd = new AndysFirstDictionary();
		String[] lines = { "LUKE", "I", "will", "never", "join", "you" };

		Set<String> dictionary = fd.getWords(lines);
		Iterator<String> iterator = dictionary.iterator();
		String s;
		int i = 0;
		String[] words = { "I", "join", "luke", "never", "will", "you" };
		while (iterator.hasNext()) {
			s = iterator.next();
			Assert.assertEquals(words[i].toLowerCase(), s);
			i++;
		}

	}

}
