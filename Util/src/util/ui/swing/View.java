package util.ui.swing;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class View {

	/**
	 * Gets the minimum and maximum bounds of the view. If the bounds are stored
	 * as a Rectangle, this should return a copy of it.
	 */
	public abstract Rectangle getViewBounds();

	/**
	 * Sets the minimum and maximum bound of the view. Calls
	 * {@link #setViewBounds(int, int, int, int)}
	 * 
	 */
	public void setViewBounds(Rectangle bounds) {
		setViewBounds(bounds.x, bounds.x + bounds.width - 1, bounds.y, bounds.y + bounds.height - 1);
	}

	/**
	 * Sets the minimum and maximum bound of the view.
	 */
	public abstract void setViewBounds(int xMin, int xMax, int yMin, int yMax);
	
	public abstract void drawView(Graphics g);
}