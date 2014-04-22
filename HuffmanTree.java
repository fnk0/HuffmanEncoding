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
    private List<Integer> binaryList;
    private int decodeCounter = 0;

    public HuffmanTree() {}

    /**
     *
     * @param byteNodeHashMap
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
     *
     * @return
     */
    public PriorityQueue<Node> getPriorityQueue() {
        return priorityQueue;
    }

    /**
     *
     * @param priorityQueue
     */
    public void setPriorityQueue(PriorityQueue<Node> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    /**
     *
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
        /*
        // Debugging print outs
        System.out.println(bitString);
        System.out.println(getBitString(bitString));
        for(byte b : Twiddle.bitsToBytes(getBitString(bitString))) {
            System.out.print(b + " ");
        }

        System.out.print(" \n" + Twiddle.bytesToBits(Twiddle.bitsToBytes(getBitString(bitString))));
        printBinaryTree(rootNode, 0);
        */
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
     *
     * @param node
     * @param bitString
     * @return
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
     *
     * @param binaryString
     * @return
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
     *
     * @param byteArray
     */
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    /**
     *
     * @return
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
     *
     * @param expectChars
     * @param rootNode
     * @param bits
     * @return
     */
    public String decodeByteArray(int expectChars, Node rootNode, List<Integer> bits) {
        String decodedString = "";
        for(int i = 0; i < expectChars; i++) {
           char c = (char) getNodeValue(rootNode, bits, decodeCounter);
           decodedString += c;
           //System.out.print(c);
        }
        //System.out.println(decodedString);
        return decodedString;
    }

    /**
     *
     * @param node
     * @param bits
     * @param position
     * @return
     */
    public byte getNodeValue(Node node, List<Integer> bits, int position) {

        if(node.getValue() != 0) {
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
