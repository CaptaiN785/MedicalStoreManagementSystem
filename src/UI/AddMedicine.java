package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Database.DatabaseOperations;

public class AddMedicine extends JPanel implements ActionListener{
	
	JLabel heading;
	JPanel  headingPanel, formPanel;
	
	JTextField tfName, tfCost;
	JLabel lbName, lbCost, lbSid;
	JComboBox<ArrayList<String>> cbSid;
	
	JButton checkSupplierDetails, btnAddMedicine, btnClear;
	Frame parentFrame;
	
	JButton btnHide;
	
	ArrayList<Integer> supplier_index;
	ArrayList<String> suppliers;
	
	Box rightLayout;
	Box verticalLayout;
	public AddMedicine(Frame parentFrame){
		super();
		this.setVisible(true);
		this.parentFrame = parentFrame;
		initUI();
	}

	private void initUI() {
		this.setLayout(new BorderLayout());
		
		headingPanel = new JPanel();
		heading = new JLabel("Add medicine");
		heading.setFont(new Font("cambria", Font.BOLD, 30));
		headingPanel.add(heading);
		this.add(headingPanel, BorderLayout.NORTH);
		
		//  working on form for taking input for adding the new medicines
		
		lbName = new JLabel("Enter name");
		lbCost = new JLabel("Medicine price");
		lbSid = new JLabel("Select supplier");
		styleLb(lbName,lbCost, lbSid);
		
		tfName = new JTextField(20);
		tfCost = new JTextField("0", 20);
		
		// Here need all the supplier list.
		Object[] res = new DatabaseOperations().getSuppliers();
		this.suppliers = (ArrayList<String>)res[0];
		this.supplier_index = (ArrayList<Integer>)res[1];
		
		cbSid = new JComboBox(suppliers.toArray());
		styleTf(tfName, tfCost);// Styling the text editor field
		
		formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; formPanel.add(lbName, c);
		c.gridx = 1; c.gridy = 0; formPanel.add(tfName, c);
		c.gridx = 0; c.gridy = 2; formPanel.add(lbCost, c);
		c.gridx = 1; c.gridy = 2; formPanel.add(tfCost, c);
		c.gridx = 0; c.gridy = 3; formPanel.add(lbSid, c);
		c.gridx = 1; c.gridy = 3; formPanel.add(cbSid, c);
		
		// Adding empty layout for getting space between field and buttons
		c.gridx = 1; c.gridy = 4; formPanel.add(Box.createHorizontalBox(), c);
		c.gridx = 1; c.gridy = 5; formPanel.add(Box.createHorizontalBox(), c);
		
		// Adding the button in the formPanel
		btnAddMedicine = new JButton("Add medicine");
		btnAddMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		btnAddMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnAddMedicine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if(validateForm()) {
					
					boolean result = new DatabaseOperations().addMedicine(tfName.getText(),
							Integer.parseInt(tfCost.getText()), supplier_index.get(cbSid.getSelectedIndex()));
					
					if(result) {
						clearForm();
						JOptionPane.showMessageDialog(getRootPane(), "Adding medicine");					
					}else {
						JOptionPane.showMessageDialog(getRootPane(), "Database error! Medicine not registered.");											
					}
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Please check the details");
				}
			}
		});
		
		
		btnClear = new JButton("Clear");
		btnClear.setFont(new Font("cambria", Font.PLAIN, 16));
		btnClear.setBorder(new EmptyBorder(new Insets(5,10, 5, 10)));
		btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearForm();
			}
		});
		
		Box btnLayout = Box.createHorizontalBox();
		btnLayout.add(btnAddMedicine);
		btnLayout.add(Box.createHorizontalStrut(100));
		btnLayout.add(btnClear);
		c.gridx = 1; c.gridy = 6; formPanel.add(btnLayout, c);
		
		// finally adding the formPanel to main panel
		this.add(formPanel, BorderLayout.CENTER);
		
		// Help button for check supplier details
		ImageIcon img = new ImageIcon(this.getClass().getClassLoader().
				getResource("Icons/avatar.png").getFile());
		Image image = img.getImage();
		image = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		checkSupplierDetails = new JButton("*");
		checkSupplierDetails.setIcon(new ImageIcon(image));
		checkSupplierDetails.setMaximumSize(new Dimension(40,40));
		checkSupplierDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
		c.gridx = 2; c.gridy = 3; formPanel.add(checkSupplierDetails, c);
		checkSupplierDetails.addActionListener(this);
		
		
		// this button will hide the supplier details
		btnHide = new JButton("Hide");
		btnHide.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnHide.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnHide.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == checkSupplierDetails) {
			int index = cbSid.getSelectedIndex();
			if(index == 0) {
				JOptionPane.showMessageDialog(getRootPane(), "Please select a supplier.");
			}else {
				// Checking if any thing is getting displayed.
				if(rightLayout != null && verticalLayout != null) {
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
				verticalLayout.add(new SearchSupplier(parentFrame, false, this.supplier_index.get(index)));
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
		if(ae.getSource() == btnHide) {
			this.remove(rightLayout);
			rightLayout = null;
			verticalLayout = null;
			System.gc();
			this.validate();
			this.revalidate();
			parentFrame.refreshDisplay();
		}
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
	private void clearForm() {
		tfName.setText("");
		tfCost.setText("0");
		cbSid.setSelectedIndex(0);
	}
	private boolean validateForm() {
		// sometime in medicine name there is integers too and hyphen(-)
		return Pattern.matches("[a-zA-z0-9\s-]+", tfName.getText()) && 
				Pattern.matches("[0-9]+", tfCost.getText()) && 
				(cbSid.getSelectedIndex() != 0);
	}
}
