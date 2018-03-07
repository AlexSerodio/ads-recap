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
        
        printNodes(list);
        
        int frequency;
        while (list.size() > 1) {
        	frequency = list.get(0).frequency + list.get(1).frequency;
            Node newNode = new Node('*', frequency, list.get(0), list.get(1));
            list.remove(0);
            list.remove(0);
            list.add(newNode);
            Collections.sort(list);
            printNodes(list);
        }
        return list.getFirst();
    }
    
    private static int countOccurences (char[] letters, char c) {
        int occurences = 0;
        int size = letters.length;
        for (int i = 0; i < size; i++)
            if (letters[i] == c)
                occurences++;
        
        return occurences;
    }
    
    private static void printNodes (LinkedList<Node> list) {
        System.out.println("+--------------------------+");
        for (Node n : list)
            System.out.println(n.letter + ": " + n.frequency);
    }
    
    public static void main (String[] args) {
        Node root = createTree ("Quero testar isso.");
    }

}
