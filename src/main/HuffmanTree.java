package main;


import java.io.Serializable;
import java.util.*;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class HuffmanTree implements Runnable, Serializable{

    private PriorityQueue<Node> priorityQueue;
    private HashMap<Byte, Node> byteNodeHashMap;
    private Node rootNode;
    private byte[] byteArray;
    private String bitString;
    private int decodeCounter = 0;

    public HuffmanTree() {}

    /**
     * Constructor of the Huffman Tree
     * @param byteNodeHashMap
     *                      The HashMap containing the frequency for each node.
     */
    public HuffmanTree(HashMap<Byte, Node> byteNodeHashMap) {
        this.byteNodeHashMap = byteNodeHashMap;
        priorityQueue = new PriorityQueue<Node>();
        Set bytes = this.byteNodeHashMap.keySet();
        Iterator i = bytes.iterator();

        while(i.hasNext()) {
            Byte key = (Byte) i.next();
            priorityQueue.add(byteNodeHashMap.get(key));
        }
    }

    /**
     * Executes the Huffman Tree method.
     */
    @Override
    public void run() {
        while (priorityQueue.size() > 1) {
            Node newNode;
            Node leftNode = priorityQueue.remove();

            if(priorityQueue.size() > 0) {
                Node rightNode = priorityQueue.remove();
                newNode = new Node(leftNode.getFrequency() + rightNode.getFrequency());
                newNode.setZeroNode(leftNode);
                newNode.setOneNode(rightNode);
                leftNode.setNodeValue(0);
                rightNode.setNodeValue(1);
                rightNode.setParentNode(newNode);
                leftNode.setParentNode(newNode);
            } else {
                newNode = new Node(leftNode.getFrequency());
                newNode.setZeroNode(leftNode);
                newNode.setOneNode(null);
                newNode.setParentNode(newNode);
                leftNode.setNodeValue(0);
            }
            priorityQueue.add(newNode);
        }
        rootNode = priorityQueue.remove();
        rootNode.setParentNode(null);
        bitString = "";
        for(byte b : byteArray) {
            Node node = byteNodeHashMap.get(b);
            bitString += getBinaryString(node, "");
        }
    }

    /**
     * Recursively prints the binary tree using an in order traversal algorithm. Used for debugging purposes
     * @param root
     *              the root tree
     * @param level
     *              the current level in the tree
     */
    public void printBinaryTree(Node root, int level){
        if(root == null) {
            return;
        }
        printBinaryTree(root.getOneNode(), level + 1);
        if(level != 0) {
            for(int i = 0; i < level - 1;i++) {
                System.out.print("|\t");
            }
            System.out.println("|-------" + root.getFrequency() + " V: [" + root.getValue() + "]  -- Child Value: " + root.getNodeValue());
        } else {
            System.out.println(root.getFrequency());
        }
        printBinaryTree(root.getZeroNode(), level+1);
    }

    /**
     * Method to get the binary String of each leaf.
     * @param node The current node to be executed
     * @param bitString
     *                  The string to start with is always an empty string
     *                  Each recursive calls apends to the new 0 or 1 to the beginning.
     * @return
     *        Returns the final 0-1 string for that node.
     */
    public String getBinaryString(Node node, String bitString) {
        if(node.getParentNode() == null) {
            return bitString;
        } else if(node.getNodeValue() == 1) {
            bitString = 1 + bitString;
            return getBinaryString(node.getParentNode(), bitString);
        } else if(node.getNodeValue() == 0) {
            bitString = 0 + bitString;
            return getBinaryString(node.getParentNode(), bitString);
        } else {
            return bitString;
        }
    }

    /**
     * Method to convert the 0 - 1 string to a List.
     * @param binaryString
     *                    A String full of 0s and 1s
     * @return
     *          a List<Integer> of 0s and 1s
     */
    public List<Integer> getBitString(String binaryString) {
        while (binaryString.length() % 8 != 0) {
            binaryString += 0;
        }
        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < binaryString.length(); i++) {
            list.add(Integer.parseInt("" + binaryString.charAt(i)));
        }
        return list;
    }

    /**
     * Sets the byteArray
     * @param byteArray
     *                  An byteArray
     */
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    /**
     * Method to get the rootNode of a the three
     * @return
     *         The root node of this three
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     *
     * @return
     */
    public List<Integer> getBinaryList() {
        return getBitString(bitString);
    }

    /**
     * Decodes the byteArray
     * @param expectChars
     *                  Number of total chars in this tree
     * @param rootNode
     *                  The rootNode of this tree
     * @param bits
     *                  The List<Integer> of bits.
     * @return
     *
     */
    public byte[] decodeByteArray(int expectChars, Node rootNode, List<Integer> bits) {
        byte[] decodedFile = new byte[expectChars];
        for(int i = 0; i < expectChars; i++) {
           decodedFile[i] = getNodeValue(rootNode, bits, decodeCounter);
        }
        return decodedFile;
    }

    /**
     * Gets the value of a specific node. For decoding
     * @param node
     *             The node to be decoded
     * @param bits
     *              List<Integer> of bits
     * @param position
     *              Current position in the list.
     * @return
     *        the last byte of that branch.
     */
    public byte getNodeValue(Node node, List<Integer> bits, int position) {

        if(node.getZeroNode() == null && node.getOneNode() == null) {
            return node.getValue();
        } else if(bits.get(position) == 0) {
            decodeCounter++;
            return getNodeValue(node.getZeroNode(), bits, decodeCounter);
        } else if(bits.get(position) == 1) {
            decodeCounter++;
            return getNodeValue(node.getOneNode(), bits, decodeCounter);
        } else {
            return node.getValue();
        }
     }
}
