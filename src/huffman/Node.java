package huffman;

public class Node implements Comparable<Node> {

	public char letter;
	public int frequency;
	public Node left;
	public Node right;

	public Node() {}

	public Node(char letter, int frequency) {
		this.letter = letter;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}

	public Node(char letter, int frequency, Node left, Node right) {
		this.letter = letter;
		this.frequency = frequency;
		this.left = left;
		this.right = right;
	}

	@Override
	public int compareTo(Node node) {
		return this.frequency - node.frequency;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Node other = (Node) obj;
		return letter == other.letter;
	}
}
