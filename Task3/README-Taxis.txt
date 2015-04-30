Run Taxis.java to execute program as stated in the instructions for Task 3.

NOTE: We use a Lambda expression in our Comparator in TaxiDriver.java. Must run with at least source level 1.8 or above.

Task 3: Taxis Strategy

Alexander Sharata (asharat1)
Mariano Pennini (mpennin3)
Matthias Philippine (mphili11)

Our strategy first involved implementing a single-source shortest path algorithm in our Graph class that we all developed together, and our Graph class implements adjacency lists for each Vertex to keep a list of adjacent vertices. The shortest path algorithm we decided to use was Dijkstra’s because it was a clear choice to solve the problem at hand. In our Graph, we implemented Dijkstra’s Algorithm, and then we had a recursive function that would help us build a string containing the path found by a call to the algorithm, returning a string in the form of “v1 -> v2 -> … -> vN.”

After the proper modifications to our graph had been made so that we could find the shortest path between two vertices and return that path in a String representation, we decided to make a TaxiDriver class that would store info about the drivers in the driverLocations.txt file. Each TaxiDriver would store a driver ID, location, shortest path (String), and that respective path’s weight (Integer).

In Taxis.java, we utilize 3 different data structures in total to solve Task 3: a Graph, ArrayList, and HashMaps. First off, we created our graph based on the edges specified in mapConnections.txt, and then we created a HashMap that maps Integer->String. The integer was from 1 - N (N being the total number of map locations) and the String was the corresponding location in order from mapLocations.txt. This made it easy to print out all the locations specified in the mapLocations.txt with constant time access of O(1).

Then after reading in the driverLocations.txt file, we would create a new TaxiDriver object with a constructor that took an argument for the driver’s ID and location and store each newly created TaxiDriver object in an ArrayList<TaxiDriver>. Then based on the driver’s location, we would call our Dijkstra’s method M times (M being the number of drivers specified in the driverLocations.txt file) and determine the shortest path, and weight of that path, from each driver’s location to the source (the source being the location of the client). Then in each TaxiDriver object, we would update their respective shortest weights to the weight of the path found by Dijkstra’s and their shortest paths to a String containing the shortest path in the form of “v1 -> v2 -> … -> vN” as discussed above. 

On top of concurrently keeping track of the TaxiDriver objects with our ArrayList<TaxiDriver>, we also created a HashMap mapping the driver’s ID (Integer) -> respective driver object (TaxiDriver), and this gave us constant time access of O(1) to access a specific TaxiDriver object when the user specified the driver ID of the driver they wanted to be picked up by.

Our TaxiDriver class implements Comparable, so this allowed us to define a Comparator for how we want to sort a collection of TaxiDriver objects. We used this to our advantage and sorted the ArrayList<TaxiDriver>, and then we were able to return the k number of drivers with the least weighted paths simply because they were the first k in our freshly sorted ArrayList<TaxiDriver>. Then using the HashMap that was also keeping track of the TaxiDriver objects and their IDs, we were able to easily access which TaxiDriver would come pick up the client upon the client specifying an ID. The way we set things up also made it easy for us to quickly access in O(1) time the ETA of the k drivers and add a feature to show the ETA of each driver before the client picks one.

The efficiency breakdown of our strategy is as follows:
If the number of drivers is M, then we made M calls to our Dijkstra’s method. And since Dijkstra’s has a running time of O(|V|^2), which runs in time linear in the number of edges, our method ran in O(M * |V|^2) — which isn’t all too shabby.
 

