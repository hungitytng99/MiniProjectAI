package Main;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Graph {
	LinkedList<Tile> tileList;
	public static final int numOfElement = Maze.columns * Maze.rows;
	private static LinkedList<Integer> adj[];
	private static LinkedList<Integer> open;
	static double[] gScore = new double[numOfElement];
	static double[] hScore = new double[numOfElement];
	static int[] previous = new int[numOfElement];
	static boolean[] vistied = new boolean[numOfElement];
	public static final double INFINITIVE_VALUE = 1000;
	static boolean choiceAnimation;

	public Graph(int v) {
		open = new LinkedList<Integer>();
		tileList = new LinkedList<Tile>();
		this.tileList = Maze.tileList;
		adj = new LinkedList[v];
		for (int i = 0; i < v; i++) {
			adj[i] = new LinkedList();
			gScore[i] = INFINITIVE_VALUE;
			previous[i] = -1;
			vistied[i] = false;
		}
	}

	void addEdge(int v, int w) {
		if (!adj[v].contains(w))
			adj[v].add(w);
		if (!adj[w].contains(v))
			adj[w].add(v);
	}

	public static double distance(int x, int y) {
		int i = 0, j = 0, k = 0, m = 0;
		i = (int) x / Maze.columns;
		j = x % Maze.rows;
		k = (int) y / Maze.columns;
		m = y % Maze.rows;
		return Math.sqrt(Math.pow(Math.abs(i - k), 2) + Math.pow(Math.abs(j - m), 2));

	}


	void printPath(int Start, int End) {
		int[] tmp = new int[numOfElement];
		int k = previous[End];
		int n = 0;
		tmp[n++] = End;
		while (k != Start) {
			tmp[n++] = k;
			k = previous[k];
		}
		tmp[n++] = Start;
		// printArray(tmp);

		for (int i = 0; i < n; i++) {
			tileList.get(tmp[i]).setBackground(Color.orange);
		}
		tileList.get(Start).setBackground(Color.green);
		tileList.get(End).setBackground(Color.red);
		JOptionPane.showMessageDialog(Path.frame, "Find " + (int) (gScore[End] - 1) + " steps.");
	}

	public void findShortest(int Start, int End) {// Using A Star algorithm

		double min;
		int u = 0;
		boolean isFound = false;
		open.add(Start);
		gScore[Start] = 0;
		previous[Start] = Start;
		while (!open.isEmpty() && !isFound) {
			min = INFINITIVE_VALUE;
			// tim so buoc di nho nhat trong cac diem dang xet
			for (int i : open) {
				vistied[i] = true;
				hScore[i] = distance(i, End);
				if (min > (hScore[i] + gScore[i])) {
					u = i;
					min = hScore[i] + gScore[i];
				}
			}
			if (u != Start) {
				tileList.get(u).setBackground(Color.pink);
				if (choiceAnimation == true)
					try {
						Thread.sleep(500);
					} catch (Exception e) {
					}
			}
			open.remove(open.indexOf(u));
			for (int v : adj[u]) {
				if (v == End) {
					gScore[v] = gScore[u] + 1;
					previous[v] = u;
					isFound = true;
					break;
				}
				if (!vistied[v]) {
					int w = 1;
					if (gScore[v] > (gScore[u] + w)) {
						gScore[v] = gScore[u] + w;
						previous[v] = u;
					}
					open.add(v);
				}
			}
		}
	}

	public void makeGraph(Graph graph, int[][] array, int Start, int End, boolean choiceAnimation) {
		this.choiceAnimation = choiceAnimation;
		for (int i = 0; i < Maze.columns; i++)
			for (int j = 0; j < Maze.rows; j++) {
				if (array[i][j] != 0) {
					int index = j * Maze.columns + i;
					if ((i - 1) > 0)
						if (array[i - 1][j] != 0)
							graph.addEdge(index, index - 1); // left
					if ((i + 1) < Maze.columns)
						if ((array[i + 1][j] != 0) && ((index + 1) < (Maze.columns * Maze.rows)))
							graph.addEdge(index, index + 1); // right
					if ((j - 1) > 0)
						if ((array[i][j - 1] != 0) && ((index - Maze.columns) > 0)) // top
							graph.addEdge(index, index - Maze.columns);
					if ((j + 1) < Maze.rows)
						if ((array[i][j + 1] != 0) && ((index + Maze.columns) < (Maze.columns * Maze.rows)))
							graph.addEdge(index, index + Maze.columns);// bottom
				}
			}
		findShortest(Start, End);
		if (choiceAnimation == true)
			try {
				Thread.sleep(700);
			} catch (Exception e) {
			}
		printPath(Start, End);
	}
}
