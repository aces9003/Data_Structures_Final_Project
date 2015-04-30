//class to interpret input file and alphabetize for

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.Charset;

/**
CodeSort main method, populates graph, then makes hashmap of sorted values.
After having sorted values in map, sort input file and output a sorted one.
*/
public final class CodeSort {

    /**
     * For checkstyle compliance only.
     */
    private CodeSort() {
    }

    /**
    Main class for CodeSort.
    @param args the files for input and output
    */
    public static void main(String[] args) {

        final int proper = 3;
        if (args.length < proper) {
            throw new IllegalArgumentException("Three arguments required.");
        }
        /*
        1. Read in file and make directed graph from strings
        2. Topological sort into hash map of sorted words
        3. Read in file of unsorted and make list of Strings
        4. Use map to order them and output to file, output file when done
        */

        /** ordered input file.*/
        String ordered = args[0];
        /** array to house ordered strings.*/
        ArrayList<String> arrayOrdered = new ArrayList<String>();
        /** disordered input file.*/
        String disordered = args[1];
        /** array to house disordered strings.*/
        ArrayList<String> arrayDisordered = new ArrayList<String>();
        /** output file.*/
        String out = args[2];
        /** graph.*/
        Graph graphs = new Graph();
        
        //populate graph
        Path path1 = Paths.get(ordered);
        try {
            //java 7 
            List<String> lines = 
                Files.readAllLines(path1, Charset.defaultCharset());
            for (String line : lines) {
                arrayOrdered.add(line);
            }
            /*
            java 8
            Stream<String> lines = Files.lines(path1);
            lines.forEach(s -> arrayOrdered.add(s));
            */
        } catch (IOException ex) {
            System.out.println("oops!");
        }

        ////////// 1. ////////////
        //first value is first character of first string, then loop until n-1
        //strating at 2nd value and compare to previous
        if (arrayOrdered.size() < 2) {
            //output error message, file too small
            System.out.println("Error: file has insufficient content.");
            return;
        }

        graphs = middleMain(arrayOrdered, graphs);
        /*
        for (int i = 1; i < arrayOrdered.size(); i++) {
            String temp1 = arrayOrdered.get(i - 1);
            String temp2 = arrayOrdered.get(i);
            int numChars = 0;
            if (temp1.length() < temp2.length()) {
                numChars = temp1.length();
            } else {
                numChars = temp2.length();
            }
            for (int j = 0; j < numChars; j++) {
                Character char1 = temp1.charAt(j);
                Character char2 = temp2.charAt(j);
                if (char1.compareTo(char2) != 0) {
                    //System.out.println("source: " + char1);
                    //System.out.println("dest: " + char2);
                    graphs.addEdge(char1, char2);
                    break;
                } 
            }
        } 
        */
        //System.out.println("Done with 1.");
        ////////// 2. ////////////
        HashMap<Character, ArrayList<Character>> dict = 
            graphs.topologicalSort();
        //System.out.println("Done with 2.");

        ////////// 3. ////////////
        //populate unordered list
        Path path2 = Paths.get(disordered);
        try {
            //java 7 
            List<String> lines = 
                Files.readAllLines(path2, Charset.defaultCharset());
            for (String line : lines) {
                arrayDisordered.add(line);
            }
            /*
            Stream<String> lines = Files.lines(path2);
            lines.forEach(st -> arrayDisordered.add(st));
            */
        } catch (IOException ex) {
            System.out.println("oops!");
        }
        //System.out.println("Done with 3.");

        ////////// 4. ////////////
        //graphs.printTest(dict);

        ArrayList<String> orderedDisorder = 
            sortDisordered(arrayDisordered, dict);

        //System.out.println("ordered disrodered!");

        //input list into file and save
        try {
            FileWriter fw = new FileWriter(out);
            for (int x = orderedDisorder.size() - 1; x >= 0; x--) {
                fw.write(orderedDisorder.get(x) + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("oops!");
        }
        System.out.println("Done! Check output file in working directory.");
    }

    /**
    main mehtod extension to stop checkstyle.
    @param arrayOrdered is array.
    @param graphs is graph.
    @return Graph is updated graph.
    */
    private static Graph middleMain(ArrayList<String> arrayOrdered, 
        Graph graphs) {
        for (int i = 1; i < arrayOrdered.size(); i++) {
            String temp1 = arrayOrdered.get(i - 1);
            String temp2 = arrayOrdered.get(i);
            int numChars = 0;
            if (temp1.length() < temp2.length()) {
                numChars = temp1.length();
            } else {
                numChars = temp2.length();
            }
            for (int j = 0; j < numChars; j++) {
                Character char1 = temp1.charAt(j);
                Character char2 = temp2.charAt(j);
                if (char1.compareTo(char2) != 0) {
                    //System.out.println("source: " + char1);
                    //System.out.println("dest: " + char2);
                    graphs.addEdge(char1, char2);
                    break;
                } 
            }
        }
        return graphs;
    }

    /**
    Helper method to sort.
    @param unordered is the array of unordered
    @param dict is the dictionary to sort
    @return ArrayList<String> of ordered symbols
    */
    private static ArrayList<String> sortDisordered(ArrayList<String> 
        unordered, HashMap<Character, ArrayList<Character>> dict) {

        for (int i = 0; i < unordered.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < unordered.size(); j++) {
                if (!compare(unordered.get(j), unordered.get(index), dict)) {
                    index = j;
                }
            }
            String temp2 = unordered.get(index);
            unordered.set(index, unordered.get(i));
            unordered.set(i, temp2);
        }
        return unordered;
    }

    /**
    Helper method to compare in sort.
    @param one is the array of unordered
    @param two is the dictionary to sort
    @param dict HashMap<String, ArrayList<String>> dict is dictionary.
    @return boolean if one is greater than two
    */
    private static boolean compare(String one, String two, 
        HashMap<Character, ArrayList<Character>> dict) {

        int numChars = 0;
        if (one.length() < two.length()) {
            numChars = one.length();
        } else {
            numChars = two.length();
        }
        for (int j = 0; j < numChars; j++) {
            Character char1 = one.charAt(j);
            Character char2 = two.charAt(j);
            if (char1.compareTo(char2) != 0) {
                //System.out.println(char1 + " " + char2);
                return dict.get(char1).contains(char2);
            } 
        }
        return false;
    }
}
