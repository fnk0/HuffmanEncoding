package main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class Encode {

    private String filename;
    private FileInputStream fileStream = null;
    private int streamSize;
    private byte[] byteStream;
    private ArrayList<Node> nodes;
    private HashMap<Byte, Node> nodeByteHashMap;
    private HuffmanTree huffTree;

    /**
     *
     */
    public Encode() {}

    /**
     *
     * @param filename
     */
    public Encode(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void encodeFile() throws FileNotFoundException, IOException {
        fileStream = new FileInputStream(filename);
        streamSize = getStreamSize();
        Timer timer = new Timer();
        System.out.println("Starting Huffman Encoding on file... " + filename);
        timer.start();

        byteStream = new byte[streamSize];
        fileStream.read(byteStream);
        read(byteStream);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename + ".huff"));
        /*
            Write Sequence:
            1: Numbers of Characters
            2: Huffman Tree
            3: Byte Array
         */
        out.writeObject(streamSize);
        out.writeObject(huffTree.getRootNode());
        byte[] toWriteArray = Twiddle.bitsToBytes(huffTree.getBinaryList());
        timer.stop();
        System.out.println("Encoded Successfully finished in: " + (timer.getRunTime() / 1000) + " seconds.");
        System.out.println("Writing to disk...  " + filename + ".huff");
        out.write(toWriteArray);
        out.flush();
        out.close();
        System.out.println("Done writing. New file created");
        File newFile = new File(filename + ".huff");
        System.out.println("Original File Size: " + (streamSize / 1000)  + " Kb.");
        System.out.println("New File Size: " + (newFile.length() / 1000) + " Kb.");
    }

    /**
     *
     * @param bytes
     */
    public void read(byte[] bytes) {
        ArrayList<Byte> bytesRead = new ArrayList<Byte>();
        nodeByteHashMap = new HashMap<Byte, Node>();
        nodes = new ArrayList<Node>();
        int counter = 0;
        for(int i = 0; i < bytes.length; i++) {
            if(!bytesRead.contains(bytes[i])) {
                byte b = bytes[i];

                for(int j = i; j < bytes.length; j++) {
                    if(b == bytes[j]) counter++;
                }
                bytesRead.add(b);
                nodeByteHashMap.put(b, (new Node(counter, b)));
                counter = 0;
            }
        }
        huffTree = new HuffmanTree(nodeByteHashMap);
        huffTree.setByteArray(byteStream);
        huffTree.run();
    }

    /**
     *
     * @return
     */
    public int getStreamSize() {
        if(fileStream != null) {
            try {
                return fileStream.available();
            } catch (IOException e) {
               return 0;
            }
        } else {
            return 0;
        }
    }
}
