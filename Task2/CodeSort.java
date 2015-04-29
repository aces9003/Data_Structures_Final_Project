//class to interpret input file and alphabetize for

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
CodeSort main method, populates graph, then makes hashmap of sorted values.
After having sorted values in map, sort input file and output a sorted one.
*/
public class CodeSort {

    /**
    Main class for CodeSort.
    @param args the files for input and output
    */
    public static void main(String[] args) {

        if (args.length < 3) {
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
            Stream<String> lines = Files.lines(path1);
            lines.forEach(s -> arrayOrdered.add(s));
        } catch (IOException ex) { 
        }

        /*
        for (String line: Files.lines(Paths.get(ordered))) {
            arrayOrdered.add(line);
        } */

        //first value is first character of first string, then loop until n-1
        //strating at 2nd value and compare to previous
        if (arrayOrdered.size() < 2) {
            //output error message, file too small
            System.out.println("Error: sorted file has insufficient content.");
            return;
        }
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
                String char1 = temp1.substring(j, j + 1);
                String char2 = temp2.substring(j, j +1 );
                if (char1.compareTo(char2) != 0) {
                    System.out.println("source: " + char1);
                    System.out.println("dest: " + char2);
                    graphs.addEdge(char1, char2);
                } 
            }
        }

        ////////// 2. ////////////
        HashMap<String, ArrayList<String>> dict = graphs.topologicalSort();

        ////////// 3. ////////////
        //populate unordered list
        Path path2 = Paths.get(disordered);
        try {
            Stream<String> lines = Files.lines(path2);
            lines.forEach(st -> arrayDisordered.add(st));
        } catch (IOException ex) { 
        }

/*
        for (String line: Files.lines(Paths.get(disordered))) {
            arrayDisordered.add(line);
        } */

        ////////// 4. ////////////
        ArrayList<String> orderedDisorder = 
            sortDisordered(arrayDisordered, dict);

        //input list into file and save
        try{
                FileWriter fw = new FileWriter(out);
                for (int x = 0; x < orderedDisorder.size(); x++) {
                    fw.write(orderedDisorder.get(x) + "\n");
                }
                fw.close();
            } catch(IOException e) {
            }
        System.out.println("Done!");
    }

    /**
    Helper method to sort.
    @param unordered is the array of unordered
    @param dict is the dictionary to sort
    @return ArrayList<String> of ordered symbols
    */
    private static ArrayList<String> sortDisordered(ArrayList<String> unordered,
        HashMap<String, ArrayList<String>> dict) {
        for (int i = 0; i < unordered.size() - 1; i++) {
            int index = i;
            for (int j = i + 1; j < unordered.size(); j++) {
                if (!compare(unordered.get(j), unordered.get(index), dict)) {
                    index = j;
                }
            }
            String temp2 = unordered.get(index);
            unordered.add(index, unordered.get(i));
            unordered.add(i, temp2);
        }
        return unordered;
    }

    /**
    Helper method to compare in sort.
    @param one is the array of unordered
    @param two is the dictionary to sort
    @param HashMap<String, ArrayList<String>> dict is dictionary
    @return boolean if one is greater than two
    */
    private static boolean compare(String one, String two, 
        HashMap<String, ArrayList<String>> dict) {

        int numChars = 0;
        if (one.length() < two.length()) {
            numChars = one.length();
        } else {
            numChars = two.length();
        }
        for (int j = 0; j < numChars; j++) {
            String char1 = one.substring(j, j + 1);
            String char2 = two.substring(j, j +1 );
            if (char1.compareTo(char2) != 0) {
                if (dict.get(one).contains(two)) {
                    return true;
                } else {
                    return false;
                }
            } 
        }
        return false;
    }
}
