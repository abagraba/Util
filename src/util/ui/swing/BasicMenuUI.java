package util.ui.swing;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;

public class BasicMenuUI extends ComponentUI implements PropertyChangeListener {

	private Dimension optionSize = new Dimension();
	private Insets optionInset = new Insets(5, 5, 5, 5);

	public Dimension getPreferredSize(JComponent c) {
		Menu menu = (Menu) c;
		Insets inset = menu.getInsets();


		int optionWidth = optionSize.width + optionInset.left + optionInset.right;
		int optionHeight = optionSize.height + optionInset.top + optionInset.bottom;

		int width = inset.left + inset.right;
		int height = inset.top + inset.bottom;
		int count = menu.getNumOptions();

		if (menu.getOrientation())
			width += count * (optionWidth + menu.getPadding()) - menu.getPadding();
		else
			height += count * (optionHeight + menu.getPadding() - menu.getPadding());

		return new Dimension(width, height);
	}

	public Dimension getMinimumSize(JComponent c) {
		return getPreferredSize(c);
	}

	public void paint(Graphics g, JComponent c) {
		Menu menu = (Menu) c;

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
