package UI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseOperations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SellMedicine extends JPanel{
	
	JTextField tfCustomerName, tfQuantity,tfCustomerPhone, tfTotalCost;
	JComboBox cbMedicineList;
	
	JPanel formPanel, headingPanel, inputPanel, bottomPanel;
	JButton btnCheckout, btnAddMedicine, btnClearAll, btnHideMedicine;
	JLabel lbHeading, lbCustomerName, lbCustomerPhone, lbQuantity, lbMedicineList, lbTotalCost;
	Frame parentFrame;
	
	JTable jtTable;
	DefaultTableModel tableModel;
	
	Object[][] medicineList;
	String [] medicineNames;
	HashMap<Integer, Integer> map;
	
	Box medicineLayout;
	HashMap<Integer, Integer> medicineCost;
	HashMap<Integer, Integer> maxMedicine;
	
	SellMedicine(Frame parentFrame){
		map = new HashMap<>();
		medicineCost = new HashMap<>();
		// In hashmap, available quantity is storing
		// it will also help in to map if present in table.
		medicineCost = new DatabaseOperations().getMedicineCost();
		maxMedicine = new HashMap<>();
		
		this.setLayout(new BorderLayout());
		this.parentFrame = parentFrame;
		
		
		// Heading panel
		lbHeading = new JLabel("Checkout");
		lbHeading.setFont(new Font("cambria", Font.PLAIN, 25));
		
		headingPanel = new JPanel();
		headingPanel.add(lbHeading);
		
		// Form panel
		// Labels
		lbCustomerName = new JLabel("Customer name");
		lbCustomerPhone = new JLabel("Customer mobile");
		lbQuantity = new JLabel("Quantity");
		lbMedicineList = new JLabel("Select Medicine");
		lbTotalCost = new JLabel("Total cost");
		styleLb(lbCustomerName,lbQuantity, lbMedicineList, lbCustomerPhone, lbTotalCost);
		
		
		// TextField
		tfCustomerName = new JTextField(20);
		tfCustomerPhone = new JTextField(20);
		tfQuantity = new JTextField(20);
		tfTotalCost = new JTextField(20);
		tfTotalCost.setEditable(false);
		styleTf(tfCustomerName, tfQuantity, tfCustomerPhone, tfTotalCost);
		
		//Medicine list comboBox
		this.medicineList = new DatabaseOperations().getMedicineList(-1);
		medicineNames = new String[this.medicineList.length];
		for(int i=0; i<medicineList.length; i++) {
			medicineNames[i] = (String)this.medicineList[i][1];
			// columns 2 has available quantity
			int mid = Integer.parseInt(medicineList[i][0].toString());
			int availableQuantity = Integer.parseInt(medicineList[i][2].toString());
			maxMedicine.put(mid, availableQuantity);
		}

		// After checkout update the combo box with new medicine list.
		cbMedicineList = new JComboBox<>(this.medicineNames);
		cbMedicineList.setFont(new Font("cambria", Font.PLAIN, 16));
		
		btnAddMedicine = new JButton("Add medicine");
		btnAddMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		btnAddMedicine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAddMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		// Keeping the add button with combo box
		Box comboLayout = Box.createHorizontalBox();
		comboLayout.add(cbMedicineList);
		comboLayout.add(btnAddMedicine);
		
		inputPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 0; c.gridy = 0; inputPanel.add(lbCustomerName, c);
		c.gridx = 1; c.gridy = 0; inputPanel.add(tfCustomerName, c);
		c.gridx = 0; c.gridy = 1; inputPanel.add(lbCustomerPhone, c);
		c.gridx = 1; c.gridy = 1; inputPanel.add(tfCustomerPhone, c);
		c.gridx = 0; c.gridy = 2; inputPanel.add(lbMedicineList, c);
		c.gridx = 1; c.gridy = 2; inputPanel.add(comboLayout, c);
		c.gridx = 0; c.gridy = 3; inputPanel.add(lbQuantity, c);
		c.gridx = 1; c.gridy = 3; inputPanel.add(tfQuantity, c);
		c.gridx = 0; c.gridy = 4; inputPanel.add(lbTotalCost, c);
		c.gridx = 1; c.gridy = 4; inputPanel.add(tfTotalCost, c);
		c.gridx = 0; c.gridy = 5; inputPanel.add(Box.createHorizontalBox());
		c.gridx = 0; c.gridy = 6; inputPanel.add(Box.createHorizontalBox());
		
		// Table of medicines
		String columns[] = {"Medicine ID", "Name", "Cost", "Quantity"};
		tableModel = new DefaultTableModel(columns, 0) {
			 public boolean isCellEditable(int row, int column) {
			       //all cells false
			       if(column == 3) return true;
			       return false;
			 }
		};
		// Adding table model listener for dynamic change in data
		tableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent te) {
				// Checking the maximum availability of medicine
				int row = te.getFirstRow();
				int col = te.getColumn();
				
				if(col == 3) { // value changed in quantity columns
					String quant = jtTable.getValueAt(row, col).toString();
					// checking if wrong value or character is inserted
					if(!Pattern.matches("[0-9]+", quant)) {
						JOptionPane.showMessageDialog(getRootPane(), "Unknown values.");
						jtTable.setValueAt(0, row, col);
						return;
					}
					
					int mid = Integer.parseInt(jtTable.getValueAt(row, 0).toString());
					int quantity = Integer.parseInt(quant);
					if(!checkAvailablity(maxMedicine.get(mid), quantity)) {
						JOptionPane.showMessageDialog(getRootPane(), "Not enough medicine! Available is "+ maxMedicine.get(mid));
						jtTable.setValueAt(Math.min(quantity, maxMedicine.get(mid)), row, col);
						return;
					}
				}
				// calculate the total cost here and update the total cost
				int totalCost = 0;
				int totalRow = jtTable.getRowCount();
				for(int i=0; i<totalRow; i++) {
					int mid = Integer.parseInt(jtTable.getValueAt(i, 0).toString());
					int quantity = Integer.parseInt(jtTable.getValueAt(i, 3).toString());
					totalCost += medicineCost.get(mid) * quantity;
				}
				tfTotalCost.setText(String.valueOf(totalCost));
			}
		});
		
		jtTable = new JTable(tableModel);
		jtTable.getTableHeader().setReorderingAllowed(false);
		jtTable.setFont(new Font("cambria", Font.PLAIN, 16));
		jtTable.setRowHeight(20);
		
		jtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {
					int row = jtTable.getSelectedRow();
					int col = jtTable.getSelectedColumn();
					
					if(col == 0) {
						displayMedicineInfo(row, col);
					}
					if(col == 1) {
						int res = JOptionPane.showConfirmDialog(getRootPane(), "Do want to delete?");
						if(res == JOptionPane.YES_OPTION) {
							int mid = Integer.parseInt((String)jtTable.getValueAt(row, 0));
							map.remove(mid);
							tableModel.removeRow(row);
						}
					}
				}
			}
		});
		
		// Form arrangements
		formPanel = new JPanel(new BorderLayout());
		formPanel.add(headingPanel, BorderLayout.NORTH);
		
		Box layout = Box.createVerticalBox();
		layout.add(inputPanel);
		JScrollPane sc = new JScrollPane(jtTable);
		layout.add(sc);
		formPanel.add(layout, BorderLayout.CENTER);
		this.add(formPanel, BorderLayout.CENTER);
		
		
		// Addding the action to add medicine buttons
		btnAddMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addMedicineToTable();
			}
		});
		// Adding key listener to combo box
		cbMedicineList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
					addMedicineToTable();
				}
			}
		});
		
		
		// Bottom panel for all kind of actions
		btnCheckout = new JButton("Checkout");
		btnClearAll = new JButton("Clear all");
		
		btnCheckout.setFont(new Font("cambria", Font.PLAIN, 16));
		btnClearAll.setFont(new Font("cambria", Font.PLAIN, 16));
		
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearTable();
			}
		});
		
		btnCheckout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int res = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure?");
				if(res == JOptionPane.YES_OPTION) {
					// | PHONE   | NAME   | MID  | QUANTITY | COST | DATETIME 
					int totalRow = jtTable.getRowCount();
					if(validateForm() && totalRow > 0) {
						String name = tfCustomerName.getText();
						long phone = Long.parseLong(tfCustomerPhone.getText());
						for(int i=0; i<totalRow; i++) {
							int mid = Integer.parseInt(jtTable.getValueAt(i, 0).toString());
							int cost = Integer.parseInt(jtTable.getValueAt(i, 2).toString()); 
							int quantity = Integer.parseInt(jtTable.getValueAt(i, 3).toString());
							if(!new DatabaseOperations().addCheckoutDetails(phone, name, mid, quantity, cost)) {
								JOptionPane.showMessageDialog(getRootPane(), "Fatal error occured for mid: " + mid);
							}
						}
						JOptionPane.showMessageDialog(getRootPane(), "Medicine checked out successfully");
						clearTable();
					}else {
						JOptionPane.showMessageDialog(getRootPane(), "Invalid credential!");
					}
				}
			}
		});
		
		// adding these two in the bottom of formPanel
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(btnCheckout);
		bottomPanel.add(btnClearAll);
		formPanel.add(bottomPanel, BorderLayout.SOUTH);

	}
	private void displayMedicineInfo(int row, int col) {
		
		if(medicineLayout != null) {
			this.remove(medicineLayout);
			medicineLayout.removeAll();
			medicineLayout.validate();
			medicineLayout.revalidate();
		}
		
		medicineLayout = Box.createVerticalBox();
		JLabel headingLabel = new JLabel("Medicine information");
		headingLabel.setFont(new Font("cambria", Font.PLAIN, 25));
		medicineLayout.add(new JPanel().add(headingLabel));
		medicineLayout.add(new SearchMedicine(parentFrame, false, Integer.parseInt(jtTable.getValueAt(row, col).toString())));
		
		btnHideMedicine = new JButton("Hide");
		btnHideMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		btnHideMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		btnHideMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				removeLayout();
			}
		});
		medicineLayout.add(btnHideMedicine);
		medicineLayout.setBorder(new LineBorder(Color.GRAY, 1, true));
		this.add(medicineLayout, BorderLayout.WEST);
		this.validate();
		this.revalidate();
		parentFrame.refreshDisplay();
	}
	private void removeLayout() {
		this.remove(medicineLayout);
		this.validate();
		this.revalidate();
		parentFrame.refreshDisplay();
	}
	private void addMedicineToTable() {
		int ind = cbMedicineList.getSelectedIndex();
		Object[] obj = medicineList[ind];
		
		int availableQuantity = Integer.parseInt(obj[2].toString());
		int mid = Integer.parseInt(obj[0].toString());
		
		Object []newObject = new Object[4];
		newObject[0] = mid;
		newObject[1] = obj[1];
		newObject[2] = medicineCost.get(mid);
		newObject[3] = 0;
		if(Pattern.matches("[0-9]+", (CharSequence)tfQuantity.getText()) && tfQuantity.getText() != ""){
			int quant = Integer.parseInt(tfQuantity.getText());
			if(checkAvailablity(availableQuantity, quant)) {
				newObject[3] = quant;
			}else {
				JOptionPane.showMessageDialog(getRootPane(), "Not enough in inventory!");
				return;
			}
		}
		if (map.containsKey(mid)) {
			JOptionPane.showMessageDialog(getRootPane(), "Medicine already added.");
		}else {
			map.put(mid,  availableQuantity);
			tableModel.addRow(newObject);
		}
	}
	boolean checkAvailablity(int available, int quantity) {
		return (available >= quantity);
	}
	private void clearTable() {
		for(int i=tableModel.getRowCount() -1; i>=0; i--) {
			int mid = Integer.parseInt(jtTable.getValueAt(i, 0).toString());
			map.remove(mid);
			tableModel.removeRow(i);
		}
		tfCustomerName.setText("");
		tfCustomerPhone.setText("");
		JOptionPane.showMessageDialog(getRootPane(), "All value is cleared");
	}
	private void styleTf(JTextField ...tfs) {
		for(JTextField tf : tfs) {
			tf.setFont(new Font("cambria", Font.PLAIN, 16));
		}
	}
	private void styleLb(JLabel ...labels) {
		for(JLabel label : labels) {
			label.setFont(new Font("cambria", Font.PLAIN, 16));
		}
	}
	private boolean validateForm() {
		return Pattern.matches("[a-zA-Z\s]+", tfCustomerName.getText()) &&
				Pattern.matches("[0-9]+", tfCustomerPhone.getText()) &&
				tfCustomerPhone.getText().length() == 10 &&
				tfCustomerName.getText().length() >= 3;
	}
}
