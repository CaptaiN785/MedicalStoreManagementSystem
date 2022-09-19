package UI;

import javax.swing.UIManager;
public class Main {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.out.println("Error while setting look and feel");
			e.printStackTrace();
		}
		new Frame();
	}
}

// Total lines of code is around : 3500