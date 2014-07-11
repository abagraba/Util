package Testing;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class BorderTest{

	
	public static void main(String [] args){
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(10,  10,  10,  10, Color.red));
		panel.setBackground(Color.blue);
		
		SimpleFrame frame = new SimpleFrame(800, 600, true, panel);
		frame.open();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
