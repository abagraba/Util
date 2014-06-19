package util.ui.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;



public class MapView extends View implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final long	serialVersionUID	= 5966196209358790787L;

	public static final int		MULTIPLICATIVE_ZOOM	= 0;
	public static final int		ADDITIVE_ZOOM		= 1;

	private int					zoomMode			= MULTIPLICATIVE_ZOOM;
	private float				zoomFactor			= 1.3f;

	private float				minZoom				= 1f / 256;
	private float				maxZoom				= 256;

	private Map					map;
	private float				zoom;
	private int					xPosition, yPosition;
	private int					width, height;

	protected int				mousex, mousey;

	public MapView() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		zoom = 1;
		repaint();
	}

	public void setZoomBounds(float minZoom, float maxZoom) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		setZoom(zoom);
	}

	public void setZoom(float zoom) {
		float pzoom = this.zoom;
		this.zoom = Math.min(Math.max(minZoom, zoom), maxZoom);
		xPosition *= this.zoom / pzoom;
		yPosition *= this.zoom / pzoom;
	}

	public void setMap(Map map) {
		this.map = map;
		repaint();
	}

	public float getZoom() {
		return zoom;
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
		return (int) (map.getWidth() * Math.pow(2, zoom));
	}

	private int imageHeight() {
		return (int) (map.getHeight() * Math.pow(2, zoom));
	}

	@Override
	public void drawView(Graphics g) {
		clamp();
		int x = width < getWidth() ? (getWidth() - width) / 2 : 0;
		int y = height < getHeight() ? (getHeight() - height) / 2 : 0;
		if (map != null)
			map.drawMap(g.create(x, y, width, height), xPosition, yPosition, width, height, zoom);
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
