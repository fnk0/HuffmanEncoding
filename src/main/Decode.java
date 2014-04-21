package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class Decode {

    private String filename;
    private FileInputStream fileStream = null;
    private ObjectInputStream input;
    private int streamSize;
    private byte[] byteStream;
    private ArrayList<Node> nodes;
    private HashMap<Byte, Node> nodeByteHashMap;
    private HuffmanTree huffTree;

    public Decode() {
    }

    public Decode(String filename) {
        this.filename = filename;
    }

    @SuppressWarnings("unchecked")
    public void decodeFile() throws FileNotFoundException, IOException {
        fileStream = new FileInputStream(filename);
        byteStream = new byte[fileStream.available()];
        input = new ObjectInputStream(fileStream);
        /*
            Read Sequence:
            1: Numbers of Characters (Integer)
            2: Huffman Tree (Node)
            3: Bit List (List<Integer>)
            4: Byte, Node - HashMap (HashMap<Byte, Node>);
         */
        Object one = null;
        Object two = null;
        Object three = null;
        //Object four = null;
        Node rootNode = null;
        int expectedChars = 0;
        List<Integer> bitList = null;
        try {
            one = input.readObject();
            two = input.readObject();
            three = input.readObject();
            //four = input.readObject();
            expectedChars = (Integer) one;
            rootNode = (Node) two;
            bitList = (List<Integer>) three;
        } catch (ClassNotFoundException ex) {

        }

        HuffmanTree tree = new HuffmanTree();
        tree.printBinaryTree(rootNode, 0);

        System.out.println(expectedChars);
        System.out.println(bitList);
    }
}
