package UI;

import javax.swing.*;

import Database.DatabaseInitialization;

import java.awt.BorderLayout;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Frame extends JFrame{
	
	protected JPanel currentPanel;
	Login login;
	TopMenuBar menuBar;
	public Frame() {
		this.setLayout(new BorderLayout());
		
		// First initialize the database table
		new DatabaseInitialization().InitializeTables();
		
		// Addding menubar
		menuBar = new TopMenuBar(this);
		this.setJMenuBar(menuBar);	
		menuBar.setVisible(false);
		
		// Addding login panel
		login = new Login(this);
		this.add(login, BorderLayout.CENTER);
		this.currentPanel  = login;
		
		// This is a frame settings.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Medical store management");
		this.setVisible(true);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(800, 600));
	}
	public void refreshDisplay() {
		validate();
		revalidate();
	}
	protected void login() {
		this.remove(currentPanel);
		menuBar.setVisible(true);
		refreshDisplay();
	}
	protected void logout() {
		menuBar.setVisible(false);
		this.remove(currentPanel);
		this.add(login);
		this.currentPanel = login;
		refreshDisplay();
	}
}
