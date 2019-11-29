package Main;

import java.util.LinkedList;

public class Path implements Runnable {
	LinkedList<Tile> tileList = new LinkedList<Tile>();
	Graph graph = new Graph(Graph.numOfElement);
	public int Start;
	public int End;
	public static int map[][] = new int[Maze.columns][Maze.rows];
	boolean choiceAnimation;
	@Override
	public void run(){
		graph.makeGraph(graph, map, Start, End,choiceAnimation);
	}
	public void getData(LinkedList<Tile> tileList,int [][]map,int Start,int End,boolean choiceAnimation)
	{
		this.tileList = tileList;
		this.Start = Start;
		this.End = End;
		this.map = map;
		this.choiceAnimation = choiceAnimation;
	}
}
