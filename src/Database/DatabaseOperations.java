package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import jdk.jshell.StatementSnippet;

public class DatabaseOperations extends DatabaseConnection{
	
	public DatabaseOperations() {
		super();
	}
	
	public boolean addSupplier(String name, String director, 
			String address, String phone, String email) {
		
		/*
		Supplier table descriptions
		+----------+--------------+------+-----+---------+----------------+
		| Field    | Type         | Null | Key | Default | Extra          |
		+----------+--------------+------+-----+---------+----------------+
		| SID      | int(11)      | NO   | PRI | NULL    | auto_increment |
		| NAME     | varchar(255) | YES  |     | NULL    |                |
		| DIRECTOR | varchar(100) | YES  |     | NULL    |                |
		| ADDRESS  | varchar(255) | YES  |     | NULL    |                |
		| PHONE    | varchar(10)  | YES  |     | NULL    |                |
		| EMAIL    | varchar(100) | YES  |     | NULL    |                |
		| REGDATE  | datetime     | YES  |     | NULL    |                |
		+----------+--------------+------+-----+---------+----------------+ 
		*/
		
		String sql = "INSERT INTO SUPPLIER(NAME, DIRECTOR, ADDRESS, PHONE, EMAIL, REGDATE)"
				+ " VALUES("
				+ "'" + name + "',"
				+ "'" + director + "',"
				+ "'" + address + "',"
				+ "'" + phone + "',"
				+ "'" + email + "',"
				+ "now() " 
				+ ")";
		
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(sql);
			System.out.println("Supplier added successfully.");
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to insert the supplier details." + e.getLocalizedMessage());
		}
		return false;		
	}

	
	public boolean updateSupplier(String name, String director, 
			String address, String phone, String email, int sid) {
		String updateSql = "UPDATE SUPPLIER SET "
				+ "NAME = '" + name + "',"
				+ "DIRECTOR = '" + director + "',"
				+ "ADDRESS = '" + address + "',"
				+ "PHONE = '" + phone + "',"
				+ "EMAIL = '" + email + "'"
				+ " WHERE SID = " + sid;
		
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(updateSql);
			System.out.println("Supplier updated successfully.");
			return true;
		} catch (SQLException e) {
			System.out.println("Unable to update the supplier details." + e.getLocalizedMessage());
		}
		
		return false;
	}
	
	public boolean addMedicine(String name, int cost, int sid) {
		/*
		+----------+--------------+------+-----+---------+----------------+
		| Field    | Type         | Null | Key | Default | Extra          |
		+----------+--------------+------+-----+---------+----------------+
		| MID      | int(11)      | NO   | PRI | NULL    | auto_increment |
		| NAME     | varchar(255) | YES  |     | NULL    |                |
		| REGDATE  | datetime     | YES  |     | NULL    |                |
		| QUANTITY | int(11)      | YES  |     | NULL    |                |
		| COST     | int(11)      | YES  |     | NULL    |                |
		| SID      | int(11)      | YES  | MUL | NULL    |                |
		+----------+--------------+------+-----+---------+----------------+
		*/
			
		String sql = "INSERT INTO MEDICINES(NAME, REGDATE, QUANTITY, COST, SID) "
				+ "VALUES ("
				+ "'" + name + "', "
				+ "now(), "
				+ " 0 ,"
				+ "" + cost + ", "
				+ "" + sid + ""
				+ ")";
		
						
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(sql);
			System.out.println("Medicine added successfully");
			return true;
		}catch(SQLException e) {
			System.out.println("Unabel to add medicine, please check supplier id "
					+ "or database connection" + e.getMessage());
		}
		return false;
	}
	
	public boolean updateMedicine(String name, int cost, int sid,int quantity, int mid) {
		String updateSql = "UPDATE MEDICINES SET "
				+ "NAME = '" + name + "',"
				+ "QUANTITY = " + quantity + ", "
				+ "COST = " + cost + ", "
				+ "SID = " + sid + " "
 				+ "WHERE MID = " + mid;

		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(updateSql);
			System.out.println("Medicine updated successfully");
			return true;
		}catch(SQLException e) {
			System.out.println("Unabel to update medicine, please check supplier id "
					+ "or database connection or medicine id" + e.getMessage());
		}
		return false;
	}
/* daily report table descriptions
+----------+---------+------+-----+---------+-------+
| Field    | Type    | Null | Key | Default | Extra |
+----------+---------+------+-----+---------+-------+
| DATE     | date    | YES  |     | NULL    |       |
| TIME     | time    | YES  |     | NULL    |       |
| MID      | int(11) | YES  | MUL | NULL    |       |
| QUANTITY | int(11) | YES  |     | NULL    |       |
+----------+---------+------+-----+---------+-------+
 */
	
	public Object[][] getMedicineList(int sid){
		
		// if sid = -1 then return all medicine list else return 
		// medicine of sid given.
		
		// firstly find the no of medicines in the database
		String sql = "SELECT COUNT(*) AS TOTAL FROM " + MEDICINES;
		
		if(sid != -1) {
			sql += " WHERE SID = " + sid;
		}
		
		Object[][] medicineList;
		
		try {
			
			Connection conn = getConnection(true);
			Statement st = conn.createStatement();
			
			ResultSet result = st.executeQuery(sql);
			if(result.next()) {
				int rows = Integer.parseInt(result.getString(1));
				medicineList = new Object[rows][4];
				
				sql = "SELECT MID, NAME, QUANTITY, SID FROM " + MEDICINES;
				
				if(sid != -1) {
					sql += " WHERE SID = " + sid;
				}
				
				sql += " ORDER BY QUANTITY";
				
				result = st.executeQuery(sql);
				int r = 0;
				while(result.next()) {
					medicineList[r][0] = result.getString(1);
					medicineList[r][1] = result.getString(2);
					medicineList[r][2] = result.getString(3);
					medicineList[r][3] = result.getString(4);
					r++;
				}
				return medicineList;
			}
		}catch(Exception e) {
			System.out.println("Unable to fetch the data" + e.getMessage());
		}
		Object[][] obj = new Object[0][0];
		return obj;
	}
	
	public boolean addMoreMedicines(int quantity, int mid) {
		String table_name = DatabaseInitialization.getDailyReportTableName();

		String sql = "INSERT INTO " + table_name + " VALUES( "
				+ "CURRENT_DATE(),CURRENT_TIME(),"
				+ "" + mid + ","
				+ "" + quantity + ")";
		
		String medicine_table_sql = "UPDATE " + MEDICINES + " "
				+ "SET QUANTITY = QUANTITY  + "
				+ ""+ quantity + " "
				+ " where mid = " + mid;
		
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(sql);
			st.executeUpdate(medicine_table_sql);
			System.out.println("Medicine quantity successfully");
			return true;
		}catch(SQLException e) {
			System.out.println("Unable to add more medicines." + e.getMessage());
		}
		return false;
	}
	
	public boolean deleteSupplier(int sid) {
		
		String sql = "DELETE FROM " + SUPPLIER + " WHERE SID = " + sid;
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(sql);
			System.out.println("Supplier deleted successfully");
			return true;
		}catch(SQLException e) {
			System.out.println("Cann't delete supplier, please check all medicines from"
					+ " this supplier." + e.getMessage());
		}
		return false;
	}
	
	public boolean deleteMedicine(int mid) {
		String sql = "DELETE FROM "+ MEDICINES +"  WHERE MID = " + mid;
		try {
			Statement st = this.getConnection(true).createStatement();
			st.executeUpdate(sql);
			System.out.println("Medicine deleted successfully");
			return true;
		}catch(SQLException e) {
			System.out.println("Unable to delete medicine. " + e.getMessage());
		}
		return false;
	}
	
	public Object[] getSuppliers() {
		String sql = "SELECT * FROM " + SUPPLIER;
		
		ArrayList<String> suppliers = new ArrayList<>();
		ArrayList<Integer> supplier_index = new ArrayList<>();
		
		// it will show to select the supplier in combo box
		suppliers.add("Select supplier");
		supplier_index.add(0);
		
		try {
			Statement st = this.getConnection(true).createStatement();
			ResultSet result = st.executeQuery(sql);
//			result.l/
			while(result.next()) {
				suppliers.add(result.getString("NAME"));
				supplier_index.add(result.getInt("SID"));
			}
			return new Object[] {suppliers, supplier_index};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Object[] {};
	}
		
	public String[] getSupplierDetails(int sid) {
		
		String sql = "SELECT * FROM " + SUPPLIER + " WHERE SID = " + sid;
		String info[] = new String[7];
		try {
			Statement st = getConnection(true).createStatement();
			ResultSet result = st.executeQuery(sql);
			if(result.next()) {
				info[0] = String.valueOf(result.getInt("SID"));
				info[1] = result.getString("NAME");
				info[2] = result.getString("DIRECTOR");
				info[3] = result.getString("PHONE");
				info[4] = result.getString("EMAIL");
				info[6] = result.getString("REGDATE");
				info[5] = result.getString("ADDRESS");
				return info;
			}
		}catch(SQLException e) {
			System.out.println("Database error." + e.getMessage());
		}
		return new String[] {};
	}	
	
	public Object[] getMedicines() {
		String sql = "SELECT * FROM " + MEDICINES;
		
		ArrayList<String> medicines = new ArrayList<>();
		ArrayList<Integer> medicine_index = new ArrayList<>();
		
		// it will show to select the medicine in combo box
		medicines.add("Select medicine");
		medicine_index.add(0);
		
		try {
			Statement st = this.getConnection(true).createStatement();
			ResultSet result = st.executeQuery(sql);
			
			while(result.next()) {
				medicines.add(result.getString("NAME"));
				medicine_index.add(result.getInt("MID"));
			}
			return new Object[] {medicines, medicine_index};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Object[] {};
	}

	public String[] getMedicineDetails(int mid) {
		String sql = "SELECT * FROM " + MEDICINES + " WHERE MID = " + mid;
		String info[] = new String[6];
		try {
			Statement st = getConnection(true).createStatement();
			ResultSet result = st.executeQuery(sql);
			if(result.next()) {
				info[0] = String.valueOf(result.getInt("MID"));
				info[1] = result.getString("NAME");
				info[2] = result.getString("QUANTITY");
				info[3] = result.getString("COST");
				info[4] = result.getString("REGDATE");
				info[5] = result.getString("SID");
				// returns mid, name, quantit, cost, regdate, sid
				return info;
			}
		}catch(SQLException e) {
			System.out.println("Database error." + e.getMessage());
		}
		return new String[] {};
	}
	
	public boolean isSupplierPresent(int sid) {
		
		String sql = "SELECT * FROM " + SUPPLIER + " WHERE SID = " + sid;
		
		try {
			Statement st = getConnection(true).createStatement();
			ResultSet result = st.executeQuery(sql);
			
			if(result.next()) {
				return true;
			}
		}catch(Exception e) {
			System.out.println("Database error while checking supplier id");
		}
		return false;
	}
	public static void main(String[] args) {
		String result[] = new DatabaseOperations().getSupplierDetails(10);
		if(result.length == 0) {
			System.out.println("Not found");
		}
		for(String res : result) {
			System.out.println(res);
		}
	}

}
