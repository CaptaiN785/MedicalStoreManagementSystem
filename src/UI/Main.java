package UI;

import javax.swing.UIManager;
//import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatIntelliJLaf;
//import com.formdev.flatlaf.FlatLightLaf;
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
//			FlatLightLaf.setup();
//			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception e) {
			System.out.println("Error while setting look and feel theme");
			e.printStackTrace();
		}
		new Frame();
	}
}
// garbage collector
// System.gc() is used to collect the garbage while running applications.
