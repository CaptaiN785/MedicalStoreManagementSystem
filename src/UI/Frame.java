package UI;

import javax.swing.*;

import java.awt.BorderLayout;
//import java.awt.Container;
import java.awt.Dimension;
import java.util.Stack;

@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	protected JPanel currentPanel;
	public Frame() {
		this.setLayout(new BorderLayout());
		this.setJMenuBar(new TopMenuBar(this));		
		
		AddSupplier md = new AddSupplier(this);
		this.add(md, BorderLayout.CENTER);
		currentPanel = md;
		
		
		// This is a frame settings.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Medical store management");
		this.setVisible(true);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(700, 500));
	}
	
	public void refreshDisplay() {
		validate();
		revalidate();
	}
}
