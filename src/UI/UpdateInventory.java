package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Database.DatabaseOperations;

import java.awt.BorderLayout;
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
	
	public UpdateInventory(Frame parentFrame) {
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		
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
	}
}
