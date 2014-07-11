package javax.swing.plaf.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.UIResource;

import util.ui.swing.Menux;

public class BasicMenuxUI extends ComponentUI {

	public static void installLAF(UIDefaults defaults) {
		Object dialogPlain12 = new FontUIResource(Font.DIALOG, Font.PLAIN, 12);
		Object inset5 = new InsetsUIResource(5, 5, 5, 5);
		Object inset12 = new InsetsUIResource(12, 12, 12, 12);

		Object[] values = new Object[] { prefix + "font", dialogPlain12, prefix + "background", defaults.getColor("control"), prefix + "foreground",
				defaults.getColor("controlText"), prefix + "margin", inset12, prefix + "optionMargin", inset5

		};
		defaults.putDefaults(values);
	}

	private static BasicMenuxUI ui;
	protected final static String prefix = "Menux.";

	public Dimension getPreferredSize(JComponent c) {
		Menux menu = (Menux) c;
		Insets inset = menu.getInsets();
		Insets margin = menu.getMargin();

		int width = inset.left + inset.right + margin.left + margin.right;
		int height = inset.top + inset.bottom + margin.top + margin.bottom;
		int count = menu.getNumOptions();

		int padding = menu.getPadding();
		if (menu.getOrientation())
			width += count * (getOptionWidth(menu) + padding) - padding;
		else
			height += count * (getOptionHeight(menu) + padding) - padding;

		return new Dimension(width, height);
	}

	public Dimension getMinimumSize(JComponent c) {
		return getPreferredSize(c);
	}

	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);
		Menux menu = (Menux) c;
		Insets inset = menu.getInsets();
		Graphics gg = g.create(inset.left, inset.top, menu.getWidth() - inset.left - inset.right, menu.getHeight() - inset.top - inset.bottom);
		paintOptions(gg, menu);
		paintText(gg, menu);
		gg.dispose();
	}

	private void paintOptions(Graphics g, Menux menu) {
		String[] options = menu.getOptions();

		g.setColor(menu.getForeground());

		for (int i = 0; i < options.length; i++) {
			Rectangle bounds = getOptionBounds(menu, i);
			paintOption(g, menu.getForeground(), menu.getBackground(), bounds, i == menu.getSelectionIndex());
		}
	}

	private void paintOption(Graphics g, Color foreground, Color background, Rectangle bounds, boolean selected) {
		int arc = 3;
		if (selected) {
			g.setColor(background);
			g.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arc, arc);
			g.setColor(foreground);
		}
		g.drawRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arc, arc);
	}

	private void paintText(Graphics g, Menux menu) {
		
		
		
		g.setColor(menu.getForeground());
		g.setFont(menu.getFont());

		FontMetrics metrics = g.getFontMetrics();
		String[] options = menu.getOptions();
		for (int i = 0; i < options.length; i++) {
			Rectangle bounds = getOptionBounds(menu, i);
			g.setColor(Color.red);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
			g.setColor(menu.getForeground());
			paintText(g, metrics, options[i], bounds);
		}
	}

	private void paintText(Graphics g, FontMetrics metrics, String text, Rectangle bounds) {
		Rectangle2D stringBounds = metrics.getStringBounds(text, g);
		int xoffset = (int) ((bounds.getWidth() - stringBounds.getWidth()) / 2);
		int yoffset = (int) ((bounds.getHeight() - stringBounds.getHeight()) / 2 + metrics.getAscent());
		g.drawString(text, bounds.x + xoffset, bounds.y + yoffset);
	}

	private Rectangle getOptionBounds(Menux menu, int index) {
		int x = (menu.getOrientation() ? index * (getOptionWidth(menu) + menu.getPadding()) : 0);
		int y = (menu.getOrientation() ? 0 : index * (getOptionHeight(menu) + menu.getPadding()));
		return new Rectangle(x, y, getOptionWidth(menu), getOptionHeight(menu));
	}

	private int getOptionWidth(Menux menu) {
		return menu.getOptionSize().width;
	}

	private int getOptionHeight(Menux menu) {
		return menu.getOptionSize().height;
	}

	/**/

	public static BasicMenuxUI createUI(JComponent c) {
		if (ui == null) {
			ui = new BasicMenuxUI();
		}
		return ui;
	}

	public void installUI(JComponent c) {
		Menux menu = (Menux) c;
		installDefaults(menu);
	}

	public void uninstallUI(JComponent c) {
		Menux menu = (Menux) c;
		uninstallDefaults(menu);
	}

	protected void installDefaults(Menux m) {
		LookAndFeel.installColorsAndFont(m, prefix + "background", prefix + "foreground", prefix + "font");
		LookAndFeel.installProperty(m, "opaque", Boolean.FALSE);
		/**/
		LookAndFeel.installBorder(m, prefix + "border");
		if (m.getOptionBorder() == null || m.getOptionBorder() instanceof UIResource) {
			m.setOptionBorder(UIManager.getBorder(prefix + "optionBorder"));
		}
		/**/
		if (m.getMargin() == null || (m.getMargin() instanceof UIResource)) {
			m.setMargin(UIManager.getInsets(prefix + "margin"));
		}
		if (m.getOptionMargin() == null || (m.getOptionMargin() instanceof UIResource)) {
			m.setOptionMargin(UIManager.getInsets(prefix + "optionMargin"));
		}
	}

	protected void uninstallDefaults(Menux m) {
		LookAndFeel.uninstallBorder(m);
	}
}
