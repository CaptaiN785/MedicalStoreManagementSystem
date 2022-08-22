package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Database.DatabaseOperations;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SearchMedicine extends JPanel implements ActionListener{
	
	
	JPanel infoPanel, headingPanel, searchPanel;
	JLabel lbHeading, lbSearch;
	
	JComboBox<ArrayList<String>> cbMedicine;
	ArrayList<String> medicines;
	ArrayList<Integer> medicine_index;
 	
	JLabel lbMid, lbName, lbRegDate, lbQuantity, lbCost, lbSid;
	JTextField tfMid, tfName, tfRegDate, tfQuantity, tfCost, tfSid;
	
	JButton btnEdit, btnClear, btnCancel, btnDelete, btnSearch, btnCheckSupplier, btnHide;
	
	Box buttonLayout, rightLayout, verticalLayout;
	
	String info[];
	Frame parentFrame;
	boolean isSupplierDetailshowing = false;
	public SearchMedicine(Frame parentFrame, boolean isSearchable, int mid) {
//		this.setBackground(Color.MAGENTA);
		this.setVisible(true);
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		
		initUI(isSearchable);
		
		if(isSearchable) {
			
			headingPanel  = new JPanel();
			lbHeading = new JLabel("Search Medicine");
			lbHeading.setFont(new Font("cambria", Font.PLAIN, 30));
			headingPanel.add(lbHeading);
			
			lbSearch = new JLabel("Search medicine");
			styleLb(lbSearch);
			
			// Getting the all medicine list with their id
			Object [] obj = new DatabaseOperations().getMedicines();
			medicines = (ArrayList<String>)obj[0];
			medicine_index = (ArrayList<Integer>)obj[1];
			
			cbMedicine = new JComboBox(medicines.toArray());
			cbMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
			cbMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
			
			btnSearch = new JButton("Search");
			styleBtn(btnSearch);
			
			searchPanel = new JPanel();
			searchPanel.add(lbSearch);
			searchPanel.add(cbMedicine);
			searchPanel.add(btnSearch);
			
			Box verticalLayout = Box.createVerticalBox();
			verticalLayout.add(headingPanel);
			verticalLayout.add(searchPanel);
			
			this.add(verticalLayout, BorderLayout.NORTH);			
			
			btnSearch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					int index = cbMedicine.getSelectedIndex();
					if(index == 0) {
						JOptionPane.showMessageDialog(getRootPane(), "Please select a medicine!");
					}else {
						//returns mid, name, quantit, cost, regdate, sid
						info = new DatabaseOperations().getMedicineDetails(medicine_index.get(index));
						if(info.length == 0) {
							JOptionPane.showMessageDialog(getRootPane(), "Medicine not found");	
						}else {
							setInformation();
							infoPanel.setVisible(true);	
							if(isSupplierDetailshowing) {
								showSupplierPanel();
							}
						}
					}
				}
			});
			
		}else {
			info = new DatabaseOperations().getMedicineDetails(mid);
			if(info.length == 0) {
				JOptionPane.showMessageDialog(getRootPane(), "Medicine not found");	
			}else {
				setInformation();
				infoPanel.setVisible(true);	
			}
		}
	}
	
	private void initUI(boolean isSearchable) {
		
		   
		// Defining the labels
				
		lbMid = new JLabel("Medicine ID");
		lbName = new JLabel("Name");
		lbQuantity = new JLabel("Quantity");
		lbCost = new JLabel("Cost");
		lbSid = new JLabel("Supplier ID");
		lbRegDate = new JLabel("Reg. date");
		styleLb(lbMid, lbName, lbQuantity, lbCost, lbSid, lbRegDate);
		
		// Defining the TextFields
		tfMid = new JTextField(20);
		tfName = new JTextField(20);
		tfQuantity = new JTextField(20);
		tfCost = new JTextField(20);
		tfSid = new JTextField(20);
		tfRegDate = new JTextField(20);
		styleTf(tfMid, tfName, tfQuantity, tfCost, tfSid, tfRegDate);
		
		// Disabling the fields
		disableFields();
		
		//==================Testing area===================
		
		
		
		//=================================================
		
		infoPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; infoPanel.add(lbMid, c);
		c.gridx = 1; c.gridy = 0; infoPanel.add(tfMid, c);
		c.gridx = 0; c.gridy = 1; infoPanel.add(lbName, c);
		c.gridx = 1; c.gridy = 1; infoPanel.add(tfName, c);
		c.gridx = 0; c.gridy = 2; infoPanel.add(lbQuantity, c);
		c.gridx = 1; c.gridy = 2; infoPanel.add(tfQuantity, c);
		c.gridx = 0; c.gridy = 3; infoPanel.add(lbCost, c);
		c.gridx = 1; c.gridy = 3; infoPanel.add(tfCost, c);
		c.gridx = 0; c.gridy = 4; infoPanel.add(lbSid, c);
		c.gridx = 1; c.gridy = 4; infoPanel.add(tfSid, c);
		c.gridx = 0; c.gridy = 5; infoPanel.add(lbRegDate, c);
		c.gridx = 1; c.gridy = 5; infoPanel.add(tfRegDate, c);
		
		// Adding two extrac space
		c.gridx = 1; c.gridy = 6; infoPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 1; c.gridy = 7; infoPanel.add(Box.createHorizontalBox(), c);
		
		// Defining the buttons 
		btnEdit = new JButton("Edit");
		btnClear = new JButton("Clear");
		btnCancel = new JButton("Cancel");		
		btnDelete = new JButton("Delete record");
		btnDelete.setBackground(Color.RED);
		btnDelete.setForeground(Color.WHITE);
		btnCheckSupplier = new JButton("*"); // it will show the informatio of supplier using supplier id.
		btnHide = new JButton("Hide");
		styleBtn(btnEdit, btnClear, btnCancel, btnDelete, btnCheckSupplier, btnHide);
		
		if(isSearchable) {

			buttonLayout = Box.createVerticalBox();
			Box h1 = Box.createHorizontalBox();
			h1.add(btnEdit);
			h1.add(btnCancel);
			h1.add(Box.createHorizontalStrut(50));
			h1.add(btnClear);
			Box h2 = Box.createHorizontalBox();
			h2.add(btnDelete);
			buttonLayout.add(h1);
			buttonLayout.add(Box.createVerticalStrut(30));
			buttonLayout.add(h2);
			
			c.gridx = 1; c.gridy = 8;infoPanel.add(buttonLayout, c);
			
			// Addding a button to check supplier by supplier id
			c.gridx = 2; c.gridy = 4; infoPanel.add(btnCheckSupplier, c);		
		}
		
		// Adding the actionListener to all these buttons	
		btnClear.addActionListener(this);
		btnCancel.addActionListener(this);
		btnEdit.addActionListener(this);
		btnDelete.addActionListener(this);
		btnCheckSupplier.addActionListener(this);
		btnHide.addActionListener(this);
		
		// Initially clear and cancel will be not visibel
		btnCancel.setVisible(false);
		btnClear.setVisible(false);
		
		// Finally adding infoPanel to mainPanel
		this.add(infoPanel, BorderLayout.CENTER);
		infoPanel.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnCheckSupplier) {
			showSupplierPanel();
			isSupplierDetailshowing = true;
		}
		if(ae.getSource() == btnClear) {
			clearFields();
		}
		if(ae.getSource() == btnDelete) {
			int res = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
			if(res == JOptionPane.YES_OPTION) {
				int mid = Integer.parseInt(tfMid.getText());
				boolean isDeleted = new DatabaseOperations().deleteMedicine(mid);
				
				if(isDeleted) {
					JOptionPane.showMessageDialog(getRootPane(), "Medicine deleted.");
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Database error.");
				}
			}
		}
		if(ae.getSource() == btnCancel) {
			setInformation();
			btnEdit.setText("Edit");
			this.disableFields();
			this.btnClear.setVisible(false);
			this.btnCancel.setVisible(false);
		}
		if(ae.getSource() == btnEdit) {
			String command = ae.getActionCommand();
			if(command == "Edit") {
				btnEdit.setText("Update");
				enableFields();
				this.btnClear.setVisible(true);
				this.btnCancel.setVisible(true);
			}else {
				if(validateForm()) {
					int res = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
					if(res == JOptionPane.YES_OPTION) {
						
						boolean isUpdated = new DatabaseOperations().updateMedicine(
								tfName.getText(), Integer.valueOf(tfCost.getText()),
								Integer.parseInt(tfSid.getText()), Integer.parseInt(tfQuantity.getText()),
								Integer.parseInt(tfMid.getText()));
						
						if(isUpdated) {
							JOptionPane.showMessageDialog(getRootPane(), "Medicine updated successfully");
							btnEdit.setText("Edit");
							this.disableFields();
							this.btnClear.setVisible(false);
							this.btnCancel.setVisible(false);
						}else {
							JOptionPane.showMessageDialog(getRootPane(), "Database error.");
						}
					}
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Incorrect details!");
				}
			}
		}
		if(ae.getSource() == btnHide) {
			this.remove(rightLayout);
			rightLayout = null;
			verticalLayout = null;
			isSupplierDetailshowing = false;
			System.gc();
			this.validate();
			this.revalidate();
			parentFrame.refreshDisplay();
		}
	}

	private void showSupplierPanel() {
		String sid = tfSid.getText();
		
		if(!Pattern.matches("[0-9]+", sid)) {
			JOptionPane.showMessageDialog(getRootPane(), "Please enter a supplier ID.");
		}else {
			// Checking if supplier id is present
			
			if(!new DatabaseOperations().isSupplierPresent(Integer.valueOf(sid))) {
				JOptionPane.showMessageDialog(getRootPane(), "Supplier is not present!");
				return;
			}
			
			// Checking if any thing is getting displayed.
			if( rightLayout != null && verticalLayout != null) {
				rightLayout.removeAll();
				rightLayout.validate();
				rightLayout.revalidate();
				verticalLayout.removeAll();
				verticalLayout.validate();
				verticalLayout.revalidate();
			}
			
			JLabel lbHeading = new JLabel("Supplier details");
			lbHeading.setFont(new Font("cambria", Font.PLAIN, 25));
			
			
			rightLayout = Box.createHorizontalBox();
			verticalLayout = Box.createVerticalBox();
			verticalLayout.add(lbHeading);
			verticalLayout.add(new SearchSupplier(parentFrame, false, Integer.valueOf(sid)));
			verticalLayout.add(btnHide);
			verticalLayout.setBorder(new LineBorder(Color.GRAY, 2, true));
			rightLayout.add(verticalLayout);
			rightLayout.add(Box.createHorizontalStrut(40));
			
			this.add(rightLayout, BorderLayout.EAST);
			this.validate();
			this.revalidate();
			parentFrame.refreshDisplay();
		}
	}
	private void setInformation() {
		//returns mid, name, quantit, cost, regdate, sid
		tfMid.setText(info[0]);
		tfName.setText(info[1]);
		tfQuantity.setText(info[2]);
		tfCost.setText(info[3]);
		tfRegDate.setText(info[4]);
		tfSid.setText(info[5]);
	}
	private void enableFields() {
		tfName.setEditable(true);
		tfQuantity.setEditable(true);
		tfCost.setEditable(true);
		tfSid.setEditable(true);
	}
	private void disableFields() {
		tfMid.setEditable(false);
		tfName.setEditable(false);
		tfQuantity.setEditable(false);
		tfCost.setEditable(false);
		tfSid.setEditable(false);
		tfRegDate.setEditable(false);
	}
	private void clearFields() {
		tfName.setText("");
		tfQuantity.setText("");
		tfCost.setText("");
		tfSid.setText("");
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
	private void styleBtn(JButton ...buttons) {
		for(JButton button: buttons) {
			button.setFont(new Font("cambria", Font.PLAIN, 16));
			button.setBorder(new EmptyBorder(new Insets(5,10, 5, 10)));
			button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	private boolean validateForm() {
		// sometime in medicine name there is integers too and hyphen(-)
		return Pattern.matches("[a-zA-z0-9\s-]+", tfName.getText()) && 
				Pattern.matches("[0-9]+", tfCost.getText()) && 
				Pattern.matches("[0-9]+", tfQuantity.getText()) &&
				Pattern.matches("[0-9]+", tfSid.getText());
	}
}
