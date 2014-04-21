package main;

import java.io.Serializable;

/**
 * Created by marcus on 4/19/14.
 * @author Marcus Gabilheri
 * @since April, 2014
 * @version 1.0
 */

public class Node implements Comparable<Node>, Serializable {

    private Node parentNode;
    private Node zeroNode;
    private Node oneNode;
    private int frequency, nodeValue;
    private byte value;

    public Node() {
        this.frequency = 0;
        this.nodeValue = 0;
    }

    public Node(int frequency) {
        this.frequency = frequency;
    }

    public Node(int frequency, byte value) {
        this.frequency = frequency;
        this.value = value;
    }

    @Override
    public int compareTo(Node node) {
        if(node.getFrequency() == this.getFrequency()) {
            return 0;
        } else if(node.getFrequency() < this.getFrequency()) {
            return 1;
        } else {
            return -1;
        }
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getZeroNode() {
        return zeroNode;
    }

    public void setZeroNode(Node zeroNode) {
        this.zeroNode = zeroNode;
    }

    public Node getOneNode() {
        return oneNode;
    }

    public void setOneNode(Node oneNode) {
        this.oneNode = oneNode;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(int nodeValue) {
        this.nodeValue = nodeValue;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public boolean hasZeroNode() {
        return !(zeroNode == null);
    }

    public boolean hasOneNode() {
        return !(oneNode == null);
    }

    public boolean isLeafNode() {
        return getValue() == 0;
    }

    @Override
    public String toString() {
        return "Byte Node: " + getNodeValue() + " Frequency: " + getFrequency();
    }
}
