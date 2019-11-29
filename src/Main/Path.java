package Main;

import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Path implements Runnable {
	LinkedList<Tile> tileList = new LinkedList<Tile>();
	Graph graph = new Graph(Graph.numOfElement);
	public int Start;
	public int End;
	public static int map[][] = new int[Maze.columns][Maze.rows];
	boolean choiceAnimation;
	static JFrame frame = new JFrame();
	@Override
	public void run(){
		try {
			graph.makeGraph(graph, map, Start, End,choiceAnimation);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "There is no shortest path");
		}
		
	}
	public void getData(LinkedList<Tile> tileList,int [][]map,int Start,int End,boolean choiceAnimation,JFrame frame)
	{
		this.tileList = tileList;
		this.Start = Start;
		this.End = End;
		this.map = map;
		this.choiceAnimation = choiceAnimation;
		this.frame = frame;
	}
}
