package UI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseOperations;

public class MedicineList extends JPanel {
	JTable jtTable;
	JPanel tablePanel;
	
	Frame parentFrame;
	JButton btnHideMedicine, btnHideSupplier;
	Box medicineLayout, supplierLayout;
	Object[][] data;
	
	JButton btnSave;
	
	public MedicineList(Frame parentFrame, boolean isReferenced, Object[][] data) {
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		
		if(isReferenced) {
			// It is just to show table if it is called in any tab.
			this.data = data;
		}else {
			// Here specifically opening this tab.
			this.data = new DatabaseOperations().getMedicineList(-1);// -1 means all medicine list
		}
		initUI(isReferenced, this.data);
	}
	private void initUI(boolean isReferenced, Object[][] data) {
		// Columns of medicine list
		Object[] columns = {"Medicine ID", "Name", "Quantity","Suplier ID"};
		if(data.length == 0) {
			JOptionPane.showMessageDialog(getRootPane(), "No medicine found!");
			return;
		}
		
		tablePanel = new JPanel(new BorderLayout());
		jtTable = new JTable(data, columns);
		jtTable.setFont(new Font("cambria", Font.PLAIN, 15));
		jtTable.setRowHeight(18);
		
		jtTable.setModel(new DefaultTableModel(data, columns) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		});
		// make columns not to change when it is arranged in other order
		jtTable.getTableHeader().setReorderingAllowed(false);
		
		// action performed on table
		if(!isReferenced) {
			jtTable.setCellSelectionEnabled(true);  
	        ListSelectionModel select= jtTable.getSelectionModel();  
	        select.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  
	        select.addListSelectionListener(new ListSelectionListener() {
	        	
	        	public void valueChanged(ListSelectionEvent e) {  
	            int[] row = jtTable.getSelectedRows();  
	            int[] column = jtTable.getSelectedColumns();  
	             
	            if(row.length == 1 && column.length == 1) {
	            	if(column[0] == 0) {
	            		displayMedicineInfo(row[0], column[0]);
	            	}
	            	if(column[0] == 3) {
	            		displaySupplierInfo(row[0], column[0]);
	            	}
	            }
	        }});  
		}

		JScrollPane sp=new JScrollPane(jtTable);    
        tablePanel.add(sp, BorderLayout.CENTER);
        tablePanel.setMaximumSize(new Dimension(400, -1));
        this.add(tablePanel, BorderLayout.CENTER);
        
        // Button for saving the file into csv format
        btnSave = new JButton("Save as csv");
        btnSave.setFont(new Font("cambria", Font.PLAIN, 16));
        btnSave.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(new JPanel().add(btnSave), BorderLayout.SOUTH);
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent ae) {
        		System.out.println("yes");
        	}
        });
        
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
}
