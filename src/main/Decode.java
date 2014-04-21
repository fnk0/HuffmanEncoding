package main;

import java.io.*;
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
            3: Byte array.
         */
        Object one;
        Object two;
        Object three = null;

        Node rootNode = null;
        int expectedChars = 0;
        try {
            one = input.readObject();
            two = input.readObject();
            three = input.readObject();
            expectedChars = (Integer) one;
            rootNode = (Node) two;

        } catch (ClassNotFoundException ex) {

        } catch (OptionalDataException ex) {

        }

        HuffmanTree tree = new HuffmanTree();
        tree.printBinaryTree(rootNode, 0);
        byte[] byteArray = (byte[]) three;

        System.out.println(expectedChars);

        List<Integer> byteList = Twiddle.bytesToBits(byteArray);

        for(int i : byteList) {
            System.out.print(i);
        }

    }
}
