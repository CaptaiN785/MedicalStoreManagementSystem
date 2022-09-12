package UI;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MonthlyBuyingReport extends JPanel {
	
	String months[] = {"January", "February", "March",
			"April", "May","June", "July", "August", 
			"September", "October", "November", "December"};
	
	String year[] = {"2022", "2023", "2024"};
	JComboBox cbMonth, cbYear;
	
	JButton btnFetchDetail;
	JLabel lbSelectMonth, lbSelectYear;
	
	JPanel searchPanel;
	
	Frame parentFrame;
	MonthlyBuyingReport(Frame parentFrame){
		
		this.parentFrame = parentFrame;
		this.setLayout(new BorderLayout());
		
		// Labels for search panel
		lbSelectMonth = new JLabel("Select month");
		lbSelectYear = new JLabel("Select year");
		
		// Combo box for month and year
		cbMonth = new JComboBox(months);
		cbYear = new JComboBox(year);
		
		// button to fetch the details
		btnFetchDetail = new JButton("Fetch details");
		btnFetchDetail.setFont(new Font("cambria", Font.PLAIN, 16));
		btnFetchDetail.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		btnFetchDetail.setCursor(new Cursor(Cursor.HAND_CURSOR));		
		
		searchPanel = new JPanel();
		searchPanel.add(lbSelectMonth);
		searchPanel.add(cbMonth);
		searchPanel.add(lbSelectYear);
		searchPanel.add(cbYear);
		searchPanel.add(btnFetchDetail);
		searchPanel.setBorder(new EmptyBorder(new Insets(20, 0, 20, 0)));
		
		this.add(searchPanel, BorderLayout.NORTH);
	}	
}
