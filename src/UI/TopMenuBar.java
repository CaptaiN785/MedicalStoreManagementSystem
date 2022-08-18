package UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class TopMenuBar extends JMenuBar {
	
	JMenu medicine, supplier, report;
	JMenuItem addMedicine, updateMedicine, deleteMedicine, searchMedicine;
	JMenuItem addSupplier, updateSupplier, deleteSupplier, searchSupplier;
	
	public TopMenuBar() {
		// Working with medicine Menu
		medicine = new JMenu("Medicine");
		medicine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// defining the menuitemes for medicine menu
		addMedicine = new JMenuItem("Add medicine");
		addMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				
			}
		});
		updateMedicine = new JMenuItem("Update medicine");
		deleteMedicine = new JMenuItem("Delete medicine");
		deleteMedicine.setForeground(Color.RED);
		searchMedicine = new JMenuItem("Search medicine");
		
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
		updateSupplier = new JMenuItem("Update supplier");
		searchSupplier = new JMenuItem("Search supplier");
		deleteSupplier = new JMenuItem("Delete Supplier");
		deleteSupplier.setForeground(Color.RED);
		
		supplier.add(addSupplier);
		supplier.add(updateSupplier);
		supplier.add(searchSupplier);
		supplier.addSeparator();
		supplier.add(deleteSupplier);
		
		// Final addition of menu to the menubar
		this.add(medicine);
		this.add(supplier);
	}
	
}
