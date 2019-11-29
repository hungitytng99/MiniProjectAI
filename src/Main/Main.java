package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
public class Main{
	public JFrame MenuWindow = new JFrame();
	JButton Maze = new JButton("Maze");
	JButton MakeMap = new JButton("Make Map");
	JComboBox<String> mapListBox;
	JButton Exit = new JButton("Exit");
	ArrayList<String> mapListStr = new ArrayList<String>();
	
	public static final int buttonHeight = 30;
	public static final int buttonWidth = 120;
	public static final int screenHeight = 530;
	public static final int screenWidth = 550;
	public static final int buttonPostionY = 460;
	static boolean MapExist = false; 
	
	public Main()
	{
		//get map list
		getMapList();
		mapListBox = new JComboBox<String>(mapListStr.toArray(new String[mapListStr.size()]));
				
		//display menu
		MenuWindow.setSize(screenWidth,screenHeight);
		MenuWindow.setResizable(false);
		MenuWindow.setLayout(null);
		MenuWindow.setLocationRelativeTo(null);
		MenuWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Maze button
		Maze.setSize(buttonWidth,buttonHeight);
		Maze.setLocation(10, buttonPostionY);
		Maze.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Maze temp = new Maze(mapListBox.getSelectedItem().toString());
				temp.getData(MenuWindow);
				MenuWindow.setVisible(false);
			}
		});
		MenuWindow.add(Maze);
		//Map Maker button
		MakeMap.setSize(buttonWidth,buttonHeight);
		MakeMap.setLocation(140, buttonPostionY);
		MenuWindow.add(MakeMap);
		MakeMap.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MakeMap makeMapFrame = new MakeMap();
				makeMapFrame.getParent(makeMapFrame);;
				MenuWindow.setVisible(false);
			}
		});
		
		
		//List Map combobox
		mapListBox.setSize(buttonWidth,buttonHeight);
		mapListBox.setLocation(280, buttonPostionY);
		MenuWindow.add(mapListBox);
		
		//Exit button
		Exit.setSize(buttonWidth,buttonHeight);
		Exit.setLocation(420, buttonPostionY);
		Exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		MenuWindow.add(Exit);
		MenuWindow.setVisible(true);
	}

	
	private void getMapList() {
		// TODO Auto-generated method stub
		for(int i = 0; i< 99;i++)
		{
			File map = new File("./Map " + i + ".map");
			if(map.exists())
			{
				mapListStr.add("Map " + i +".map");
				MapExist = true;
			}
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
	}

}
