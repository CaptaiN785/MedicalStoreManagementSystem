package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TopMenuBar extends JMenuBar {
	
	JMenu medicine, supplier, report;
	JMenuItem addMedicine, updateMedicine, deleteMedicine, searchMedicine;
	JMenuItem addSupplier, updateSupplier, deleteSupplier, searchSupplier;
	
	public TopMenuBar(Frame parentFrame) {
		// Working with medicine Menu
		medicine = new JMenu("Medicine");
		medicine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// defining the menuitemes for medicine menu
		addMedicine = new JMenuItem("Add medicine");
		addMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new AddMedicine(parentFrame));
			}
		});
		
		updateMedicine = new JMenuItem("Update medicine");
		updateMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new UpdateMedicine(parentFrame));
			}
		});
		searchMedicine = new JMenuItem("Search medicine");
		searchMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new UpdateMedicine(parentFrame));
			}
		});
		
		deleteMedicine = new JMenuItem("Delete medicine");
		deleteMedicine.setForeground(Color.RED);
		deleteMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new DeleteSupplier(parentFrame));
			}
		});
		
		medicine.add(addMedicine);
		medicine.add(updateMedicine);
		medicine.add(searchMedicine);
		medicine.addSeparator();
		medicine.add(deleteMedicine);
		
		// Working with supplier menu
		supplier = new JMenu("Supplier");
		supplier.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// defining the menuitems for suppliers
		addSupplier = new JMenuItem("Add supplier.");
		addSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new AddSupplier(parentFrame));
			}
		});
		
		updateSupplier = new JMenuItem("Update supplier");
		updateSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new UpdateSupplier(parentFrame));
			}
		});
		
		searchSupplier = new JMenuItem("Search supplier");
		searchSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new SearchSupplier(parentFrame));
			}
		});
		deleteSupplier = new JMenuItem("Delete Supplier");
		deleteSupplier.setForeground(Color.RED);
		deleteSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new DeleteSupplier(parentFrame));
			}
		});
		
		supplier.add(addSupplier);
		supplier.add(updateSupplier);
		supplier.add(searchSupplier);
		supplier.addSeparator();
		supplier.add(deleteSupplier);
		
		// Final addition of menu to the menubar
		this.add(medicine);
		this.add(supplier);
	}
	public void addAnotherPanel(Frame frame, JPanel panel) {
		frame.remove(frame.currentPanel);
		frame.currentPanel = panel;
		frame.add(frame.currentPanel, BorderLayout.CENTER);
		frame.refreshDisplay();
	}
}
