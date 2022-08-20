package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SearchSupplier extends JPanel{
	JLabel lbHeading;
	JPanel headingPanel, infoPanel;
	
	JLabel lbSid, lbName, lbDirector, lbPhone, lbEmail, lbAddress, lbRegDate;
	JTextField tfSid, tfName, tfDirector, tfPhone, tfEmail, tfAddress, tfRegDate;
	
	JButton btnEdit, btnClear, btnDelete;
	
	public SearchSupplier(Frame parentFrame) {
		// Panel settings
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		// Heading panel
		headingPanel = new JPanel();
		lbHeading = new JLabel("Search suppliers");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 30));
		headingPanel.add(lbHeading);
		this.add(headingPanel, BorderLayout.NORTH);
		
		searchSid(0, parentFrame);
	}
	public void searchSid(int sid, Frame parentFrame) {
		
		// working on infoPanel Label
		lbSid = new JLabel("Supplier ID: ");
		lbName = new JLabel("Name: ");
		lbDirector = new JLabel("Director: ");
		lbPhone = new JLabel("Phone: ");
		lbEmail = new JLabel("Email: ");
		lbRegDate = new JLabel("Reg. Date: ");
		lbAddress = new JLabel("Address: ");
		styleLb(lbSid, lbName, lbDirector, lbPhone, lbEmail, lbRegDate, lbAddress);
		
		// Working on infoPanel textField
		tfSid = new JTextField(20);
		tfName = new JTextField(20);
		tfDirector = new JTextField(20);
		tfPhone = new JTextField(20);
		tfEmail = new JTextField(20);
		tfRegDate = new JTextField(20);
		tfAddress = new JTextField(20);
		styleTf(tfSid, tfName, tfDirector, tfPhone, tfEmail, tfRegDate, tfAddress);
		
		// Now working on info panel
		infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; infoPanel.add(lbSid, c);
		c.gridx = 1; c.gridy = 0; infoPanel.add(tfSid, c);
		c.gridx = 0; c.gridy = 1; infoPanel.add(lbName, c);
		c.gridx = 1; c.gridy = 1; infoPanel.add(tfName, c);
		c.gridx = 0; c.gridy = 2; infoPanel.add(lbDirector, c);
		c.gridx = 1; c.gridy = 2; infoPanel.add(tfDirector, c);
		c.gridx = 0; c.gridy = 3; infoPanel.add(lbPhone, c);
		c.gridx = 1; c.gridy = 3; infoPanel.add(tfPhone, c);
		c.gridx = 0; c.gridy = 4; infoPanel.add(lbEmail, c);
		c.gridx = 1; c.gridy = 4; infoPanel.add(tfEmail, c);
		c.gridx = 0; c.gridy = 5; infoPanel.add(lbRegDate, c);
		c.gridx = 1; c.gridy = 5; infoPanel.add(tfRegDate, c);
		c.gridx = 0; c.gridy = 6; infoPanel.add(lbAddress, c);
		c.gridx = 1; c.gridy = 6; infoPanel.add(tfAddress, c);
		this.add(infoPanel, BorderLayout.CENTER);
		disableField();
		
		// Adding two empty layout for spaces
		c.gridx = 0; c.gridy = 7; infoPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 0; c.gridy = 8; infoPanel.add(Box.createHorizontalBox(), c);
		
		// Now adding button for actions
		btnEdit = new JButton("Edit");
		btnEdit.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnEdit.setFont(new Font("cambria", Font.PLAIN, 16));
		btnEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnClear = new JButton("Clear");
		btnClear.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnClear.setFont(new Font("cambria", Font.PLAIN, 16));
		btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		btnDelete = new JButton("Delete Supplier");
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.RED);
		btnDelete.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnDelete.setFont(new Font("cambria", Font.ITALIC, 16));
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int response = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
				if(response == JOptionPane.YES_OPTION) {
					System.out.println("Deleteing supplier");
				}else {
					System.out.println("Not deleting the supplier");
				}
			}
		});
		
		Box btnLayout = Box.createHorizontalBox();
		btnLayout.add(btnEdit);
		c.gridx = 1; c.gridy = 9; infoPanel.add(btnLayout, c);
		c.gridx = 1; c.gridy = 10; infoPanel.add(Box.createVerticalBox().add(btnDelete), c);
		
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String command = ae.getActionCommand();
				if(command.equals("Edit")) {
					System.out.println("Edit");
					btnEdit.setText("Update");
					btnLayout.add(btnClear);
				}else {
					System.out.println("Updating");
					btnLayout.remove(btnClear);
					btnEdit.setText("Edit");
				}
				parentFrame.refreshDisplay();// Refresh the display
				// after doing all tasks.
			}
		});
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
	private void enableField() {
		tfName.setEnabled(true);
		tfDirector.setEnabled(true);
		tfPhone.setEnabled(true);
		tfEmail.setEnabled(true);
		tfAddress.setEnabled(true);
	}
	private void disableField() {
		tfSid.setEnabled(false);
		tfName.setEnabled(false);
		tfDirector.setEnabled(false);
		tfPhone.setEnabled(false);
		tfEmail.setEnabled(false);
		tfRegDate.setEnabled(false);
		tfAddress.setEnabled(false);
	}
}
