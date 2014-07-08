package util.ui.swing;

import java.awt.Color;
import java.awt.Graphics;



public class OrbStyle {

	Color[]	colors	= new Color[] { new Color(20, 20, 255), new Color(30, 30, 255), new Color(50, 60, 255), new Color(80, 100, 255),
			new Color(110, 130, 255), new Color(150, 150, 255), new Color(110, 130, 255), new Color(80, 100, 255), new Color(50, 60, 255),
			new Color(30, 30, 255), new Color(20, 20, 255) };

	public void drawOrb(Graphics g, int x, int y, int radius) {
		for (int i = 0; i < colors.length; i++) {
			g.setColor(colors[i]);
			g.fillOval(x - radius + i, y - radius + i, 2 * radius - 2 * i, 2 * radius - 2 * i);
		}
	}

}
