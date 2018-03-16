package furb;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
*
* @author Nemo
*/
class Node implements Comparable<Node> {

    public char letter;
    public int frequency;
    public Node left;
    public Node right;
    
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
    
    public static String encodeMessage (Node node, String message) {
    	Map<Character, String> map = new HashMap<>();
    	
    	if ((node.left == null) && (node.right == null))
    		map.put(node.letter, "0");
    	else
    		encode(node, "", map);
    	
    	String code = "";
        for (int i = 0 ; i < message.length(); i++)
            code += map.get(message.charAt(i));
        
    	return code;
    }
    
    private static void encode (Node node, String code, Map<Character, String> map) {
    	if (node == null)
    		return;
    	
    	if ((node.left == null) && (node.right == null))
    		map.put(node.letter, code);
    	
    	encode(node.left, code + "0", map);
    	encode(node.right, code + "1", map);
    }
    
    public static StringBuilder decodeMessage (Node root, String code) {
    	StringBuilder text = new StringBuilder();
    	
    	if (code.length() == 1) {
    		text.append(root.letter);
    		return text;
    	}
    	
    	int index = -1;
    	while (index < code.length()-1)
    		index = decode(root, index, code, text);
    	
    	return text;
    }
    
    private static int decode (Node root, int index, String code, StringBuilder text) {
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
    
    public static void main (String[] args) {
        String message = "test.";
        System.out.println("Original message: " + message);
        
    	Node root = createTree (message);
        System.out.println("Tree: " + printTree(root));
        
        String code = encodeMessage(root, message);
        System.out.println("Encoded message: " + code);
        
        StringBuilder result = decodeMessage(root, code);
        System.out.println("Decoded message: " + result);
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
}
