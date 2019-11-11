package Main;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MakeMap extends JFrame {
	static int rows = 20;
    static int columns = 20;
    int panelSize = 25;
    static int map[][] = new int[columns][rows];
    ArrayList<String> mapList = new ArrayList<String>();
    int level = 0;
    boolean levelsExistAlready = false;
    
    public MakeMap(){
    	getMapList();
    	getLevelChoice();
    	if(level != -1){
	        loadMap();
	        this.setResizable(false);
	        this.setSize((columns*panelSize)+50, (rows*panelSize)+70);
	        this.setTitle("Map Maker");
	        this.setLayout(null);
	        
	        this.addWindowListener(new WindowAdapter(){
	            @Override
				public void windowClosing(WindowEvent e) {
	                saveMap();
	                new Main();
	            }
	        });
	        
	        this.setLocationRelativeTo(null);
	        
	        for(int y = 0; y < columns; y++){
	            for(int x = 0; x < rows; x++){
	            	MapMakerTile tile = new MapMakerTile(x, y);
	                tile.setSize(panelSize-1, panelSize-1);
	                tile.setLocation((x*panelSize)+23, (y*panelSize)+25);
	                if(map[x][y] == 0){
	                    tile.setBackground(Color.GRAY);
	                }else{
	                    tile.setBackground(Color.WHITE);
	                }
	                
	                tile.setVisible(true);
	                
	                this.add(tile);
	            }
	        }
	        this.setVisible(true);
    	}else{
    		new Main();
    	}
    }
    
    public void getMapList(){
    	for(int i = 0; i < 99; i++){
    		File map = new File("./Map "+i+".map");
    		if(map.exists()){
    			System.out.println("Map "+i+" exists");
    			mapList.add("Map "+i+".map");
    			levelsExistAlready = true;
    		}
    	}
    }
    
    public void getLevelChoice(){
    	if(levelsExistAlready){
	    	String maps[] = new String[99];
	    	mapList.toArray(maps);
	    	maps[mapList.size()] = "New level";
	    	String choice = (String)JOptionPane.showInputDialog(null, "Which level would you like to play?", "Maze  Selector", JOptionPane.QUESTION_MESSAGE, null, maps, maps[0]);
	    	System.out.println(choice);
	    	if(choice != null && !choice.equals("New level")){
	    		level = Integer.parseInt((choice.replace("Map ", "").replace(".map", "")));
	    	}else if(choice == null){
	    		level = -1;
	    	}else{
	    		level = mapList.size();
	    	}
    	}
    }
    
    public void saveMap(){
        try{
        PrintWriter writer = new PrintWriter("Map "+level+".map", "UTF-8");
        for(int y = 0; y < columns; y++){
            for(int x = 0; x < rows; x++){
                writer.print(map[x][y]);
            }
            writer.print("\r\n");
        }
        writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void loadMap(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("Map "+level+".map"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String mapStr = sb.toString();
            
            int counter = 0;
            for(int y = 0; y < columns; y++){
                for(int x = 0; x < rows; x++){
                    String mapChar = mapStr.substring(counter, counter+1);
                    if(!mapChar.equals("\r\n") && !mapChar.equals("\n")&& !mapChar.equals("\r")){//If it's a number
                        //System.out.print(mapChar);
                        map[x][y] = Integer.parseInt(mapChar);
                    }else{//If it is a line break
                        x--;
                        //System.out.print(mapChar);
                    }
                    counter++;
                }
            }
        }catch(Exception e){
            System.out.println("Unable to load existing map(if exists), creating new map.");
            for(int y = 0; y < columns; y++){
                for(int x = 0; x < rows; x++){
                    map[x][y] = 0;
                }
            }
        }
    }
	

}
