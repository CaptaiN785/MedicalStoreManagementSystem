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
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class AddMedicine extends JPanel{
	
	JLabel heading;
	JPanel  headingPanel, formPanel;
	
	JTextField tfName, tfQuantity, tfCost;
	JLabel lbName, lbQuantity, lbCost, lbSid;
	JComboBox<String> cbSid;
	
	JButton checkSupplierDetails, btnAddMedicine, btnClear;

	public AddMedicine(Frame parentFrame){
		super();
//		this.setBackground(Color.RED);
		this.setVisible(true);
		
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
		lbQuantity = new JLabel("Enter quantity");
		lbCost = new JLabel("Medicine price");
		lbSid = new JLabel("Select supplier");
		styleLb(lbName, lbQuantity, lbCost, lbSid);
		
		tfName = new JTextField(20);
		tfQuantity = new JTextField("0",20);
		tfCost = new JTextField("0", 20);
		
		// Here need all the supplier list.
		String supplierList[] = {"Select supplier", "Sharma medicals", 
				"Medical stores chandigarh", "Dental store"};
		cbSid = new JComboBox<String>(supplierList);
		styleTf(tfName, tfQuantity, tfCost);// Styling the text editor field
		
		formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; formPanel.add(lbName, c);
		c.gridx = 1; c.gridy = 0; formPanel.add(tfName, c);
		c.gridx = 0; c.gridy = 1; formPanel.add(lbQuantity, c);
		c.gridx = 1; c.gridy = 1; formPanel.add(tfQuantity, c);
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
					JOptionPane.showMessageDialog(getRootPane(), "Adding medicine");					
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
				clear();
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
	private void clear() {
		tfName.setText("");
		tfQuantity.setText("0");
		tfCost.setText("0");
		cbSid.setSelectedIndex(0);
	}
	private boolean validateForm() {
		// sometime in medicine name there is integers too and hyphen(-)
		return Pattern.matches("[a-zA-z0-9\s-]+", tfName.getText()) && 
				Pattern.matches("[0-9]+", tfQuantity.getText()) &&
				Pattern.matches("[0-9]+", tfCost.getText()) && 
				(cbSid.getSelectedIndex() != 0);
	}
}
