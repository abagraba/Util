package Testing;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class SimpleFrame extends JFrame {

	public SimpleFrame(int w, int h, boolean resizeable) {
		this(w, h, resizeable, null);
	}

	public SimpleFrame(int w, int h, boolean resizeable, Container pane) {
		super();
		Dimension d = new Dimension(w, h);
		setMinimumSize(d);
		setPreferredSize(d);
		if (!resizeable)
			setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		if (pane != null)
			setContentPane(pane);
	}

	public void open() {
		pack();
		setVisible(true);
	}

	public void addComponent(JComponent comp) {
		getContentPane().add(comp);
	}

	public void addComponent(JComponent comp, Object arg) {
		getContentPane().add(comp, arg);
	}

	public void setLayoutManager(LayoutManager layout) {
		getContentPane().setLayout(layout);
	}

}
