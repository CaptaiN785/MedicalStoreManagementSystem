package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseOperations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.awt.Font;
import java.awt.Insets;

public class UpdateInventory extends JPanel{
	JTable jtTable;
	DefaultTableModel tableModel;
	Frame parentFrame;
	
	JPanel headingPanel, searchPanel;
	JLabel lbHeading, lbSearchMedicine;
	
	JComboBox cbMedicineList;
	JButton btnAddMedicine;
	
	Object[][] medicineList;
	String [] medicineNames;
	HashMap<Integer, Integer> map; // it will map which medicine is present which not.
	
	
	JButton btnUpdateInventory, btnRemoveAll, btnClearAll;
	
	
	// All element for suppliers display
	Box supplierLayout;
	JButton btnHideSupplier;
	
	// all element for medicine details display
	Box medicineLayout;
	JButton btnHideMedicine;
	
	public UpdateInventory(Frame parentFrame) {
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		map = new HashMap<>();
		
		lbHeading = new JLabel("Update inventory");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 25));
		headingPanel = new JPanel();
		headingPanel.add(lbHeading);
		
		
		lbSearchMedicine = new JLabel("Add medicine to table");
		lbSearchMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		
		this.medicineList = new DatabaseOperations().getMedicineList(-1);
		medicineNames = new String[this.medicineList.length];
		for(int i=0; i<medicineList.length; i++) {
			medicineNames[i] = (String)this.medicineList[i][1];
		}
		
		cbMedicineList = new JComboBox(this.medicineNames);
		cbMedicineList.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		btnAddMedicine = new JButton("Add medicine");
		btnAddMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		btnAddMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		searchPanel = new JPanel();
		searchPanel.add(lbSearchMedicine);
		searchPanel.add(cbMedicineList);
		searchPanel.add(btnAddMedicine);
		
		Box verticalLayout = Box.createVerticalBox();
		verticalLayout.add(headingPanel);
		verticalLayout.add(searchPanel);
		this.add(verticalLayout, BorderLayout.NORTH);
		
		
		// Create a table that will allow to update the quantity of every medicine
		// Firstly there is no data in table 
		// Adding only column to table
		String columns[] = {"Medicine ID", "Name", "Quantity", "Supplier ID"};
		
		
		tableModel = new DefaultTableModel(columns, 0) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       if(column == 2) return true;
		       return false;
		    }
		};
		jtTable = new JTable(tableModel);
		jtTable.setFont(new Font("cambria", Font.PLAIN, 16));
		jtTable.setRowHeight(18);
		
		// make columns not to change when it is arranged in other order
		jtTable.getTableHeader().setReorderingAllowed(false);
		
		// action performed on table
		jtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(me.getClickCount() == 2) {// if mouse is double clicked on name
					int row = jtTable.getSelectedRow();
					int col = jtTable.getSelectedColumn();
					if(col == 1) { // if name is clicked
						int r = JOptionPane.showConfirmDialog(getParent(), "Are your sure want to delete?");
						if(r == JOptionPane.YES_OPTION) {
							String val = (String)jtTable.getValueAt(row, col);
							tableModel.removeRow(row);
							int mid = Integer.parseInt((String)tableModel.getValueAt(row, 0));
							map.remove(mid);
							JOptionPane.showMessageDialog(getRootPane(), val + " is deleted.");
						}
					}else if(col == 3) { // if supplier is clicked
						displaySupplierInfo(row, col);
					}else if(col == 0) {
						displayMedicineInfo(row, col);
					}
				}
			}
		});

		///Adding actionListener to button to add in table
		btnAddMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int index = cbMedicineList.getSelectedIndex();
				Object []obj = medicineList[index];
				obj[2] = 0;
				int mid = Integer.parseInt((String)obj[0]);
				if(!map.containsKey(mid)) { // putting map to medicine id
					// so that it won't appear more than one time in tables.
					tableModel.addRow(obj);
					map.put(mid, 1);
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Medicine already added.");
				}
			}
		});
		
		JScrollPane sp=new JScrollPane(jtTable);
		this.add(sp, BorderLayout.CENTER);
		
		// Now working on Bottom panel 
		btnRemoveAll = new JButton("Remove all");
		btnRemoveAll.setBackground(Color.RED);
		btnRemoveAll.setForeground(Color.WHITE);
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				removeAllEntry(true);
			}
		});
		
		btnClearAll = new JButton("Clear Quantity");
		btnClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				clearAllQuantity();
			}
		});
		
		btnUpdateInventory = new JButton("Update inventory");
		btnUpdateInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// asking for sure
				int res = JOptionPane.showConfirmDialog(getRootPane(), "Are your sure?");
				if(res == JOptionPane.YES_OPTION) {
					int totalEntries = tableModel.getRowCount();
					int data[][] = new int[totalEntries][2];
					
					for(int i=0; i<totalEntries; i++) {
						data[i][0] = Integer.parseInt((String)tableModel.getValueAt(i, 0));
						String quant = tableModel.getValueAt(i, 2).toString();
						if(Pattern.matches("[0-9]+", quant)) {
							data[i][1] = Integer.parseInt(quant);
						}else {
							JOptionPane.showMessageDialog(getRootPane(), "Unknown value for medicine id: " + data[i][0]);
							return;
						}
					}
					
					if(new DatabaseOperations().updateInventory(data)) {
						removeAllEntry(false);
						JOptionPane.showMessageDialog(getRootPane(), "Inventory updated.");
					}else {
						JOptionPane.showMessageDialog(getRootPane(), "Unable to update");
					}
				}
			}

		});
		
		// styling the bottom buttons
		styleBtn(btnRemoveAll, btnClearAll, btnUpdateInventory);
		
		JPanel bottomLayout = new JPanel();
		bottomLayout.setBorder(new LineBorder(Color.GRAY, 1));
		
		bottomLayout.add(btnUpdateInventory);
		bottomLayout.add(btnRemoveAll);
		bottomLayout.add(btnClearAll);
		this.add(bottomLayout, BorderLayout.SOUTH);
		
	}
	
	private void removeAllEntry(boolean asked) {
		if(asked == false) {
			int totalEntry = tableModel.getRowCount();
			for(int i = totalEntry-1; i>=0; i--) {
				int mid = Integer.parseInt((String)tableModel.getValueAt(i, 0));
				map.remove(mid);
				tableModel.removeRow(i);
			}
			cbMedicineList.setSelectedIndex(0);
			return;
		}
		int res = JOptionPane.showConfirmDialog(getRootPane(), "Are you sure want to remove all entry?");
		if(res == JOptionPane.YES_OPTION) {
			int totalEntry = tableModel.getRowCount();
			for(int i = totalEntry-1; i>=0; i--) {
				int mid = Integer.parseInt((String)tableModel.getValueAt(i, 0));
				map.remove(mid);
				tableModel.removeRow(i);
			}
			cbMedicineList.setSelectedIndex(0);
			JOptionPane.showMessageDialog(getRootPane(), "All record is cleared");
		}
	}
	private void clearAllQuantity() {
		int res = JOptionPane.showConfirmDialog(getRootPane(), "Do you want to clear all quantity?");
		if(res == JOptionPane.YES_OPTION) {
			int totalEntry = tableModel.getRowCount();
			for(int i = totalEntry-1; i>=0; i--) tableModel.setValueAt("0", i, 2);
			JOptionPane.showMessageDialog(getRootPane(), "All quantity is cleared");
		}
	}
	private void displaySupplierInfo(int row, int col) {
		
		if(supplierLayout != null) {
			this.remove(supplierLayout);
			supplierLayout.removeAll();
			supplierLayout.validate();
			supplierLayout.revalidate();
		}
		
		supplierLayout = Box.createVerticalBox();
		JLabel headingLabel = new JLabel("Supplier information");
		headingLabel.setFont(new Font("cambria", Font.PLAIN, 25));
		supplierLayout.add(new JPanel().add(headingLabel));
		supplierLayout.add(new SearchSupplier(parentFrame, false, Integer.parseInt((String)jtTable.getValueAt(row, col))));
		
		btnHideSupplier = new JButton("Hide");
		btnHideSupplier.setFont(new Font("cambria", Font.PLAIN, 16));
		btnHideSupplier.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		btnHideSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				removeSupplierLayout();
			}
		});
		
		supplierLayout.add(btnHideSupplier);
		this.add(supplierLayout, BorderLayout.EAST);
		this.validate();
		this.revalidate();
		parentFrame.refreshDisplay();
	}
	private void removeSupplierLayout() {
		this.remove(supplierLayout);
		this.validate();
		this.revalidate();
		parentFrame.refreshDisplay();
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
		medicineLayout.add(new SearchMedicine(parentFrame, false, Integer.parseInt((String)jtTable.getValueAt(row, col))));
		
		btnHideMedicine = new JButton("Hide");
		btnHideMedicine.setFont(new Font("cambria", Font.PLAIN, 16));
		btnHideMedicine.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		
		btnHideMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				removeLayout();
			}
		});
		medicineLayout.add(btnHideMedicine);
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
	
	// Helper functions
	private void styleBtn(JButton ...buttons) {
		for(JButton button : buttons) {
			button.setFont(new Font("cambria", Font.PLAIN, 16));
			button.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		}
	}
}
