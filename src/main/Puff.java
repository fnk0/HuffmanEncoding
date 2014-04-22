package main;


import java.io.IOException;

/**
 * Created by marcus on 4/19/14.
 * @version 1.0
 * @author Marcus Gabilheri
 * @since April, 2014
 */

public class Puff {

    public static void main(String[] args) {
        Decode decode = null;
        String filename = "";

        if(args.length == 1) {
            filename = args[0];
            if(filename.contains(".huff")) {
                System.out.println("Decoding... " + filename);
                decode = new Decode(filename);
            } else {
                exitProgram();
            }
        } else {
           exitProgram();
        }
        try {
            if(decode != null) {
                decode.decodeFile();
            } else {
                exitProgram();
            }
        } catch (IOException e) {
            System.out.println("File Not found! Please specify a correct file to decode.");
        }
    }

    public static void exitProgram() {
        System.out.println("Please provide a file to Decode with a .huff extension.");
        System.out.println("Usage: Huff <fileToDecode>");
        System.exit(10);
    }
}
