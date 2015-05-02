import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Taxis class that takes in 4 command-line args,
 * determines which k drivers to alert, and then
 * outputs the directions for the specified driver.
 * @author Alex Sharata (asharat1)
 *
 */
public final class Taxis {

    /** HashMap of all Taxi Drivers containing their shortest paths,
     * and it maps driver ID -> TaxiDriver object. */
    public static Map<Integer, TaxiDriver> mapOfDrivers = 
            new HashMap<Integer, TaxiDriver>();

    /**
     * For checkstyle compliance only.
     */
    private Taxis() {
    }

    /**
     * Main routine that prompts for user input and displays
     * the the k best-suited drivers to pickup the client.
     * @param args k, map locations, map connections, driver locations
     */
    public static void main(String [ ] args) {
        DijkstraGraph g = new DijkstraGraph();
        Map<Integer, String> locationMap = new HashMap<Integer, String>();
        List<TaxiDriver> driversList = new ArrayList<>();
        int k = Integer.parseInt(args[0]);
        int mapLocationsCount = 0;
        int mapConnectionsCount = 0;
        int driverCount = 0;
        argCheck(args);       

        // mapLocations.txt
        try {
            Scanner mapLocations = new Scanner(new FileReader(args[1]));
            String location = "";

            while (mapLocations.hasNext()) {
                location = mapLocations.nextLine();
                mapLocationsCount++;
                locationMap.put(mapLocationsCount, location.trim());
            }

        }  catch (IOException e) {
            System.err.println(e);
        }

        // mapConnections.txt
        try {
            Scanner mapConnections = new Scanner(new FileReader(args[2]));
            String lineOfInput = "";

            while (mapConnections.hasNext()) {
                lineOfInput = mapConnections.nextLine();
                String v1 = lineOfInput.substring(lineOfInput.
                        indexOf('(') + 1, lineOfInput.indexOf(','));
                String v2 = lineOfInput.substring(lineOfInput.
                        indexOf(',') + 1, lineOfInput.indexOf(')'));
                String weightString = lineOfInput.substring(lineOfInput.
                        lastIndexOf(')') + 1, lineOfInput.length());
                int weight = Integer.parseInt(weightString.trim());
                // Makes the streets 'two-way' streets
                g.createEdge(v1.trim(), v2.trim(), weight);
                g.createEdge(v2.trim(), v1.trim(), weight);
                mapConnectionsCount++;
            }
        }  catch (IOException e) {
            System.err.println(e);
        }

        // driverLocations.txt
        try {
            final int three = 3;    // shuts up checkstyle.
            Scanner driverLocations = new Scanner(new FileReader(args[three]));
            String lineOfInput = "";
            int driverID = 0;
            String location = "";

            while (driverLocations.hasNext()) {
                String driverIDString = "";
                lineOfInput = driverLocations.nextLine();
                location = lineOfInput.replaceAll("^\\s*[0-9]+\\s+", "");
                int idEndPt = Math.abs(lineOfInput.length() 
                        - (location.trim().length()));
                driverIDString = lineOfInput.substring(0, idEndPt);
                driverID = Integer.parseInt(driverIDString.trim());
                driverCount++;
                TaxiDriver tempDriver = 
                        new TaxiDriver(driverID, location.trim());
                driversList.add(tempDriver);
            }

        }  catch (IOException e) {
            System.err.println(e);
        }

        System.out.println();
        System.out.println("Collecting map locations from mapLocations.txt...");
        System.out.println(g.mapOfVertices.size() + " locations input.");

        System.out.println();
        System.out.println("Collecting map connections from "
                + "mapConnections.txt...");
        System.out.println(mapConnectionsCount + " connections input.");

        System.out.println();
        System.out.println("Collecting driver locations from "
                + "driverLocations.txt...");
        System.out.println(driverCount + " drivers input.");

        System.out.println();
        System.out.println("Map locations are:");
        System.out.println();
        for (int i = 1; i <= locationMap.size(); i++) {
            System.out.println("    " + i + " " + locationMap.get(i));
        }

        System.out.println();
        System.out.print("Enter number of recent client pickup request "
                + "location: ");
        Scanner userLocation = new Scanner(System.in);

        // Source location AKA client's location (AKA destination for driver)
        String sourceString = userLocation.nextLine();
        System.out.println();

        for (TaxiDriver tempDriver : driversList) {
            // Location of driver AKA starting point
            g.dijkstraAlgorithm(tempDriver.location);

            int source = Integer.parseInt(sourceString);
            g.getShortestPath(locationMap.get(source));

            tempDriver.setShortestPath(g.currentShortestPath);
            tempDriver.setPathWeight(g.currentShortestPathWeight);
            mapOfDrivers.put(tempDriver.driverID, tempDriver);
        }

        Collections.sort(driversList, TaxiDriver.Comparators.PATHWEIGHT);

        System.out.println("The " + k + " drivers to alert about this pickup "
                + "are: ");
        System.out.println();
        for (int i = 0; i < k; i++) {
            System.out.println(driversList.get(i).driverID + " at " 
                    + driversList.get(i).location
                    + " (ETA: " + driversList.get(i).getPathWeight() 
                    + " minutes)");
        }

        System.out.println();
        System.out.print("Enter the ID number of the driver who responded: ");
        Scanner driverChoice = new Scanner(System.in);
        // Source location AKA client's location (AKA destination for driver)
        String driverString = driverChoice.nextLine();
        System.out.println();

        TaxiDriver chosen = 
                mapOfDrivers.get(Integer.parseInt(driverString.trim()));
        System.out.println("The recommended route for driver " + driverString 
                + " is: ");
        System.out.println(chosen.getShortestPath());
        System.out.println("Expected total time: " + chosen.getPathWeight() 
                + " minutes");
        System.out.println();

        pickAnotherDriver();
        return;
    }

    /**
     * Helper method checking correct number of command-line args was passed.
     * @param args command-line arguments
     * @return true if number of arguments is 4
     */
    private static boolean argCheck(String [ ] args) {
        final int four = 4;
        if (args.length != four) {
            System.out.println("Improper number of command-line argss passed. "
                    + "Aborting.");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Allows user to pick another driver.
     */
    private static void pickAnotherDriver() {
        String userChoice;

        while (true) {
            Scanner driverChoice = new Scanner(System.in);

            // Prompt user to choose another driver
            System.out.printf("Do you want to pick another driver? [Y/N]? ");
            userChoice = driverChoice.nextLine();

            if (userChoice.equals("n") || userChoice.equals("N")) {
                System.out.println("Goodbye.");
                return;
            }

            System.out.println();
            System.out.print("Enter the ID number of the driver who responded: "
                    + "");
            String driverString = driverChoice.nextLine();
            System.out.println();

            TaxiDriver chosen = 
                    mapOfDrivers.get(Integer.parseInt(driverString.trim()));
            System.out.println("The recommended route for driver " 
                    + driverString + " is: ");
            System.out.println(chosen.getShortestPath());
            System.out.println("Expected total time: " + chosen.getPathWeight() 
                    + " minutes");
            System.out.println();
        }
    }
}
