package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

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
		
	public static void main(String[] args) {
		ArrayList<String> s;
		ArrayList<Integer> i;
		Object[] res = new DatabaseOperations().getSuppliers();
		s = (ArrayList<String>)res[0];
	}

}
