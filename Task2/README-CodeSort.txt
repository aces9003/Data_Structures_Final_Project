Task 2: CodeSort

Our strategy for solving this problem involves a hashmap with keys which represent characters and values which are sets of characters (char --> Set<char>) to allow for rapid access to individual characters (keys) and rapid comparison to all characters which proceed it (Set<char>).

To populate the hashmap, a topological sort will be used to exploit the in-degree system in graphs which can be used for priority and alphabetization. To determine the directed graph, the sorted input file will be read in and a directed graph will be populated based on its content. The graph is implemented with a vertex class with parameters to store data and a directed pointer array to other connected verticies. A hashmap stores every vertex in the same fashion as the one above, but instead of having the ordered set as the value, the set is of adjacent verticies only.


Steps for implementation:

        1. Read in file and make directed graph from strings
        2. Topological sort into hash map of sorted words
        3. Read in file of unsorted and make list of Strings
        4. Use map to order them and output to file, output file when done