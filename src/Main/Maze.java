package Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class Maze {
	JFrame MazeWindow = new JFrame("Maze Window");
	JFrame MenuWindow;
	JButton Back = new JButton("Back");
	JButton Go = new JButton("Go");
	JButton Pause = new JButton("Pause");
	JCheckBox isAnimation = new JCheckBox("Animation");
	public static final int buttonPositionX = 550;
	public static final int rows = 20;
	public static final int columns = 20;
	public static final int panelSize = 25;
	private static int map[][] = new int[columns][rows];
	public int Start;
	public int End;
	public static LinkedList<Tile> tileList;
	Thread t1 = new Thread();
	boolean isRunning = true;
	boolean choiceAnimation = false;

	public Maze(String str) {
		tileList = new LinkedList<Tile>();
		loadMap(str);
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
				t1.stop();
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
				if (t1.isAlive() == false) {
					resetMap();
					Path path1 = new Path();
					path1.getData(tileList, map, Start, End, choiceAnimation);
					t1 = new Thread(path1);
					t1.start();
				}
			}
		});
		// Pause Button
		Pause.setSize(Main.buttonWidth, Main.buttonHeight);
		Pause.setLocation(buttonPositionX, 110);
		MazeWindow.add(Pause);
		Pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (t1.isAlive() == true) {
					if (Pause.getText().equals("Pause")) {
						Pause.setText("Play");
						t1.suspend();
					} else {
						Pause.setText("Pause");
						t1.resume();
					}
				}
			}
		});
		// isAnimation Check Box
		isAnimation.setLocation(buttonPositionX, 150);
		isAnimation.setSize(100, 100);
		MazeWindow.add(isAnimation);
		isAnimation.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == 1)
					choiceAnimation = true;
				else
					choiceAnimation = false;
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
				} else if(map[x][y] == 1)
				{
					tile.setBackground(Color.WHITE);
				}
				else if(map[x][y] == 2)
				{
					tile.setBackground(Color.green);
					Start = y*columns + x;
				}
				else if(map[x][y] ==3)
				{
					tile.setBackground(Color.red);
					End  = y*rows + x;
					System.out.println(End);
				}
				tile.setVisible(true);
				MazeWindow.add(tile);
			}
		}
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

	void resetMap() {
		for (Tile tile : tileList) {
			if (tile.getBackground().equals(Color.pink) || tile.getBackground().equals(Color.orange)) {
				tile.setBackground(Color.white);
			}
		}
	}

	public void getData(JFrame jFrame) {
		this.MenuWindow = jFrame;
	}
}
