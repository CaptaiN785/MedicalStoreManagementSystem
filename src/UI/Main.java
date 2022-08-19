package UI;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) {
		try {
//			UIManager.setLookAndFeel("javax.swing.plaf.synth.SynthMenuBarUI");
//			UIManager.setLookAndFeel ("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("javax.swing.plaf.metal");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel"); // error
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Frame();
	}
}
// garbage collector
// System.gc() is used to collect the garbage while running applications.
