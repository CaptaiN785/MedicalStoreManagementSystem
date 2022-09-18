package UI;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Database.DatabaseOperations;

public class MonthlySoldReport extends JPanel{
	
	JLabel lbSelectMonth, lbSelectYear, lbHeading;
	JComboBox cbSelectMonth, cbSelectYear;
	JButton btnSubmit, btnSave, btnHideMedicine;
	JPanel headingPanel, tablePanel, searchPanel;
	Frame parentFrame;
	
	JTable jtTable;
	DefaultTableModel tableModel;
	JScrollPane sc;
	
	Box medicineLayout;
	int month, year;
	
	MonthlySoldReport(Frame parentFrame){
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		
		
		lbHeading = new JLabel("Monthly Sold report");
		lbHeading.setFont(new Font("cambria", Font.BOLD, 25));
		headingPanel = new JPanel(new BorderLayout());
		JPanel temp = new JPanel();
		temp.add(lbHeading);
		headingPanel.add(temp, BorderLayout.NORTH);
		this.add(headingPanel, BorderLayout.NORTH);
		
		// Search Panel
		searchPanel = new JPanel();
		// search panel attributes
		lbSelectMonth = new JLabel("Select Month");
		lbSelectYear = new JLabel("Select year");
		
		String months[] = {"January", "February", "March", "April",
				"May", "June", "July","August", "September",
				"October", "November", "December"};
		String years[] = {"2021", "2022", "2023"};
		
		cbSelectMonth = new JComboBox(months);
		cbSelectYear = new JComboBox(years);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// show tables of selected month
//				System.out.println("Action performed");
				fillTable();
			}
		});
		// Styling the fonts
		styleFont(lbSelectMonth, lbSelectYear, cbSelectMonth, cbSelectYear, btnSubmit);
	
		// Adding the components to searchPanel
		searchPanel.add(lbSelectMonth);
		searchPanel.add(cbSelectMonth);
		searchPanel.add(lbSelectYear);
		searchPanel.add(cbSelectYear);
		searchPanel.add(btnSubmit);
		headingPanel.add(searchPanel, BorderLayout.CENTER);
		
		// Now table works
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
	       			boolean res = new DatabaseOperations().saveMonthlySoldReport(month, year, filePath);
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
	private void styleFont(JComponent ...objects) {
		for(JComponent obj : objects) {
			obj.setFont(new Font("cambria", Font.PLAIN, 16));
		}
	}
	private void clearTable() {
		int totalRow = tableModel.getRowCount();
		for(int i=totalRow -1; i >= 0; i--) {
			tableModel.removeRow(i);
		}
	}
	private void fillTable() {
		int month = cbSelectMonth.getSelectedIndex() + 1;
		int year = Integer.parseInt(cbSelectYear.getSelectedItem().toString());
		ArrayList<String[]> data = new DatabaseOperations().getMonthlySoldReport(month, year);
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
		this.month = month;
		this.year = year;
		sc.setVisible(true);
		btnSave.setVisible(true);
		this.validate();
		this.revalidate();
		// After fitting all value visible the table.
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
