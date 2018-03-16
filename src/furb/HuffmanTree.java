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
    
    public static void encodeMessage (Node node, String code, Map<Character, String> map) {
    	if (node == null)
    		return;
    	
    	if (node.letter != '*') {
    		node.code = code;
    		map.put(node.letter, node.code);
    	}
    	
    	encodeMessage(node.left, code + "0", map);
    	encodeMessage(node.right, code + "1", map);
    }
    
    public static StringBuilder decodeMessage (Node root, String code) {
    	StringBuilder text = new StringBuilder();
    	int index = -1;
    	while (index < code.length()-2) {
    		index = decode(root, index, code, text);
    	}
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
        String message = "testando a arvore de huffman.";
    	Node root = createTree (message);
        System.out.println(printTree(root));
        
        Map<Character, String> map = new HashMap<>();
        encodeMessage(root, "", map);
        //System.out.println(printCode(root));
        
        String code = "";
        for (int i = 0 ; i < message.length(); i++) {
            code += map.get(message.charAt(i));
        }

        System.out.println(code);
        
        StringBuilder result = decodeMessage(root, code);
        System.out.println(result);
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

    private static String printCode (Node node) {
    	if(node == null)
    		return "";
    	else if (node.letter == '*')
    		return printCode(node.left) + printCode(node.right);
    	
    	return node.letter + ": " + node.code + " | " + 
    			printCode(node.left) +
    			printCode(node.right);
    }
    
    private static String printCode2 (Node node) {
    	if(node == null)
    		return "";
    	else if (node.letter == '*')
    		return printCode2(node.left) + printCode2(node.right);
    	
    	return node.code + 
    			printCode2(node.left) +
    			printCode2(node.right);
    }
    
    private static String printMessage (Node node) {
    	if(node == null)
    		return "";
    	else if (node.letter == '*')
    		return printMessage(node.left) + printMessage(node.right);
    	
    	return node.letter + 
    			printMessage(node.left) +
    			printMessage(node.right);
    }
}
