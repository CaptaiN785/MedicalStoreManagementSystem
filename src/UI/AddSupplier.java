package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Database.DatabaseInitialization;
import Database.DatabaseOperations;
public class AddSupplier extends JPanel{
	
	JTextField tfName, tfDirector, tfPhone, tfEmail, tfAddress;
	JLabel lbName, lbDirector, lbPhone, lbEmail, lbAddress, lbHeading;
	
	JPanel formPanel, headingPanel;
	
	JButton btnAddSupplier, btnClear;
	
	public AddSupplier(Frame parentFrame) {
		// Adding layout this panel
		this.setLayout(new BorderLayout());
		
		
		headingPanel = new JPanel();
		lbHeading = new JLabel("Add Supplier");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 30));
		headingPanel.add(lbHeading);
		this.add(headingPanel, BorderLayout.NORTH);

		// all labels for taking input
		lbName = new JLabel("Enter name");
		lbDirector = new JLabel("Director name");
		lbPhone = new JLabel("Mobile no.");
		lbEmail = new JLabel("Email address");
		lbAddress = new JLabel("Address");
		styleLb(lbName, lbDirector, lbPhone, lbEmail, lbAddress);
		
		// All textField
		tfName = new JTextField(20);
		tfDirector = new JTextField(20);
		tfPhone = new JTextField(20);
		tfEmail = new JTextField(20);
		tfAddress = new JTextField(20);
		styleTf(tfName, tfDirector, tfPhone, tfEmail, tfAddress);
		
		// Defining the form panel
		formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; formPanel.add(lbName, c);
		c.gridx = 1; c.gridy = 0; formPanel.add(tfName, c);
		c.gridx = 0; c.gridy = 1; formPanel.add(lbDirector, c);
		c.gridx = 1; c.gridy = 1; formPanel.add(tfDirector, c);
		c.gridx = 0; c.gridy = 2; formPanel.add(lbPhone, c);
		c.gridx = 1; c.gridy = 2; formPanel.add(tfPhone, c);
		c.gridx = 0; c.gridy = 3; formPanel.add(lbEmail, c);
		c.gridx = 1; c.gridy = 3; formPanel.add(tfEmail, c);
		c.gridx = 0; c.gridy = 4; formPanel.add(lbAddress, c);
		c.gridx = 1; c.gridy = 4; formPanel.add(tfAddress, c);
		
		// Adding two empty spaces to form panel to provide space betweent field and buttons,
		c.gridx = 1; c.gridy = 5; formPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 1; c.gridy = 6; formPanel.add(Box.createHorizontalBox(), c);
		
		// Defining the buttons
		btnAddSupplier = new JButton("Add supplier");
		btnAddSupplier.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnAddSupplier.setFont(new Font("cambria", Font.PLAIN, 16));
		btnAddSupplier.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(validateForm()) {
					boolean result = new DatabaseOperations().addSupplier(tfName.getText(),
							tfDirector.getText(), tfAddress.getText(),
							tfPhone.getText(),tfEmail.getText());
					clearForm();
					if(result) {
						JOptionPane.showMessageDialog(getRootPane(), "Supplier added");
					}else{
						JOptionPane.showMessageDialog(getRootPane(), "Supplier added");						
					}
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Please check your details.");
				}
			}
		});
		
		btnClear = new JButton("Clear");
		btnClear.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnClear.setFont(new Font("cambria", Font.PLAIN, 16));
		btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClear.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearForm();
			}
		});
		
		Box btnLayout = Box.createHorizontalBox();
		btnLayout.add(btnAddSupplier);
		btnLayout.add(Box.createHorizontalStrut(100));
		btnLayout.add(btnClear);
		c.gridx = 1; c.gridy = 7; formPanel.add(btnLayout, c);
		
		
		Box verticalLayout = Box.createVerticalBox();
		verticalLayout.add(formPanel);
		verticalLayout.add(Box.createVerticalStrut(200));
		
		this.add(verticalLayout, BorderLayout.CENTER);
		this.setVisible(true);
		
	}
	private void clearForm() {
		tfName.setText("");
		tfDirector.setText("");
		tfPhone.setText("");
		tfEmail.setText("");
		tfAddress.setText("");
	}
	
	private boolean validateForm() {
		String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
		return Pattern.matches("[a-zA-z0-9\s-]+", tfName.getText()) &&
				Pattern.matches("[a-zA-Z\s]+", tfDirector.getText()) &&
				Pattern.matches("[0-9]+", tfPhone.getText()) && 
				(tfPhone.getText().length() == 10) && 
				Pattern.matches(emailPattern, tfEmail.getText()) &&
				Pattern.matches("[a-zA-Z0-9\s,-]+", tfAddress.getText());
	}
	private void styleTf(JTextField ...fields) {
		for(JTextField field : fields) {
			field.setFont(new Font("cambria", Font.PLAIN, 16));
		}
	}
	private void styleLb(JLabel ...labels) {
		for(JLabel label: labels) {
			label.setFont(new Font("cambria", Font.PLAIN, 16));
		}
	}
}	
