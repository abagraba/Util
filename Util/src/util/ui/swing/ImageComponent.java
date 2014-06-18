package util.ui.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class ImageComponent extends JComponent {

	private static final long serialVersionUID = -1965490455275908662L;

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
		Dimension size = image != null ? new Dimension(image.getWidth(null), image.getHeight(null)) : new Dimension();
		setMinimumSize(size);
		setMaximumSize(size);
		setPreferredSize(size);
		revalidate();
		repaint();
	}

	public void paintComponent(Graphics g) {
		if (image != null)
			g.drawImage(image, 0, 0, null);
	}
}