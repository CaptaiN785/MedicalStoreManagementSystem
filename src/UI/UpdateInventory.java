package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Database.DatabaseOperations;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.HashMap;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

public class UpdateInventory extends JPanel{
	
	JTable jtTable;
	Frame parentFrame;
	
	JPanel headingPanel, searchPanel;
	JLabel lbHeading, lbSearchMedicine;
	
	JComboBox cbMedicineList;
	
	JButton btnAddMedicine;
	
	Object[][] medicineList;
	String [] medicineNames;
	
	HashMap<Integer, Integer> map;
	
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
		
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
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
					if(col == 1) {
						int r = JOptionPane.showConfirmDialog(getParent(), "Are your sure?");
						if(r == JOptionPane.YES_OPTION) {
							String val = (String)jtTable.getValueAt(row, col);
							tableModel.removeRow(row);
							JOptionPane.showMessageDialog(getRootPane(), val + " is deleted.");
						}
					}
				}
			}
		});

		JScrollPane sp=new JScrollPane(jtTable);
		this.add(sp, BorderLayout.CENTER);
		
		///Adding actionlistener to button to add in table
		btnAddMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int index = cbMedicineList.getSelectedIndex();
				if(!map.containsKey(index)) {
					Object []obj = medicineList[index];
					obj[2] = 0;
					tableModel.addRow(obj);
					map.put(index, 1);
				}else {
					JOptionPane.showMessageDialog(getRootPane(), "Medicine already added.");
				}
			}
		});
		// right click on table row to delete
		// make quantity of each object as null
	}
}
