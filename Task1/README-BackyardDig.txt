Task 2: CodeSort

Our strategy for solving this problem involves a hashmap with keys which represent characters and values which are sets of characters (char --> Set<char>) to allow for rapid access to individual characters (keys) and rapid comparison to all characters which proceed it (Set<char>).

To populate the hashmap, a topological sort will be used because to exploit the in-degree system in graphs which can be used for priority and alphabetization. To determine the directed graph, the sorted input file will be read in and a directed graph will be populated based on its content.

*TODO: handle new characters unseen in input sorted file