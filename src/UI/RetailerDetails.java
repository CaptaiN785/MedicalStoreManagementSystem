package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseOperations;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class RetailerDetails extends JPanel{
	
	JLabel lbHeading, lbPhone;
	JTextField tfPhone;
	JButton btnSearch, btnSave, btnHideMedicine;
	
	JPanel headingPanel;

	JTable jtTable;
	DefaultTableModel tableModel;
	JScrollPane sc;
	
	Box medicineLayout;
	long phone;
	Frame parentFrame;
	RetailerDetails(Frame parentFrame){
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());	
		Font font = new Font("cambria", Font.PLAIN, 16);
		// ===============================

		headingPanel = new JPanel(new BorderLayout());
		JPanel temp = new JPanel();
		lbHeading = new JLabel("Retailer details");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 25));
		temp.add(lbHeading);
		headingPanel.add(temp, BorderLayout.NORTH);
		
		// ====== Search box ==========
		lbPhone = new JLabel("Enter mobile number");
		lbPhone.setFont(font);
		
		tfPhone = new JTextField(20);
		tfPhone.setFont(font);
		
		btnSearch = new JButton("Search");
		btnSearch.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSearch.setFont(font);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fillTable();
			}
		});
		
		JPanel sp = new JPanel();
		sp.add(lbPhone); sp.add(tfPhone); sp.add(btnSearch);
		headingPanel.add(sp, BorderLayout.CENTER);
		
		this.add(headingPanel, BorderLayout.NORTH);
		
		// table work
		String columns[] = {"Phone", "Medicine ID", "Name", "Quantity", "Cost", "Datetime"};
		tableModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		jtTable = new JTable(tableModel);
		jtTable.setFont(new Font("cambria", Font.PLAIN, 16));
		jtTable.getTableHeader().setReorderingAllowed(false);
		jtTable.getTableHeader().setFont(new Font("cambria", Font.PLAIN, 16));
		jtTable.setRowHeight(22);
		jtTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jtTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				int row = jtTable.getSelectedRow();
				int col = jtTable.getSelectedColumn();
				if(me.getClickCount() == 2 && col == 1) {
					displayMedicineInfo(row, col);
				}
			}
		});
		
		sc = new JScrollPane(jtTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(sc, BorderLayout.CENTER);
		sc.setVisible(false);
		
		// Adding button to save the table in CSV format
		
		btnSave = new JButton("Save as CSV");
		btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSave.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnSave.setFont(new Font("cambria", Font.PLAIN, 16));
		btnSave.setVisible(false);
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				// Action Performed to save the table as csv
				JFileChooser fileChooser = new JFileChooser();
	        	fileChooser.setDialogTitle("Save report table");
	       		
	        	int fileSelected = fileChooser.showSaveDialog(null);
	        	if(fileSelected == JFileChooser.APPROVE_OPTION) {
	       			File fileToSave = fileChooser.getSelectedFile();
	       			String filePath = fileToSave.getAbsolutePath();
	       			filePath += ".csv";
	       			boolean res = new DatabaseOperations().saveRetailerDetail(filePath, phone);
	       			if(res) {
	       				JOptionPane.showMessageDialog(getRootPane(), "File saved succesfully");
	       			}else {
	       				JOptionPane.showMessageDialog(getRootPane(), "Unable to save the file");
        			}
	        	}
			}
		});
		this.add(new JPanel().add(btnSave), BorderLayout.SOUTH);
		
		
	}
	private void clearTable() {
		int totalRow = tableModel.getRowCount();
		for(int i=totalRow -1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	private void fillTable() {
		String phoneString = tfPhone.getText();
		if(Pattern.matches("[0-9]+", (CharSequence)phoneString) && phoneString.length() == 10) {
			long phone = Long.parseLong(phoneString);
			ArrayList<String[]> data = new DatabaseOperations().getRetailerDetails(phone);
			if(data.size() == 0) {
				JOptionPane.showMessageDialog(getRootPane(), "No data found");
				sc.setVisible(false);
				btnSave.setVisible(false);
				return;
			}
			clearTable();
			for(String []value : data) {
				tableModel.addRow(value);
			}
			this.phone = phone;
			sc.setVisible(true);
			btnSave.setVisible(true);
			this.validate();
			this.revalidate();
			// After fitting all value visible the table.
		}else {
			JOptionPane.showMessageDialog(getRootPane(), "Invalid mobile number!");
		}
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
}

