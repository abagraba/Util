package util.ui.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageComponent extends JComponent {

	private static final long serialVersionUID = 1587086567331765214L;
	
	private Image image;

	public ImageComponent() {
	}

	public ImageComponent(Image image) {
		setImage(image);
	}

	public void setImage(Image image) {
		if (this.image == image)
			return;
		this.image = image;
		revalidate();
		repaint();
	}
	
	public Dimension getMinimumSize(){
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public Dimension getMaximumSize(){
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public Dimension getPreferredSize(){
		return new Dimension(image.getWidth(null), image.getHeight(null));
	}

	public void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, null);
	}
}
