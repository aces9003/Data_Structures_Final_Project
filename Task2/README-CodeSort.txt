Task 2: CodeSort

Our strategy for solving this problem involves a hashmap with keys which represent characters and values which are sets of characters (char --> Set<char>) to allow for rapid access to individual characters (keys) and rapid comparison to all characters which proceed it (Set<char>). This Hashmap acts as a dictionary to sort the input text file.

To populate the hashmap dictionary, a topological is used to exploit the in-degree system in graphs which can be used for priority and "alphabetization". To determine the directed graph, the sorted input file is read in and a directed graph is populated based on its content. The graph is implemented with a vertex class with parameters to store data and a directed pointer array to other connected verticies. A hashmap stores every vertex in the same fashion as the one above, but instead of having the ordered set as the value, the set is of adjacent verticies only. To have the hashmap dictionary not only have the adjacent verticies of any given vertex (in terms of characters, to be able to compare and recognize all characters proceeding it), a recursive method is used to build a list of values from each adjacent list of each adjacent list from the original vertex given. 

To then organize the unsorted file, the "words" in it are input into a single string, parsed and passed into an array of strings, then the list is sorted using the dictionary of ordered characters with an insertion sort to avoid having to make a new list (insertion sort organizes within existing arrays). A file is then written from the list and output.


Steps for implementation:

        1. Read in file and make directed graph from strings
        2. Topological sort into hash map of sorted words
        3. Read in file of unsorted and make list of Strings
        4. Use map to order them and output to file, output file when done

