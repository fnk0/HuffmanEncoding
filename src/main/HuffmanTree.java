package main;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.List;

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

    public HuffmanTree() {
    }

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

            //Debugging print outs.
            List<Integer> list = getBitString(bitString);
            System.out.println("Original String: " + bitString);
            System.out.println("Decoded String:  " + fromBitToByte(list));
            printBinaryTree(rootNode, 0);

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
        int counter = 0;

        while (binaryString.length() % 8 != 0) {
            binaryString = binaryString + 0;
            counter++;
        }

        byte[] rawBytes = binaryString.getBytes();
        return Twiddle.bytesToBits(rawBytes);
    }

    /**
     *
     * @param list
     * @return
     */
    public String fromBitToByte(List<Integer> list) {
        return new String(Twiddle.bitsToBytes(list));
    }

    /**
     *
     * @param byteArray
     */
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public String getBitString() {
        return bitString;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public List<Integer> getBinaryList() {
        return getBitString(bitString);
    }
}
