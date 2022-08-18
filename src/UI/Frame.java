package UI;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

public class Frame extends JFrame{
	public Frame() {
		this.setLayout(new BorderLayout());
		this.setJMenuBar(new TopMenuBar());
		
//		Container container = getContentPane();
//		container.add(new AddMedicine());
		this.add(new AddMedicine(), BorderLayout.CENTER);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Medical store management");
		this.setVisible(true);
//		this.pack();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(700, 500));
	}
}
