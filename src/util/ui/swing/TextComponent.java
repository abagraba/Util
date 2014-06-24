package util.ui.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class TextComponent extends JComponent {

	public String text;
	private Font font;
	private Rectangle2D bounds;
	private boolean dirtyBounds;

	public TextComponent() {
		this("");
	}

	public TextComponent(String text) {
		setText(text);
	}

	public void setText(String text) {
		this.text = text;
		dirtyBounds = true;
	}

	public String getText() {
		return text;
	}

	public void setFont(Font font) {
		this.font = font;
		new TextLayout(text, font, null);
		dirtyBounds = true;
	}

	public Font getFont() {
		return font;
	}

	public Dimension getMinimumSize() {
		return new Dimension((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()));
	}

	public Dimension getMaximumSize() {
		return new Dimension((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()));
	}

	public Dimension getPreferredSize() {
		return new Dimension((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()));
	}

	public void paintComponent(Graphics g) {
		if (dirtyBounds) {
			dirtyBounds = false;
			bounds = font.getStringBounds(text, ((Graphics2D) g).getFontRenderContext());
			revalidate();
			repaint();
			return;
		}
		else {
			g.setFont(font);
			g.drawString(text, 0, 0);
		}
	}

}
