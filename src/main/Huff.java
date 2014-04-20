package main;

import java.io.IOException;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class Huff {

    public static void main(String[] args) {

        Encode encode = null;
        String filename = "";

        if(args.length == 1) {
            filename = args[0];
            encode = new Encode(filename);
        } else {
            System.out.println("Please provide a file to encode.");
            System.out.println("Usage: Huff <fileToEncode>");
            System.exit(10);
        }

        try {
            encode.encodeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
