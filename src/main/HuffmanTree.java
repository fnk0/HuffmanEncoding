package main;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class HuffmanTree implements Runnable, Serializable{

    PriorityQueue<Node> priorityQueue;
    HashMap<Byte, Node> byteNodeHashMap;

    public HuffmanTree(HashMap<Byte, Node> byteNodeHashMap) {
        this.byteNodeHashMap = byteNodeHashMap;
        priorityQueue = new PriorityQueue<Node>();
        Set bytes = byteNodeHashMap.keySet();
        Iterator i = bytes.iterator();

        while(i.hasNext()) {
            Byte key = (Byte) i.next();
            priorityQueue.add(byteNodeHashMap.get(key));
        }
    }

    public PriorityQueue<Node> getPriorityQueue() {
        return priorityQueue;
    }

    public void setPriorityQueue(PriorityQueue<Node> priorityQueue) {
        this.priorityQueue = priorityQueue;
    }

    @Override
    public void run() {
        while (priorityQueue.size() > 1) {
            Node newNode;
            Node leftNode = priorityQueue.poll();

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
        Node node = priorityQueue.remove();
        node.setParentNode(null);
        node = createTree(node);

        printBinaryTree(node, 0);
    }

    /**
     *
     * @param node
     * @return
     */
    public Node createTree(Node node) {
        //setNodeParents(node);
        return node;
    }

    /*
    public void setNodeParents(Node node) {
        if(node.getZeroNode() != null) {
            node.getZeroNode().setParentNode(node);
            //System.out.print("Node: " + node + " -> Parent: " + node.getParentNode() + "\n");
            setNodeParents(node.getZeroNode());
        }
        if(node.getOneNode() != null) {
            node.getOneNode().setParentNode(node);
            //System.out.print("Node: " + node + " -> Parent: " + node.getParentNode() + "\n");
            setNodeParents(node.getOneNode());
        }
    }
    */

    /**
     * Recursively prints the binary tree for debugging purposes
     * @param root
     *              the root tree
     * @param level
     *              the current level in the tree
     */
    public void printBinaryTree(Node root, int level){
        if(root==null)
            return;
        printBinaryTree(root.getOneNode(), level+1);
        if(level!=0){
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
            System.out.println("|-------" + root.getFrequency() + " V: [" + root.getValue() + "]");
        }
        else
            System.out.println(root.getFrequency());
        printBinaryTree(root.getZeroNode(), level+1);
    }

    public String createBinaryString(Node root, int level) {
        String binaryString = "";
        if(root==null) {
            return binaryString;
        }
        createBinaryString(root.getOneNode(), level+1);
        if(level!=0) {
            for(int i=0;i<level-1;i++)
                System.out.print("|\t");
            System.out.println("|-------" + root.getFrequency() + " V: [" + root.getValue() + "]");
        }  else {
            System.out.println(root.getFrequency());
        }
        createBinaryString(root.getZeroNode(), level+1);

        return binaryString;
    }

}
