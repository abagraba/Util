package util.ui.swing;

import java.awt.Graphics;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;



public class FileSelectorUI extends ComponentUI {

	File	root;
	File[]	subfiles;

	public FileSelectorUI(File root) {
		this.root = root;
		subfiles = root.listFiles();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		int hw = c.getWidth() / 2;
		int hh = c.getHeight() / 2;

		FileOrb orb = new FileOrb(root);
		orb.orbStyle.drawOrb(g, hw, hh, 20);
		for (int i = 0; i < subfiles.length; i++) {
			double theta = (double) i / subfiles.length * 2 * Math.PI;
			FileOrb o = new FileOrb(subfiles[i]);
			System.out.println(subfiles[i]);
			o.orbStyle.drawOrb(g, hw + (int) (50 * Math.cos(theta)), hh + (int) (50 * Math.sin(theta)), 20);
		}

	}

}
