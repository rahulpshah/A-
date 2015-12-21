This is CLI application which takes input the map through a text file and finds the shortest path form one city to another using DFS/BFS algorithm.

> `javac SearchRomania.java`

> `java SearchRomania [searchtype] [sourcecity] [destinationcity] < input.txt`

Example:

> javac SearchRomania.java
> java SearchRomania dfs zerind craiova < input.txt

The input file entries should be in following format:

`(source,destination,weight)`

