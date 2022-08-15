package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class  DatabaseConnection{
	
	// Protected variables
	protected static final String DB = "MEDICAL";
	protected static String SUPPLIER = "SUPPLIER";
	protected static String MEDICINES = "MEDICINES";
	
	// Private variable 
	private Connection conn = null;
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static boolean isDriverLoaded = false;
	
	protected DatabaseConnection(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver loaded successfully");	
			isDriverLoaded = true;
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found " + ex.getMessage());
		}
	}
	
	protected Connection getConnection(boolean hasDatabase) {
		String database = "";
		if(hasDatabase) {
			database = DB;
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database,USER, PASSWORD);
			System.out.println("Database is connected");
		} catch (SQLException e) {
			System.out.println("Unable to make connection" + e.getMessage());
		}
		return conn;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DatabaseConnection();
	}

}
