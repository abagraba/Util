package util.ui.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;



public abstract class Map {

	private int	width, height, tileWidth, tileHeight;

	public Map(int width, int height, int tileWidth, int tileHeight) {
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public abstract BufferedImage getTile(int x, int y);

	public void drawMap(Graphics g, int x, int y, int width, int height, float zoom) {
		int w = (int) (tileWidth * zoom);
		int h = (int) (tileHeight * zoom);

		int tileXMin = x / w;
		int tileXMax = (x + width - 1) / w;
		int tileYMin = y / h;
		int tileYMax = (y + height - 1) / h;
		int xOffset = x % w;
		int yOffset = y % h;

		for (int tx = 0; tx <= tileXMax - tileXMin; tx++)
			for (int ty = 0; ty <= tileYMax - tileYMin; ty++)
				g.drawImage(getTile(tx + tileXMin, ty + tileYMin), -xOffset + tx * w, -yOffset + ty * h, w, h, null);
	}
}
