package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    public Encode() {
    }

    public Encode(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void encodeFile() throws FileNotFoundException, IOException{

        fileStream = new FileInputStream(filename);
        byteStream = new byte[getStreamSize()];


        fileStream.read(byteStream);

        read(byteStream);

    }

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

        HuffmanTree huffTree = new HuffmanTree(nodeByteHashMap);
        huffTree.setByteArray(byteStream);
        huffTree.run();

    }

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
