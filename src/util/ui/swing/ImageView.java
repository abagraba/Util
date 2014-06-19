package util.ui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;



public class ImageView extends View implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long	serialVersionUID	= 5966196209358790787L;

	public static final int		MULTIPLICATIVE_ZOOM	= 0;
	public static final int		ADDITIVE_ZOOM		= 1;

	private int					zoomMode			= MULTIPLICATIVE_ZOOM;
	private float				zoomFactor			= 1.3f;

	private float				minZoom				= 1f / 256;
	private float				maxZoom				= 256;

	private BufferedImage		image;
	private float				zoom;
	private int					xPosition, yPosition;
	private int					width, height;

	private int					mousex, mousey;

	public ImageView(BufferedImage image) {
		this.image = image;
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		zoom = 1;
		repaint();
	}

	public void setZoomBounds(float minZoom, float maxZoom) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
	}

	public void setZoom(float zoom) {
		float pzoom = this.zoom;
		this.zoom = Math.min(Math.max(minZoom, zoom), maxZoom);
		xPosition *= this.zoom / pzoom;
		yPosition *= this.zoom / pzoom;
	}

	public void setZoomFactor(float zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public void setZoomMode(int zoomMode) {
		this.zoomMode = zoomMode;
	}

	public void zoomIn(int xoffset, int yoffset) {
		xPosition += xoffset;
		yPosition += yoffset;
		switch (zoomMode) {
			case ADDITIVE_ZOOM:
				setZoom(zoom + zoomFactor);
				break;
			case MULTIPLICATIVE_ZOOM:
				setZoom(zoom * zoomFactor);
				break;
		}
		xPosition -= xoffset;
		yPosition -= yoffset;
	}

	public void zoomOut(int xoffset, int yoffset) {
		xPosition += xoffset;
		yPosition += yoffset;
		switch (zoomMode) {
			case ADDITIVE_ZOOM:
				setZoom(zoom - zoomFactor);
				break;
			case MULTIPLICATIVE_ZOOM:
				setZoom(zoom / zoomFactor);
				break;
		}
		xPosition -= xoffset;
		yPosition -= yoffset;
	}

	private void clamp() {
		width = Math.min(getWidth(), imageWidth());
		height = Math.min(getHeight(), imageHeight());
		xPosition = Math.max(0, Math.min(xPosition, imageWidth() - getWidth()));
		yPosition = Math.max(0, Math.min(yPosition, imageHeight() - getHeight()));
	}

	private int imageWidth() {
		return (int) (image.getWidth() * zoom);
	}

	private int imageHeight() {
		return (int) (image.getHeight() * zoom);
	}

	@Override
	public void drawView(Graphics g) {
		clamp();
		int x = width < getWidth() ? (getWidth() - width) / 2 : 0;
		int y = height < getHeight() ? (getHeight() - height) / 2 : 0;
		// Position and dimension and divided by zoom separately so that rounding issues do not cause dimensions to
		// change wildly when translating image at high zooms.
		g.drawImage(image, x, y, x + width, y + height, (int) (xPosition / zoom), (int) (yPosition / zoom), (int) (xPosition / zoom + width / zoom),
				(int) (yPosition / zoom + height / zoom), null);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawView(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		xPosition += mousex - e.getX();
		yPosition += mousey - e.getY();
		mousex = e.getX();
		mousey = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0)
			zoomIn(e.getX(), e.getY());
		else
			zoomOut(e.getX(), e.getY());
		repaint();
	}

}
