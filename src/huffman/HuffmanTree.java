package huffman;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class HuffmanTree {

	public Node createTree(String message) {

		char[] letters = message.toCharArray();

		LinkedList<Node> list = new LinkedList<>();
		Node node;
		for (int i = 0; i < letters.length; i++) {
			node = new Node(letters[i], 0);
			if (!list.contains(node)) {
				node.frequency = countOccurences(letters, letters[i]);
				list.add(node);
			}
		}
		Collections.sort(list);

		while (list.size() > 1) {
			int frequency = list.get(0).frequency + list.get(1).frequency;
			Node newNode = new Node('*', frequency, list.get(0), list.get(1));
			list.removeFirst();
			list.removeFirst();
			list.add(newNode);
			Collections.sort(list);
		}
		return list.getFirst();
	}

	private int countOccurences(char[] letters, char c) {
		int occurences = 0;
		int size = letters.length;
		for (int i = 0; i < size; i++)
			if (letters[i] == c)
				occurences++;

		return occurences;
	}

	public String encodeMessage(Node node, String message) {
		Map<Character, String> map = new HashMap<>();

		if ((node.left == null) && (node.right == null))
			map.put(node.letter, "0");
		else
			encode(node, "", map);

		String code = "";
		for (int i = 0; i < message.length(); i++)
			code += map.get(message.charAt(i));

		return code;
	}

	private void encode(Node node, String code, Map<Character, String> map) {
		if (node == null)
			return;

		if ((node.left == null) && (node.right == null))
			map.put(node.letter, code);

		encode(node.left, code + "0", map);
		encode(node.right, code + "1", map);
	}

	public StringBuilder decodeMessage(Node root, String code) {
		StringBuilder text = new StringBuilder();

		if (code.length() == 1) {
			text.append(root.letter);
			return text;
		}

		int index = -1;
		while (index < code.length() - 1)
			index = decode(root, index, code, text);

		return text;
	}

	private int decode(Node root, int index, String code, StringBuilder text) {
		if (root == null)
			return index;

		if ((root.left == null) && (root.right == null)) {
			text.append(root.letter);
			return index;
		}

		index++;

		if (code.charAt(index) == '0')
			index = decode(root.left, index, code, text);
		else
			index = decode(root.right, index, code, text);

		return index;
	}

	public Node recreateTree(String tree) {
		char[] chars = tree.toCharArray();
		Stack<Character> stack = new Stack<>();

		for (int i = tree.length() - 1; i >= 0; i--)
			stack.push(chars[i]);

		Node root = new Node();
		return recreateTree(stack, root);
	}

	private Node recreateTree(Stack<Character> stack, Node node) {
		char symbol;
		symbol = stack.pop();

		if ((symbol == '<') && (stack.peek() == '>'))
			return node;

		if ((symbol == '<') || (symbol == '>'))
			return recreateTree(stack, node);

		node = new Node(symbol, -1);
		node.left = recreateTree(stack, node.left);
		node.right = recreateTree(stack, node.right);
		return node;
	}

	public String printTree(Node node) {
		if (node == null)
			return "<>";

		return "<" + node.letter + printTree(node.left) + printTree(node.right) + ">";
	}	
	
	private static void testFilesEncoding() {
		HuffmanTree huffman = new HuffmanTree();

		File directory = new File(FileUtils.getTestFilesPath());

		String content = null;
		Node root = null;
		Node node = null;
		String encoded;
		String createdTree;
		String recreatedTree;
		StringBuilder decoded;
		StringBuilder sb = new StringBuilder("+--------------------------------------+" + "\n");
		for (File file : directory.listFiles()) {
			if (file.isFile()) {

				content = FileUtils.readAllFile(file);
				if (content != null && !content.isEmpty()) {
					String name = file.getName();
					sb.append("Current file: " + name + "\n");
					sb.append("File size: " + file.length() + " bytes" + "\n");

					root = huffman.createTree(content);
					sb.append("Original text: " + content + "\n");

					createdTree = huffman.printTree(root);

					sb.append("Tree: " + createdTree + "\n");

					long start = System.currentTimeMillis();
					encoded = huffman.encodeMessage(root, content);
					sb.append("Encoded text: " + encoded + "\n");
					long end = System.currentTimeMillis();

					long millis = end - start;

					sb.append("Execution time: "
							+ String.format("%02d:%02d:%02d.%d", TimeUnit.MILLISECONDS.toHours(millis),
									TimeUnit.MILLISECONDS.toMinutes(millis)
											- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
									TimeUnit.MILLISECONDS.toSeconds(millis)
											- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
									TimeUnit.MILLISECONDS.toMillis(millis)
											- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)))
							+ "\n");

					FileUtils.createFile(encoded, createdTree, name);

					// decoded = huffman.decodeMessage(root, encoded);
					// sb.append("Decoded text: " + decoded.toString() + "\n");
					// node = huffman.recreateTree(createdTree);
					// recreatedTree = huffman.printTree(node);
					// sb.append("Recreated tree: " + recreatedTree + "\n");
					// sb.append("Are original and decoded equal: " +
					// content.equals(decoded.toString()) + "\n");
				}
			}

			sb.append("+--------------------------------------+" + "\n");
		}

		System.out.println(sb.toString());

	}

	private static void testFilesDecoded() {

		HuffmanTree huffman = new HuffmanTree();
		Node root = null;
		File directory = new File(FileUtils.getEncodedFilesPath());
		String[] lines;
		String line;
		StringBuilder decoded;
		StringBuilder sb = new StringBuilder("+--------------------------------------+" + "\n");
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				lines = FileUtils.readFile(file);
				line = lines[0];
				if (line != null && !line.isEmpty()) {
					String name = file.getName();
					sb.append("Current file: " + name + "\n");
					sb.append("File size: " + file.length() + " bytes" + "\n");

					root = huffman.recreateTree(lines[1]);
					long start = System.currentTimeMillis();
					decoded = huffman.decodeMessage(root, line);
					sb.append("Decoded text: " + decoded + "\n");
					long end = System.currentTimeMillis();

					long millis = end - start;

					sb.append("Execution time: "
							+ String.format("%02d:%02d:%02d.%d", TimeUnit.MILLISECONDS.toHours(millis),
									TimeUnit.MILLISECONDS.toMinutes(millis)
											- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
									TimeUnit.MILLISECONDS.toSeconds(millis)
											- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)),
									TimeUnit.MILLISECONDS.toMillis(millis)
											- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis)))
							+ "\n");

				}
			}

			sb.append("+--------------------------------------+" + "\n");
		}

		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		//testFilesEncoding();
		binary();
	}
	
	private static void binary () {
		HuffmanTree huffman = new HuffmanTree();
	
		// creates tree and encode message
		String message = "testing the huffman tree.";
		Node no = huffman.createTree(message);
		String encoded = huffman.encodeMessage(no, message);
		String originalTree = huffman.printTree(no);
		
		System.out.println("Original message: " + message);
		System.out.println("Original tree: " + originalTree);
		System.out.println("Encoded message: " + encoded);		
		
		// save in binary file
		FileUtils.createBinaryFile(encoded, originalTree, "result.out");
		
		// read tree and encoded message from file
		String[] result = FileUtils.readBinaryFile("result.out");
		String newTree = result[1];
		String newCode = result[0];
		
		System.out.println("Recovered tree: " + newTree);
		System.out.println("Recovered code: " + newCode);
		
		// recreates tree from file and decode message
		Node newRoot = huffman.recreateTree(newTree);
		StringBuilder decoded = huffman.decodeMessage(newRoot, newCode);
		
		System.out.println("Recreated tree: " + huffman.printTree(newRoot));
		System.out.println("Decoded message: " + decoded.toString());
		
		System.out.println("Codes are equal: " + encoded.equals(newCode));
		System.out.println("Trees are equal: " + originalTree.equals(newTree));
		System.out.println("Messages are equal: " + message.equals(decoded.toString()));
		
		System.out.println("Done");
	}
}
