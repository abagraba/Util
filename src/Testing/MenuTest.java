package Testing;

import javax.swing.UIManager;

import util.ui.swing.LAFUtil;
import util.ui.swing.Menux;

public class MenuTest {

	public static void main(String[] args) {
		LAFUtil.installUI(Menux.class);
		
		
		SimpleFrame frame = new SimpleFrame(800, 600, true);

		Menux m = new Menux();
		System.out.println(UIManager.getLookAndFeelDefaults().get("Menux.font"));
		System.out.println(m.getFont());
		m.setOptions(new String[] { "A", "B" });
		frame.add(m);

		frame.open();
	}

}
