package Main;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	private int v;
	public static final int numOfElement = Maze.columns * Maze.rows;
	private static LinkedList<Integer> adj[];
	public static Integer parent[] = new Integer[numOfElement];

	public Graph(int v) {
		this.v = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; i++)
			adj[i] = new LinkedList();
	}

	void addEdge(int v, int w) {
		if (!adj[v].contains(w))
			adj[v].add(w);
		if (!adj[w].contains(v))
			adj[w].add(v);
	}

	static void BFS(int start, int end) {
		boolean visited[] = new boolean[numOfElement];
		for (int i = 0; i < numOfElement; i++) {
			parent[i] = -1;
			visited[i] = false;
		}
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(start);

		while (!queue.isEmpty()) {
			int temp = queue.pollFirst();
			while (!visited[temp]) {
				visited[temp] = true;
				for (int x : adj[temp]) {
					if (!visited[x]) {
						queue.add(x);
						if (parent[x] == -1)
							parent[x] = temp;
					}
				}
			}
		}
	}

	public void Shortest(Graph graph, int[][] array,int Start,int End) {
		System.out.println("Start. Num = " + numOfElement);
		for (int i = 0; i < Maze.columns; i++)
			for (int j = 0; j < Maze.rows; j++) {
				if (array[i][j] == 1) {
					int index = j * Maze.columns + i;
					if ((i - 1) > 0)
						if (array[i - 1][j] == 1)
							graph.addEdge(index, index - 1); // left

					if ((i + 1) < Maze.columns)
						if ((array[i + 1][j] == 1) && ((index + 1) < (Maze.columns * Maze.rows)))
							graph.addEdge(index, index + 1); // right
					if ((j - 1) > 0)
						if ((array[i][j - 1] == 1) && ((index - Maze.columns) > 0)) // top
							graph.addEdge(index, index - Maze.columns);
					if ((j + 1) < Maze.rows)
						if ((array[i][j + 1] == 1) && ((index + Maze.columns) < (Maze.columns * Maze.rows)))
							graph.addEdge(index, index + Maze.columns);// bottom
				}
			}
		BFS(Start,End);
	}
}
