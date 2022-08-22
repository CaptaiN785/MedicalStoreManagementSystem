package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Database.DatabaseOperations;

public class SearchSupplier extends JPanel implements ActionListener{
	JLabel lbHeading;
	JPanel headingPanel, infoPanel = null;
	
	JLabel lbSid, lbName, lbDirector, lbPhone, lbEmail, lbAddress, lbRegDate;
	JTextField tfSid, tfName, tfDirector, tfPhone, tfEmail, tfAddress, tfRegDate;
	
	JButton btnEdit, btnClear, btnDelete, btnCancel;
	
	JPanel searchPanel;
	JComboBox<ArrayList<String>> cbSupplier;
	JButton btnSearch;
	JLabel lbSearch;
	Frame parentFrame = null;
	
	ArrayList<String> suppliers = null;
	ArrayList<Integer> supplier_index = null;
	
	String [] info = null;
	
	public SearchSupplier(Frame parentFrame, boolean isSearchable, int sid) {
		// Panel settings
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		
		// Heading panel
		
		initUI(parentFrame, this, isSearchable);
		
		if(isSearchable) {
			headingPanel = new JPanel();
			lbHeading = new JLabel("Search suppliers");
			lbHeading.setFont(new Font("cambria", Font.BOLD, 30));
			headingPanel.add(lbHeading);

			searchPanel = new JPanel();
			searchPanel.setBorder(new EmptyBorder(new Insets(20, 20, 20, 20)));
			
			lbSearch = new JLabel("Search supplier");
			styleLb(lbSearch);
			
			// Here need all the supplier list.
			Object[] res = new DatabaseOperations().getSuppliers();
			this.suppliers = (ArrayList<String>)res[0];
			this.supplier_index = (ArrayList<Integer>)res[1];
			
			cbSupplier = new JComboBox(suppliers.toArray());
			cbSupplier.setFont(new Font("cambria", Font.PLAIN, 16));
			cbSupplier.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
			
			btnSearch = new JButton("Search");
			btnSearch.setFont(new Font("cambria", Font.PLAIN, 16));
			btnSearch.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
			btnSearch.addActionListener(this);
			
			searchPanel.add(lbSearch);
			searchPanel.add(cbSupplier);
			searchPanel.add(btnSearch);
			
			Box verticalLayout = Box.createVerticalBox();
			verticalLayout.add(headingPanel);
			verticalLayout.add(searchPanel);
			this.add(verticalLayout, BorderLayout.NORTH);
			
		}else {
			this.info = new DatabaseOperations().getSupplierDetails(sid);
			if(info.length == 0) {
				JOptionPane.showMessageDialog(getRootPane(), "Supplier not found");
			}else {
				setInformation(info);
				infoPanel.setVisible(true);
			}
		}
	}
	// Implementing the methods for global actionListener
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnSearch) {
			int index = cbSupplier.getSelectedIndex();
			if(index == 0) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a supplier.");
			}else {
				int sid = this.supplier_index.get(index);
				this.info = new DatabaseOperations().getSupplierDetails(sid);
				if(info.length == 0) {
					JOptionPane.showMessageDialog(getRootPane(), "Supplier not found");
					return;
				}
				setInformation(info);
				infoPanel.setVisible(true);
				this.validate();
				this.revalidate();
				parentFrame.refreshDisplay();
			}
		}
	}
	
	public void initUI(Frame parentFrame, SearchSupplier parentPanel, boolean isSearchable) {	
		
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
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnCancel.setFont(new Font("cambria", Font.PLAIN, 16));
		btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		btnClear = new JButton("Clear");
		btnClear.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnClear.setFont(new Font("cambria", Font.PLAIN, 16));
		btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearFields();
			}
		});
		
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
					boolean isDeleted = new DatabaseOperations().deleteSupplier(Integer.parseInt(tfSid.getText()));
					if(isDeleted) {
						JOptionPane.showMessageDialog(getRootPane(), "Supplier deleted succesfully");
						parentPanel.remove(infoPanel);
						parentPanel.validate();
						parentPanel.revalidate();
					}else {
						JOptionPane.showMessageDialog(getRootPane(), "Database error!");
					}
				}else {
					System.out.println("delete cancel");
				}
			}
		});
		
		Box btnLayout = Box.createHorizontalBox();
		if(isSearchable) {
			btnLayout.add(btnEdit);
			c.gridx = 1; c.gridy = 9; infoPanel.add(btnLayout, c);
			c.gridx = 1; c.gridy = 10; infoPanel.add(Box.createVerticalBox().add(btnDelete), c);
			
		}
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String command = ae.getActionCommand();
				if(command.equals("Edit")) {
					enableField();
					btnEdit.setText("Update");
					btnLayout.add(btnCancel);
					btnLayout.add(Box.createHorizontalStrut(30));
					btnLayout.add(btnClear);
				}else {
					if(validateForm()) {
						int res = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
						if(res == JOptionPane.YES_OPTION) {
							boolean isUpdated = new DatabaseOperations().updateSupplier(tfName.getText(),
									tfDirector.getText(), tfAddress.getText(), 
									tfPhone.getText() , tfEmail.getText() , Integer.parseInt(tfSid.getText()));
							if(isUpdated) {
								btnLayout.remove(btnClear);
								btnLayout.remove(btnCancel);
								btnEdit.setText("Edit");
								disableField();
								JOptionPane.showMessageDialog(getRootPane(), "Supplier updated.");
							}else {
								JOptionPane.showMessageDialog(getRootPane(), "Database error.");
							}
						}
					}else {
						JOptionPane.showMessageDialog(getRootPane(), "Please check your details.");
					}
				}
				parentFrame.refreshDisplay();// Refresh the display
				// after doing all tasks.
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				setInformation(info);
				btnLayout.remove(btnClear);
				btnLayout.remove(btnCancel);
				btnEdit.setText("Edit");
				disableField();
			}
		});
		infoPanel.setVisible(false);
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
	private void setInformation(String[] info) {
		// Providing the values to each fields
		tfSid.setText(info[0]);
		tfName.setText(info[1]);
		tfDirector.setText(info[2]);
		tfPhone.setText(info[3]);
		tfEmail.setText(info[4]);
		tfRegDate.setText(info[6]);
		tfAddress.setText(info[5]);
	}
	
	private void enableField() {
		tfName.setEditable(true);
		tfDirector.setEditable(true);
		tfPhone.setEditable(true);
		tfEmail.setEditable(true);
		tfAddress.setEditable(true);
	}
	
	private void disableField() {
		tfSid.setEditable(false);
		tfName.setEditable(false);
		tfDirector.setEditable(false);
		tfPhone.setEditable(false);
		tfEmail.setEditable(false);
		tfRegDate.setEditable(false);
		tfAddress.setEditable(false);
	}
	private void clearFields() {
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
}
