package util.ui;

import java.io.File;

import javax.swing.JFrame;



public class Test extends JFrame {

	public Test() {
		getContentPane().add(new FileSelector(new File("src/")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		new Test();
	}
}
