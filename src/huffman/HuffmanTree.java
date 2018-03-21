package huffman;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

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

	public static void main(String[] args) {
		HuffmanTree huffman = new HuffmanTree();

		File directory = new File(FileUtils.getTestFilesPath());

		String[] lines = null;
		String line = null;
		Node root = null;
		String encoded;
		String createdTree;
		StringBuilder sb = new StringBuilder("+--------------------------------------+" + "\n");
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				lines = FileUtils.readFile(file);
				for (int i = 0; i < lines.length; i++) {
					line = lines[i];
					if (line != null && !line.isEmpty()) {
						String name = file.getName();
						sb.append("Current file: " + name + "\n");
						sb.append("File size: " + file.length() + "bytes" + "\n");
						sb.append("Begin: "
								+ new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()) + "\n");
						root = huffman.createTree(line);
						createdTree = huffman.printTree(root);
						encoded = huffman.encodeMessage(root, line);
						sb.append("End: "
								+ new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()) + "\n");
						// FileUtils.createFile(encoded, createdTree, name);
					}

				}
			}

			sb.append("+--------------------------------------+" + "\n");
		}

		System.out.println(sb.toString());

		// StringBuilder decoded = huffman.decodeMessage(root, encoded);
		// Node node = huffman.recreateTree(createdTree);
		// String recreatedTree = huffman.printTree(node);

		// System.out.println("Original message: " + message);
		// System.out.println("+--------------------------------------+");
		// System.out.println("Created Tree: " + createdTree);
		// System.out.println("+--------------------------------------+");
		// System.out.println("Encoded message: " + encoded);
		// System.out.println("+--------------------------------------+");
		// System.out.println("Decoded message: " + decoded.toString());
		// System.out.println("+--------------------------------------+");
		// System.out.println("Recreated Tree: " + recreatedTree);
		// System.out.println("+--------------------------------------+");
		// System.out.println("Created and recreated tree are equal: " +
		// createdTree.equals(recreatedTree));
		// System.out.println("+--------------------------------------+");
		// System.out.println("Original message and decoded message are equal: " +
		// message.equals(decoded.toString()));
	}
}
