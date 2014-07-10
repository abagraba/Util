package util.ui.swing;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

public class BasicMenuUI extends ComponentUI implements PropertyChangeListener {

	protected Dimension optionSize = new Dimension();
	protected Insets optionInset = new Insets(5, 5, 5, 5);

	public static BasicMenuUI createUI(JComponent c) {
		return new BasicMenuUI();
	}

	public Dimension getPreferredSize(JComponent c) {
		Menu menu = (Menu) c;
		Insets inset = menu.getInsets();

		int width = inset.left + inset.right;
		int height = inset.top + inset.bottom;
		int count = menu.getNumOptions();

		if (menu.getOrientation())
			width += count * (getOptionWidth() + menu.getPadding()) - menu.getPadding();
		else
			height += count * (getOptionHeight() + menu.getPadding() - menu.getPadding());

		return new Dimension(width, height);
	}


	public Dimension getMinimumSize(JComponent c) {
		return getPreferredSize(c);
	}

	public void paint(Graphics g, JComponent c) {
		Menu menu = (Menu) c;
		paintOptions(g, menu);
		paintText(g, menu);
	}

	private void paintOptions(Graphics g, Menu menu) {
		String[] options = menu.getOptions();
		Insets inset = menu.getInsets();

		g.setColor(menu.getForeground());

		int x = inset.left;
		int y = inset.top;
		int arc = 3;
		for (int i = 0; i < options.length; i++) {
			if (menu.getOrientation()) {
				if (menu.getSelectionIndex() == i) {
					g.setColor(menu.getBackground());
					g.fillRoundRect(x + i * (optionSize.width + menu.getPadding()), y, optionSize.width, optionSize.height, arc, arc);
					g.setColor(menu.getForeground());
				}
				g.drawRoundRect(x + i * (optionSize.width + menu.getPadding()), y, optionSize.width, optionSize.height, arc, arc);
			} else {
				if (menu.getSelectionIndex() == i) {
					g.setColor(menu.getBackground());
					g.fillRoundRect(x + i * (optionSize.width + menu.getPadding()), y, optionSize.width, optionSize.height, arc, arc);
					g.setColor(menu.getForeground());
				}
				g.drawRoundRect(x, y + i * (optionSize.width + menu.getPadding()), optionSize.width, optionSize.height, arc, arc);
			}
		}

	}

	private void paintText(Graphics g, Menu menu) {
		g.setColor(menu.getForeground());

		String[] options = menu.getOptions();
		Insets inset = menu.getInsets();
		FontMetrics fm = menu.getFontMetrics(menu.getFont());

		for (int i = 0; i < options.length; i++) {
			Rectangle bounds = getOptionBounds(menu, i);
			int delta = (bounds.width - (int) fm.getStringBounds(options[i], g).getWidth()) / 2;
			g.drawString(options[i], inset.left + bounds.x + delta, inset.top + bounds.y);
		}
	}

	private void paintText(Graphics g, String text, Rectangle bounds){
		FontMetrics metrics = g.getFontMetrics();
		Rectangle2D stringBounds = metrics.getStringBounds(text, g);
		int xoffset = (int) (bounds.getCenterX() - stringBounds.getCenterX());
		int yoffset = (int) (bounds.getCenterY() - stringBounds.getCenterY() + metrics.getAscent());
		g.drawString(text, bounds.x + xoffset, bounds.y + yoffset);
	}
	
	private Rectangle getOptionBounds(Menu menu, int index) {
		int x = (menu.getOrientation() ? index * (getOptionWidth() + menu.getPadding()) : 0);
		int y = (menu.getOrientation() ? 0 : index * (getOptionHeight() + menu.getPadding()));
		return new Rectangle(x, y, getOptionWidth(), getOptionHeight());
	}
	
	private int getOptionWidth() {
		return optionInset.left + optionInset.right + optionSize.width;
	}

	private int getOptionHeight() {
		return optionInset.top + optionInset.bottom + optionSize.height;
	}

	/**/
	public void installUI(JComponent c) {
		LookAndFeel.installColorsAndFont(c, "Menu.background", "Menu.foreground", "Menu.font");
		LookAndFeel.installProperty(c, "opaque", Boolean.FALSE);
		c.addPropertyChangeListener(this);
	}

	public void uninstallUI(JComponent c) {
		c.removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Menu menu = (Menu) evt.getSource();
		if (evt.getPropertyName().equals("options")) {
			FontMetrics fm = menu.getFontMetrics(menu.getFont());
			int maxWidth = 0;
			int maxHeight = 0;
			for (String option : menu.getOptions()) {
				maxWidth = Math.max(maxWidth, fm.stringWidth(option));
				maxHeight = Math.max(maxHeight, fm.getHeight());
			}
			optionSize.setSize(maxWidth, maxHeight);
		}
	}
}
