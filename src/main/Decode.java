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
        Node rootNode = null;
        int expectedChars = 0;
        byte[] byteArray = null;
        try {
            one = input.readObject();
            two = input.readObject();
            expectedChars = (Integer) one;
            rootNode = (Node) two;
           int counter = 0;
           byteArray = new byte[expectedChars];
           //System.out.println("Available: " + input.available());
           while (input.available() > 0) {
               byteArray[counter] = input.readByte();
               //System.out.print(byteArray[counter]);
               counter++;
           }
        } catch (ClassNotFoundException ex) {}

        huffTree = new HuffmanTree();
        List<Integer> byteList = Twiddle.bytesToBits(byteArray);

        /*
        // Debugging Statements
        System.out.println("\n Expected Chars: " + expectedChars);
        System.out.println("Byte Array");
        for(byte b : byteArray) {
            System.out.print(b);
        }
        System.out.println();

        System.out.println("Byte List");
        for(Integer i : byteList) {
            System.out.print(i);
        }
        */

        OutputStream out = new FileOutputStream("decoded_" + filename.replaceAll(".huff", ""));
        out.write(huffTree.decodeByteArray(expectedChars, rootNode, byteList).getBytes());
        out.flush();
        out.close();

    }
}
