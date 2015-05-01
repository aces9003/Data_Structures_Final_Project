import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;


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
        //Weight of the edge
        int weight = 0;
        //Scanner to parse through the file.
        Scanner inRead = new Scanner(new FileReader(args[0]));
        int numRows = inRead.nextInt();
        int numCols = inRead.nextInt();
        //Vertex number
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
        
        //These two lines read in the extra lines between the 
        //first line of text and the first line of coordinates.
        inRead.nextLine();
        inRead.nextLine();
        
        while (inRead.hasNext()) {
            
            
            lineOfInput = inRead.nextLine();
            
            //These next assignments represent the coordinate 
            //position of the points as vertex numbers.
            //These range from 0 to (N-1) where N is the number of vertices.
            int xFirst = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    indexOf('(') + 1, lineOfInput.indexOf(',')));
            
            int yFirst = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    indexOf(',') + 1, lineOfInput.indexOf(')')));
            
            int xSecond = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf('(') + 1, lineOfInput.lastIndexOf(',')));
            
            int ySecond = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf(',') + 1, lineOfInput.lastIndexOf(')')));
            
            weight = Integer.parseInt(lineOfInput.substring(lineOfInput.
                    lastIndexOf(')') + 2, lineOfInput.length()));
            
            //Creates a string containing the coordinate points
            String edgeString = "(" + xFirst + "," + yFirst + ")" + " " + "("
                    + xSecond + "," + ySecond + ")";
            
            //Adds an edge to the graph with the two vertices, 
            //the weight of the edge, and the string 
            //representation of the coordinates of the two vertices.
            backyard.addEdge(twoDMap[xFirst][yFirst], 
                    twoDMap[xSecond][ySecond], weight, edgeString);
        }
        inRead.close();
        
        //Perform Kruskal's method on the graph to 
        //obtain the minimum spanning tree.
        backyard.kruskalPublic();
        
        //Print the graph to the specified file.
        backyard.printGraphPublic(new PrintWriter(args[1]), args[1]);
        
    }
    
    

}
