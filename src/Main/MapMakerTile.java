package Main;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import org.w3c.dom.events.MutationEvent;

public class MapMakerTile extends JPanel {
	int x, y, state;
	boolean isStart = false;
	boolean isEnd = false;

	public MapMakerTile(int x, int y) {
		this.x = x;
		this.y = y;

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				state = MakeMap.stateChoice();
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (state == 2 && !MakeMap.hasStart) {
						setBackground(Color.green);
						MakeMap.map[x][y] = state;
						MakeMap.hasStart = true;
						isStart = true;
					} else if (state == 3 && !MakeMap.hasEnd) {
						setBackground(Color.red);
						MakeMap.map[x][y] = state;
						MakeMap.hasEnd = true;
						isEnd = true;
					} else if (state == 1) {
						setBackground(Color.white);
						MakeMap.map[x][y] = state;
						if (isStart) {
							MakeMap.hasStart = false;
							isStart = false;
						}
						if (isEnd) {
							MakeMap.hasEnd = false;
							isEnd = false;
						}
					} else if (state == 0) {
						setBackground(Color.gray);
						MakeMap.map[x][y] = state;
						if (isStart) {
							MakeMap.hasStart = false;
							isStart = false;
						}
						if (isEnd) {
							MakeMap.hasEnd = false;
							isEnd = false;
						}
					}
				}
			}
		});
	}
}
