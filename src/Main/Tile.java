package Main;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Tile extends JPanel {
	int x,y;
	boolean isWall = true;
	public Tile(int x,int y)
	{
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		this.x = x;
		this.y = y;
	}
	public void setWall(boolean isWall)
	{
		this.isWall = isWall;
	}
}
