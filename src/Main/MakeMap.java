package Main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class MakeMap extends JFrame {
	JFrame makeMapFrame = new JFrame();
	static int rows = 20;
	static int columns = 20;
	int panelSize = 25;
	static int map[][] = new int[columns][rows];
	ArrayList<String> mapList = new ArrayList<String>();
	int level = 0;
	boolean levelsExistAlready = false;
	JPanel choiceOption = new JPanel();
	ButtonGroup Option = new ButtonGroup();
	static JRadioButton startRadio = new JRadioButton("Start");
	static JRadioButton endRadio = new JRadioButton("End");
	static JRadioButton blockRadio = new JRadioButton("Block");
	static JRadioButton roadRadio = new JRadioButton("Road");
	JButton saveButton = new JButton("Save");
	int state;// end start block or road
	static boolean hasStart = false, hasEnd = false;
	boolean isSave;

	public MakeMap() {
		this.hasStart = false;
		this.hasEnd = false;
		isSave = false;
		startRadio.setSize(70, 50);
		startRadio.setSelected(true);
		endRadio.setSize(70, 50);
		blockRadio.setSize(70, 50);
		roadRadio.setSize(70, 50);
		saveButton.setSize(70, 30);
		Option.add(startRadio);
		Option.add(endRadio);
		Option.add(blockRadio);
		Option.add(roadRadio);
		choiceOption.add(startRadio);
		choiceOption.add(endRadio);
		choiceOption.add(blockRadio);
		choiceOption.add(roadRadio);
		choiceOption.setSize(70, 100);
		choiceOption.setLocation(530, 25);
		saveButton.setLocation(530, 135);
		choiceOption.setVisible(true);
		choiceOption.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		choiceOption.setLayout(new BoxLayout(choiceOption, BoxLayout.Y_AXIS));
		this.add(choiceOption);
		this.add(saveButton);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				saveMapMethod();
			}
		});
		getMapList();
		getLevelChoice();
		if (level != -1) {
			loadMap();
			this.setResizable(false);
			this.setSize((columns * panelSize) + 110, (rows * panelSize) + 70);
			this.setTitle("Map Maker");
			this.setLayout(null);

			this.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					if(!isSave)
					{
						JOptionPane.showMessageDialog(makeMapFrame, "Your map isn't saved.");
					}
					new Main();
				}
			});

			this.setLocationRelativeTo(null);

			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					MapMakerTile tile = new MapMakerTile(x, y);
					tile.setSize(panelSize - 1, panelSize - 1);
					tile.setLocation((x * panelSize) + 23, (y * panelSize) + 25);
					if (map[x][y] == 0) {
						tile.setBackground(Color.GRAY);
					} else if (map[x][y] == 1) {
						tile.setBackground(Color.WHITE);
					} else if (map[x][y] == 2) {
						hasStart = true;
						tile.isStart = true;
						tile.setBackground(Color.GREEN);
					} else if (map[x][y] == 3) {
						tile.isEnd = true;
						hasEnd = true;
						tile.setBackground(Color.RED);
					}

					tile.setVisible(true);
					this.add(tile);
				}
			}
			this.setVisible(true);
		} else {
			new Main();
		}
	}

	public void getMapList() {
		for (int i = 0; i < 99; i++) {
			File map = new File("./Map " + i + ".map");
			if (map.exists()) {
				mapList.add("Map " + i + ".map");
				levelsExistAlready = true;
			}
		}
	}

	public static int stateChoice() {
		if (startRadio.isSelected()) {
			if (!hasStart)
				return 2;
			else
				return 4;
		} else if (endRadio.isSelected()) {
			if (!hasEnd)
				return 3;
			else
				return 4;
		} else if (blockRadio.isSelected()) {
			return 0;

		} else if (roadRadio.isSelected()) {
			return 1;
		}
		return 4;
	}

	public void getLevelChoice() {
		if (levelsExistAlready) {
			String maps[] = new String[99];
			mapList.toArray(maps);
			maps[mapList.size()] = "New level";
			String choice = (String) JOptionPane.showInputDialog(null, "Which level would you like to play?",
					"Maze  Selector", JOptionPane.QUESTION_MESSAGE, null, maps, maps[0]);
			if (choice != null && !choice.equals("New level")) {
				level = Integer.parseInt((choice.replace("Map ", "").replace(".map", "")));
			} else if (choice == null) {
				level = -1;
			} else {
				level = mapList.size();
			}
		}
	}

	public void saveMap() {
		try {
			PrintWriter writer = new PrintWriter("Map " + level + ".map", "UTF-8");
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					writer.print(map[x][y]);
				}
				writer.print("\r\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadMap() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Map " + level + ".map"));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String mapStr = sb.toString();

			int counter = 0;
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					String mapChar = mapStr.substring(counter, counter + 1);
					if (!mapChar.equals("\r\n") && !mapChar.equals("\n") && !mapChar.equals("\r")) {// If it's a number
						// System.out.print(mapChar);
						map[x][y] = Integer.parseInt(mapChar);
					} else {// If it is a line break
						x--;
						// System.out.print(mapChar);
					}
					counter++;
				}
			}
		} catch (Exception e) {
			for (int y = 0; y < columns; y++) {
				for (int x = 0; x < rows; x++) {
					map[x][y] = 1;
				}
			}
		}
	}

	public void saveMapMethod() {
		if (hasStart && hasEnd) {
			saveMap();
			isSave = true;
		} else {
			JOptionPane.showMessageDialog(makeMapFrame, "Your map must have one start and one end.");
			isSave = false;
		}
	}

	public void getParent(JFrame frame) {
		this.makeMapFrame = frame;
	}

}
