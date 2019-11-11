package Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Maze {
	JFrame MazeWindow = new JFrame("Maze Window");
	JFrame MenuWindow;
	JButton Back = new JButton("Back");
	JButton Go = new JButton("Go");
	public static final int buttonPositionX = 550;
	public static final int rows = 20;
	public static final int columns = 20;
	public static final int panelSize = 25;
	public static int map[][] = new int[columns][rows];
	private int Start;
	private int End;
	LinkedList<Tile> tileList = new LinkedList<Tile>();
	Graph graph = new Graph(Graph.numOfElement);

	public Maze(String str) {
		loadMap(str);
		System.out.println(str);
		// Set up MazeWindow JFrame
		MazeWindow.setSize(Main.screenWidth + 140, Main.screenHeight + 60);
		MazeWindow.setResizable(false);
		MazeWindow.setLayout(null);
		MazeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MazeWindow.setLocationRelativeTo(null);

		// Back button
		Back.setSize(Main.buttonWidth, Main.buttonHeight);
		Back.setLocation(buttonPositionX, 30);
		MazeWindow.add(Back);
		Back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MazeWindow.setVisible(false);
				MenuWindow.setVisible(true);
			}
		});

		// Go Button
		Go.setSize(Main.buttonWidth, Main.buttonHeight);
		Go.setLocation(buttonPositionX, 70);
		MazeWindow.add(Go);
		Go.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graph.Shortest(graph, map, Start, End);
				int shortWay[] = new int[100];
				int i = 0;
				while (graph.parent[End] != Start) {
					shortWay[i++] = graph.parent[End];
					End = graph.parent[End];
				}
				for(int k = 0; k < i; k++)
				{
					tileList.get(shortWay[k]).setBackground(Color.pink);
				}
			}
					
		});

		// Color Map
		for (int y = 0; y < columns; y++) {
			for (int x = 0; x < rows; x++) {
				Tile tile = new Tile(x, y);
				tileList.add(tile);
				tile.setSize(panelSize, panelSize);
				tile.setLocation(x * panelSize + 23, y * panelSize + 25);
				if (map[x][y] == 0) {
					tile.setBackground(Color.GRAY);
				} else {
					tile.setBackground(Color.WHITE);
					tile.setWall(false);
					if (x == 0) {
						Start = y * columns;
						tile.setBackground(Color.green);
					}
					if (x == columns - 1) {
						End = y * rows + x;
						tile.setBackground(Color.red);
					}
				}
				tile.setVisible(true);
				MazeWindow.add(tile);
			}
		}

		//
		MazeWindow.setVisible(true);
	}

	private void loadMap(String str) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(str));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String mapString = sb.toString();
			int counter = 0;
			for (int y = 0; y < columns; y++)
				for (int x = 0; x < rows; x++) {
					String mapChar = mapString.substring(counter, counter + 1);
					if (!mapChar.equals("\r\n") && !mapChar.equals("\r") && !mapChar.equals("\n")) {
						map[x][y] = Integer.parseInt(mapChar);
					} else {
						x--;
					}
					counter++;
				}

		} catch (Exception e) {
			System.out.println("Cannot load map.");
		}

	}

	public void getData(JFrame jFrame) {
		this.MenuWindow = jFrame;
	}
}
