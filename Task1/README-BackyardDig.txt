Task 1: BackyardDig

Our strategy for solving this involves 3 classes:
    -the Graph class is responsible for representing all of the elements that go into the graph.
    -the edge class represents an edge with beginning and ending vertices and the weight associated with the edge.
    -the vertex class represents a single vertex in the graph and contains an adjacency list for all of the vertices that the vertex is adjacent to.

As they are created, the edges are placed into a minimum priority queue, where they are sorted by edge weight.
		-To order the edges by weight, the priority queue class from the Java API takes a comparator as a parameter and the compareTo method in the Edges class
		 orders the elements.

Kruskal's method works as follows:
	-This method uses a union-find data structure to check whether two vertices are already connected, so that no cycles are created in the graph.
	-If the edge creates no cycles, then it is added to an array list of edges that hold the final edges needed for the minimum spanning tree.

Time Complexity:
	-The find operation of Kruskal's method is O(logN) and this is done 2k times where k is the number of edges in the MST.
	-Adding edged to the array list is O(1) amortized, but O(N) worst case due to resizing.
	-Printing the graph is O(k) where k is the number of elements.
	-So, final complexity is O(klogN)