package Database;

import java.sql.*;
import java.util.Calendar;

public class DatabaseInitialization extends DatabaseConnection{
	private  Connection dbConnection = null;
	
	// Convert the all void to boolean in order to get 
	// response of success of failed.
	// create a method that will initialize the database.
	public DatabaseInitialization() {
		super();// For loading the driver.
		dbConnection = super.getConnection(false);
		boolean database = createDatabase();
		if(database) {
			System.out.println("Database is created.");
		}else {
			System.out.println("Database is not created.");
		}
	}
	protected boolean InitializeTables() {
		boolean supplier = createSupplierTable();
		boolean medicine = createMedicineTable();
		boolean report = createDailyReportTable();
		
		return supplier && medicine && report;		
	}

	private boolean createDatabase() {
		try {
			Statement st = this.dbConnection.createStatement();
			String sql = "CREATE DATABASE IF NOT EXISTS " + DB;
			
			st.executeUpdate(sql);
			this.dbConnection = super.getConnection(true);
			System.out.println("Database is created.");
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to create the database." + e.getMessage());
			
		}
		return false;
	}
	
	private boolean createSupplierTable() {
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
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to create the supplier table" + e.getMessage());
		}
		return false;
	}
	private boolean createMedicineTable() {
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
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to create the Medicines table" + e.getMessage());
		}
		return false;
	}
	
	private boolean createDailyReportTable() {
		try {
			Statement st = this.dbConnection.createStatement();
			
			String table_name = getDailyReportTableName();
			
			String sql = "CREATE TABLE IF NOT EXISTS "
					+ ""+ table_name + " ( "
					+ "DATE Date, "
					+ "TIME TIME, "
					+ "MID INT, "
					+ "QUANTITY INT, "
					+ "CONSTRAINT `report_medicines_" + table_name + "` "
					+ "FOREIGN KEY (MID) REFERENCES " + MEDICINES + "(MID) "
					+ "ON DELETE CASCADE "
					+ "ON UPDATE CASCADE "
					+ ")";
			st.executeUpdate(sql);
			
			System.out.println("Daily report table is created.");
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to create dailyreport tables " + e.getMessage());
		}
		return false;
	}
	
	public static String getDailyReportTableName() {
		
		Calendar today = Calendar.getInstance();
		
		int month = today.get(Calendar.MONTH);
		int year = today.get(Calendar.YEAR);

		String name = "DailyReport_" + month + "_"+ year;
		return name;
	}
	
	public static void main(String[] args) {
		new DatabaseInitialization();
	}
}