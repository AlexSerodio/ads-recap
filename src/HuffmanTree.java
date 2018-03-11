import java.util.Collections;
import java.util.LinkedList;

/**
*
* @author Nemo
*/
class Node implements Comparable<Node> {

    public char letter;
    public int frequency;
    public Node left;
    public Node right;
    public String code;
    
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

/**
 *
 * @author Nemo
 */
public class HuffmanTree {

    public static Node createTree (String message) {

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
        printList(list);
        Collections.sort(list);
        printList(list);
        
        while (list.size() > 1) {
        	int frequency = list.get(0).frequency + list.get(1).frequency;
            Node newNode = new Node('*', frequency, list.get(0), list.get(1));
            list.removeFirst();
            list.removeFirst();
            list.add(newNode);
            Collections.sort(list);
            printList(list);
        }
        return list.getFirst();
    }
    
    public static void encoder (Node node, String code) {
    	if (node == null)
    		return;
    	
    	if (node.letter != '*')
    		node.code = code;
    	
    	encoder(node.left, code + "0");
    	encoder(node.right, code + "1");
    }
    
    public static void main (String[] args) {
        Node root = createTree ("testando a arvore de huffman.");
        System.out.println(printTree(root));
        encoder(root, "");
        System.out.println(printMessage(root));
    }
    
    /* ----------------------------------------------------------- */
    
    private static int countOccurences (char[] letters, char c) {
        int occurences = 0;
        int size = letters.length;
        for (int i = 0; i < size; i++)
            if (letters[i] == c)
                occurences++;
        
        return occurences;
    }
    
    private static String printTree(Node node) {
		if(node == null)
			return "<>";
		return "<" + node.letter + ":" +  node.frequency
			+ printTree(node.left) 
			+ printTree(node.right)
			+ ">";
	}

    private static void printList (LinkedList<Node> list) {
        System.out.println("+--------------------------+");
        for (Node n : list)
            System.out.println(n.letter + ": " + n.frequency);
    }

    private static String printMessage (Node node) {
    	if(node == null)
    		return "";
    	else if (node.letter == '*')
    		return printMessage(node.left) + printMessage(node.right);
    	
    	return node.letter + ": " + node.code + " | " + 
    			printMessage(node.left) +
    			printMessage(node.right);
    }
}
