package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel{
	JLabel lbHeading, lbUsername, lbPassword, lbLogin;
	JTextField tfUsername;
	JPasswordField pfPassword;
	JButton btnLogin;
	JPanel loginPanel;
	
	Frame parentFrame;
	Login(Frame parentFrame){
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		Font font = new Font("cambria", Font.PLAIN, 16);
		
		lbHeading = new JLabel("Medical Store Management System");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 25));
		JPanel headingPanel = new JPanel();
		headingPanel.add(lbHeading);
		headingPanel.setBorder(new EmptyBorder(new Insets(30, 0, 0, 0)));
		this.add(headingPanel, BorderLayout.NORTH);
		
		// Login panel
		lbLogin = new JLabel("Login");
		lbLogin.setFont(new Font("cambria", Font.BOLD, 22));
		
		lbUsername = new JLabel("Enter username");
		lbUsername.setFont(font);
		lbPassword = new JLabel("Enter password");
		lbPassword.setFont(font);
		
		tfUsername = new JTextField(20);
		tfUsername.setFont(font);
		pfPassword = new JPasswordField(20);
		pfPassword.setFont(font);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(font);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnLogin.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String username = tfUsername.getText();
				String password = pfPassword.getText();
				
				if(username.equals("captain") && password.equals("captain")) {
					tfUsername.setText("");
					pfPassword.setText("");
					parentFrame.login();
				}
				else
					JOptionPane.showMessageDialog(getRootPane(), "Invalid credentials!");
			}
		});
		
		loginPanel = new JPanel(new BorderLayout());
//		loginPanel.add(loginLabelPanel, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setPreferredSize(getMinimumSize());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 10, 40, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 1; c.gridy = 0; formPanel.add(lbLogin, c);
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 0; c.gridy = 1; formPanel.add(lbUsername, c);
		c.gridx = 1; c.gridy = 1; formPanel.add(tfUsername, c);
		c.gridx = 0; c.gridy = 2; formPanel.add(lbPassword, c);
		c.gridx = 1; c.gridy = 2; formPanel.add(pfPassword, c);
		// space for button
		c.gridx = 1; c.gridy = 3; formPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 1; c.gridy = 4; formPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 1; c.gridy = 5; formPanel.add(btnLogin, c);
		
		loginPanel.add(formPanel, BorderLayout.CENTER);
		this.add(loginPanel, BorderLayout.CENTER);
	}
}
