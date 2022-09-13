package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import Database.DatabaseOperations;

public class MonthlyBuyingReport extends JPanel {
	//
	String months[] = {"January", "February", "March",
			"April", "May","June", "July", "August", 
			"September", "October", "November", "December"};
	
	String year[] = {"2022", "2023", "2024"};
	JComboBox cbMonth, cbYear;
	//========
	JButton btnFetchDetail, btnSave;
	JLabel lbSelectMonth, lbSelectYear;
	//=============
	JPanel searchPanel, tablePanel;
	Frame parentFrame;
	//=========
	
	JTable jtTable;
	DefaultTableModel tableModel;
	String tableName; // the report table name ie.dailyreport_7_2022
	//
	Box medicineLayout;
	JButton btnHideMedicine;
	
	MonthlyBuyingReport(Frame parentFrame){
		
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		Font font = new Font("cambria", Font.PLAIN, 16);
		
		
		// Labels for search panel
		lbSelectMonth = new JLabel("Select month");
		lbSelectMonth.setFont(font);
		lbSelectYear = new JLabel("Select year");
		lbSelectYear.setFont(font);
		
		// Combo box for month and year
		cbMonth = new JComboBox(months);
		cbYear = new JComboBox(year);
		
		// button to fetch the details
		btnFetchDetail = new JButton("Fetch details");
		btnFetchDetail.setFont(new Font("cambria", Font.PLAIN, 16));
		btnFetchDetail.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnFetchDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));				
		btnFetchDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fillTable();// used to fill the table
			}
		});
		
		searchPanel = new JPanel();
		searchPanel.add(lbSelectMonth);
		searchPanel.add(cbMonth);
		searchPanel.add(lbSelectYear);
		searchPanel.add(cbYear);
		searchPanel.add(btnFetchDetail);
		searchPanel.setBorder(new EmptyBorder(new Insets(20, 0, 20, 0)));
		
		// Now Adding the table
		String columns[] = {"Medicine ID", "Date", "Time", "Name", "Added"};
		tableModel = new DefaultTableModel(columns, 0) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		jtTable = new JTable(tableModel);
		jtTable.setFont(font);
		jtTable.getTableHeader().setReorderingAllowed(false);
		// it will restrict to reorder the column names
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
            }
        }});  
		
		
		JScrollPane sc = new JScrollPane(jtTable);
		// creating a table panel
		tablePanel = new JPanel(new BorderLayout());
		tablePanel.add(sc, BorderLayout.CENTER);
		tablePanel.setVisible(false);
		
		// Button to save the table as csv file
		btnSave = new JButton("Save as CSV");
		btnSave.setFont(font);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
	        	JFileChooser fileChooser = new JFileChooser();
	        	fileChooser.setDialogTitle("Save report table");
	       		
	        	int fileSelected = fileChooser.showSaveDialog(null);
	        	if(fileSelected == JFileChooser.APPROVE_OPTION) {
	       			File fileToSave = fileChooser.getSelectedFile();
	       			String filePath = fileToSave.getAbsolutePath();
	       			filePath += ".csv";
	       			boolean res = new DatabaseOperations().saveMonthlyBuyingDetails(tableName, filePath);
	       			if(res) {
	       				JOptionPane.showMessageDialog(getRootPane(), "File saved succesfully");
	       			}else {
	       				JOptionPane.showMessageDialog(getRootPane(), "Unable to save the file");
        			}
	        	}
			}
		});
		tablePanel.add(new JPanel().add(btnSave), BorderLayout.SOUTH);
		
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(tablePanel, BorderLayout.CENTER);
	}	
	private void fillTable() {
		
		int month = cbMonth.getSelectedIndex() + 1;
		String year = (String)cbYear.getSelectedItem();
		
		String name = "DailyReport_" + month + "_"+ year;
		
		Object obj[] = new DatabaseOperations().getMonthlyBuyingReport(name);
		int response_code = (int)obj[0];
		if(response_code == 1) {
			// add element to table
			tableName = name;
			ArrayList<Object[]> data = (ArrayList<Object[]>)obj[1];
			for(Object[] row : data) {
				tableModel.addRow(row);
			}
			tablePanel.setVisible(true);
		}else if(response_code == 0) {// means no record
			JOptionPane.showMessageDialog(getRootPane(), "No record found!");
		}else if(response_code == 3) {// no database table present
			JOptionPane.showMessageDialog(getRootPane(), "No transaction found!");
		}else {// database error.
			JOptionPane.showMessageDialog(getRootPane(), "Database error!");
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
