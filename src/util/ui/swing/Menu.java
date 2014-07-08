package util.ui.swing;

import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JComponent;

public class Menu extends JComponent {

	private static final String[] none = new String[0];
	private static final Insets defInset = new Insets(5, 5, 5, 5);

	private static final boolean HORIZONTAL = true;
	private static final boolean VERTICAL = false;

	private String[] options = none;
	private int index = 0;

	private int padding = 0;

	protected boolean orientation;

	public Menu() {

	}

	/**/

	public int getSelection() {
		return index;
	}

	public String[] getOptions() {
		return Arrays.copyOf(options, options.length);
	}

	public int getNumOptions() {
		return options.length;
	}

	public boolean getOrientation() {
		return orientation;
	}

	public int getPadding() {
		return padding;
	}

	/**/

	public void setPadding(int padding) {
		int old = this.padding;
		this.padding = padding;
		firePropertyChange("padding", old, padding);
		revalidate();
		repaint();
	}

	public void setSelection(int index) {
		this.index = index;
		reindex();
		revalidate();
		repaint();
	}

	public void setOptions(String[] options) {
		String[] old = this.options;
		if (options == null)
			this.options = none;
		else
			this.options = Arrays.copyOf(options, options.length);
		firePropertyChange("options", old, options);
		reindex();
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
		revalidate();
		repaint();
	}

	public void setOptions(Class<? extends Enum<?>> options) {
		setOptions(options.getEnumConstants());
	}

	public void setHorizontal() {
		boolean old = orientation;
		orientation = HORIZONTAL;
		firePropertyChange("orientation", old, orientation);
		revalidate();
		repaint();
	}

	public void setVertical() {
		boolean old = orientation;
		orientation = VERTICAL;
		firePropertyChange("orientation", old, orientation);
		revalidate();
		repaint();
	}

	/**/

	private void reindex() {
		int old = getSelection();
		index = Math.min(options.length - 1, Math.max(0, index));
		firePropertyChange("selected", old, getSelection());
	}

	/**/

}
