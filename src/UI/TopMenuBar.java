package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class TopMenuBar extends JMenuBar {
	
	JMenu medicine, supplier, report, service;
	JMenuItem addMedicine, medicineList, updateInventory, searchMedicine;
	JMenuItem addSupplier, updateSupplier, deleteSupplier, searchSupplier;
	
	JMenuItem checkoutMedicine, monthlyBuyingReport, monthlySoldReport, retailerDetail, logout;
	
	public TopMenuBar(Frame parentFrame) {
		// Working with medicine Menu
		medicine = new JMenu("Medicine");
		medicine.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// defining the menuItemes for medicine menu
		addMedicine = new JMenuItem("Add medicine");
		addMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new AddMedicine(parentFrame));
			}
		});
		medicineList = new JMenuItem("Medicine list");
		medicineList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new MedicineList(parentFrame, false, null));
			}
		});
		searchMedicine = new JMenuItem("View medicine");
		searchMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new SearchMedicine(parentFrame, true, 4));
			}
		});
		
		updateInventory = new JMenuItem("Update Inventory");
		updateInventory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new UpdateInventory(parentFrame));
			}
		});
		
		medicine.add(addMedicine);
		medicine.addSeparator();
		medicine.add(medicineList);
		medicine.addSeparator();
		medicine.add(searchMedicine);
		medicine.add(updateInventory);
		
		// Working with supplier menu
		supplier = new JMenu("Supplier");
		supplier.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		// defining the menuItems for suppliers
		addSupplier = new JMenuItem("Add supplier.");
		addSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new AddSupplier(parentFrame));
			}
		});
		
//		updateSupplier = new JMenuItem("Update supplier");
//		updateSupplier.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				addAnotherPanel(parentFrame, new UpdateSupplier(parentFrame));
//			}
//		});
//		
		searchSupplier = new JMenuItem("View supplier");
		searchSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new SearchSupplier(parentFrame, true, 0));
			}
		});
//		deleteSupplier = new JMenuItem("Delete Supplier");
//		deleteSupplier.setForeground(Color.RED);
//		deleteSupplier.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent ae) {
//				addAnotherPanel(parentFrame, new DeleteSupplier(parentFrame));
//			}
//		});
		
		supplier.add(addSupplier);
//		supplier.add(updateSupplier);
		supplier.addSeparator();
		supplier.add(searchSupplier);
//		supplier.add(deleteSupplier);
		
		
		// Service menu Items
		service = new JMenu("Services");
		service.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		checkoutMedicine = new JMenuItem("Checkout");
		service.add(checkoutMedicine);
		checkoutMedicine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new SellMedicine(parentFrame));
			}
		});
		monthlyBuyingReport = new JMenuItem("Monthly buying report");
		service.addSeparator();
		service.add(monthlyBuyingReport);
		monthlyBuyingReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new MonthlyBuyingReport(parentFrame));
			}
		});
		
		monthlySoldReport = new JMenuItem("Monthly sold report");
		service.add(monthlySoldReport);
		monthlySoldReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new MonthlySoldReport(parentFrame));
			}
		});
		
		retailerDetail = new JMenuItem("Retailer details");
		service.add(retailerDetail);
		retailerDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				addAnotherPanel(parentFrame, new RetailerDetails(parentFrame));
			}
		});
		
		
		logout = new JMenuItem("Logout");
		service.addSeparator();
		service.add(logout);
		logout.setForeground(Color.red);
		logout.setFont(new Font("cambria", Font.HANGING_BASELINE, 16));
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int res = JOptionPane.showConfirmDialog(getRootPane(), "Do you want to logout?");
				if(res == JOptionPane.YES_OPTION) {
					parentFrame.logout();
				}
			}
		});
		
		// styling the all menu items
		styleMenuItems(addMedicine, medicineList, updateInventory, searchMedicine,
	addSupplier, searchSupplier,
	checkoutMedicine, monthlyBuyingReport, monthlySoldReport, retailerDetail,logout);
		
		// Styling the Menu
		styleMenu(medicine, supplier, service);
		
		// Final addition of menu to the menu bar
		this.add(medicine);
		this.add(supplier);
		this.add(service);
	}
	public void addAnotherPanel(Frame frame, JPanel panel) {
		frame.remove(frame.currentPanel);
		frame.currentPanel = panel;
		frame.add(frame.currentPanel, BorderLayout.CENTER);
		frame.refreshDisplay();
	}
	private void styleMenuItems(JMenuItem ...items) {
		for(JMenuItem item: items) {
			item.setFont(new Font("cambria", Font.PLAIN, 16));
			item.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}
	private void styleMenu(JMenu ...items) {
		for(JMenu item: items) {
			item.setFont(new Font("cambria", Font.PLAIN, 16));
			item.setBorder(new EmptyBorder(new Insets(0, 10, 0, 10)));
		}
	}
}
