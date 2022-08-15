package Database;

import java.sql.*;

public class DatabaseInitialization extends DatabaseConnection{
	private  Connection dbConnection = null;
	
	// Convert the all void to boolean in order to get response of success of failed.
	// create a seperate method that will initialize the database.
	public DatabaseInitialization() {
		dbConnection = super.getConnection(false);
		createDatabase();
		createSupplierTable();
		createMedicineTable();
		System.out.println("Database is initialized successfully");
	}
	private void createDatabase() {
		try {
			Statement st = this.dbConnection.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS " + DB;
			
			st.executeUpdate(sql);
			this.dbConnection = super.getConnection(true);
			System.out.println("Database is created.");
			
		} catch (SQLException e) {
			System.out.println("Unable to create the database." + e.getMessage());
		}
	}
	
	private void createSupplierTable() {
		try {
			Statement st = this.dbConnection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "
							+ SUPPLIER +"("
							+ "SID INT PRIMARY KEY AUTO_INCREMENT, "
							+ "NAME VARCHAR(255), "
							+ "DIRECTOR VARCHAR(100), "
							+ "ADDRESS VARCHAR(255), "
							+ "PHONE VARCHAR(10), "
							+ "EMAIL VARCHAR(100), "
							+ "REGDATE DATETIME "
							+ ")";
			st.executeUpdate(sql);
			System.out.println(SUPPLIER + " table is created.");
			
		} catch (SQLException e) {
			System.out.println("Unable to create the supplier table" + e.getMessage());
		}
	}
	private void createMedicineTable() {
		try {
			Statement st = this.dbConnection.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS "
							+ MEDICINES +"("
							+ "MID INT PRIMARY KEY AUTO_INCREMENT, "
							+ "NAME VARCHAR(255), "
							+ "REGDATE DATETIME, "
							+ "QUANTITY INT, "
							+ "COST INT, "
							+ "SID INT, "
							+ "CONSTRAINT `supplier_medicines` "
							+ "FOREIGN KEY (SID) REFERENCES " + SUPPLIER + "(SID) "
							+ "ON DELETE RESTRICT "
							+ "ON UPDATE CASCADE "
							+ ")";
//			System.out.println(sql);
			st.executeUpdate(sql);
			System.out.println(MEDICINES + " table is created.");
			
		} catch (SQLException e) {
			System.out.println("Unable to create the Medicines table" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DatabaseInitialization();
	}
}
