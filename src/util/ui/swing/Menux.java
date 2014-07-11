package util.ui.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.basic.BasicMenuxUI;

public class Menux extends JComponent {

	private static final long serialVersionUID = 8325909042616932558L;

	private static final String uiClassID = "MenuxUI";

	public String getUIClassID() {
		return uiClassID;
	}

	private static final String[] none = new String[0];
	public static final boolean HORIZONTAL = true;
	public static final boolean VERTICAL = false;

	private String[] options = none;
	private int index = 0;

	private int padding = 0;

	private boolean orientation;

	private Insets margin;

	private Border optionBorder;
	private Insets optionMargin;
	private Dimension optionSize = new Dimension();

	public Menux() {
		updateUI();
	}

	/**/

	public void setSelectionIndex(int index) {
		this.index = index;
		reindex();
		revalidate();
		repaint();
	}

	public int getSelectionIndex() {
		return index;
	}

	/**/

	public void setOptions(String[] options) {
		String[] old = this.options;
		if (options == null)
			this.options = none;
		else
			this.options = Arrays.copyOf(options, options.length);
		firePropertyChange("options", old, options);
		reindex();
		repack();
		revalidate();
		repaint();
	}

	public <T extends Enum<?>> void setOptions(T[] options) {
		String[] old = this.options;
		if (options == null)
			this.options = none;
		else {
			this.options = new String[options.length];
			for (int i = 0; i < options.length; i++)
				this.options[i] = options[i].toString();
		}
		firePropertyChange("options", old, options);
		reindex();
		repack();
		revalidate();
		repaint();
	}

	public void setOptions(Class<? extends Enum<?>> options) {
		setOptions(options.getEnumConstants());
	}

	public String[] getOptions() {
		return Arrays.copyOf(options, options.length);
	}

	public int getNumOptions() {
		return options.length;
	}

	/**/

	public void setOrientation(boolean orientation) {
		boolean old = this.orientation;
		this.orientation = orientation;
		firePropertyChange("orientation", old, orientation);
		revalidate();
		repaint();
	}

	public boolean getOrientation() {
		return orientation;
	}

	/**/

	public void setOptionBorder(Border optionBorder) {
		this.optionBorder = optionBorder;
	}

	public Border getOptionBorder() {
		return optionBorder;
	}

	public Insets getOptionInsets() {
		if (optionBorder == null)
			return new InsetsUIResource(0, 0, 0, 0);
		return optionBorder.getBorderInsets(this);
	}

	/**/

	public void setMargin(Insets margin) {
		this.margin = margin;
	}

	public Insets getMargin() {
		return margin;
	}

	/**/

	public void setOptionMargin(Insets optionMargin) {
		this.optionMargin = optionMargin;
	}

	public Insets getOptionMargin() {
		return optionMargin;
	}

	/**/

	public void setPadding(int padding) {
		int old = this.padding;
		this.padding = padding;
		firePropertyChange("padding", old, padding);
		revalidate();
		repaint();
	}

	public int getPadding() {
		return padding;
	}

	/**/

	public Dimension getOptionSize() {
		return optionSize;
	}

	public void setFont(Font font) {
		super.setFont(font);
		repack();
	}

	/**/

	private void reindex() {
		int old = getSelectionIndex();
		index = Math.min(options.length - 1, Math.max(0, index));
		firePropertyChange("selected", old, getSelectionIndex());
	}

	private void repack() {
		FontMetrics fm = getFontMetrics(getFont());
		int maxWidth = 0;
		int maxHeight = 0;
		for (String option : options) {
			maxWidth = Math.max(maxWidth, fm.stringWidth(option));
			maxHeight = Math.max(maxHeight, fm.getHeight());
		}
		Insets inset = getOptionInsets();
		optionSize.setSize(maxWidth + optionMargin.left + optionMargin.right + inset.left + inset.right, maxHeight + optionMargin.top + optionMargin.bottom
				+ inset.top + inset.bottom);
	}

	/**/

	public void updateUI() {
		setUI((BasicMenuxUI) UIManager.getUI(this));
	}
}
