package Main;

import java.awt.Color;
import java.util.LinkedList;

public class Path implements Runnable {
	LinkedList<Tile> tileList = new LinkedList<Tile>();
	Graph graph = new Graph(Graph.numOfElement);
	public int Start;
	public int End;
	public static int map[][] = new int[Maze.columns][Maze.rows];
	
	@Override
	public void run(){
		graph.Shortest(graph, map, Start, End);
		int shortWay[] = new int[100];
		int i = 0;
		while (graph.parent[End] != Start) {
			shortWay[i++] = graph.parent[End];
			End = graph.parent[End];
		}
		for(int k = i-1; k >= 0; k--)
		{
			tileList.get(shortWay[k]).setBackground(Color.pink);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}
	public void getData(LinkedList<Tile> tileList,int [][]map,int Start,int End)
	{
		this.tileList = tileList;
		this.Start = Start;
		this.End = End;
		this.map = map;
	}

}
