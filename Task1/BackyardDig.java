import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


/**
 * This class determines the shortest path required to 
 * dig up all of the toys put underground by Professor More's son.
 * @author Mariano Pennini
 *
 */
public final class BackyardDig {
    
    /**
     * Added this to shut checkstyle up.
     */
    private BackyardDig() {
        
    }
    /**
     * The main method for the BackyardDig class.
     * @param args the arguments passed to the main method.
     * @throws FileNotFoundException thrown if the file is not found.
     */
    public static void main(String[] args) throws FileNotFoundException {
        int weight = 0;
        Scanner inRead = new Scanner(new FileReader(args[0]));
        int numRows = inRead.nextInt();
        int numCols = inRead.nextInt();
        int atCellNum = 0;
        int[][] twoDMap = new int[numRows][numCols];
        String lineOfInput = "";
        Graph backyard = new Graph();
        
        //Fill 2D array with vertex numbers
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                twoDMap[i][j] = atCellNum;
                atCellNum++;
                
            }
        }
        
        
        
        inRead.nextLine();
        while (inRead.hasNext()) {
            
            
            lineOfInput = inRead.nextLine();
            
            int xFirst = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    indexOf('(') + 1, lineOfInput.indexOf(',')));
            //System.out.print("xFirst: " + xFirst);
            int yFirst = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    indexOf(',') + 1, lineOfInput.indexOf(')')));
            //System.out.print(" yFirst: " + yFirst);
            int xSecond = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf('(') + 1, lineOfInput.lastIndexOf(',')));
            //System.out.print("xSecond: " + xSecond);
            int ySecond = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf(',') + 1, lineOfInput.lastIndexOf(')')));
            //System.out.println(" ySecond: " + ySecond);
            weight = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf(')') + 2, lineOfInput.length()));
            //System.out.println("Weight: " + weight);
            backyard.addEdge(twoDMap[xFirst][yFirst], 
                    twoDMap[xSecond][ySecond], weight);
        }
        
        inRead.close();
        
        //backyard.kruskal();
        
    }
    
    

}
