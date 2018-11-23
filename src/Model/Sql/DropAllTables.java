package Model.Sql;

import java.sql.Connection;
import java.sql.Statement;

public class DropAllTables {
	
	public static void dropAllTables() {
		final String DB_NAME = "flexiRentalSystemDB";
		String[] TABLE_NAME = {"RENTAL_RECORD","RENTAL_PROPERTY"};
		for(String iString: TABLE_NAME) {
			executeDrop(DB_NAME, iString);
		}
		
	}
	
	private static void executeDrop(String DB_NAME,String TABLE_NAME) {
		try (Connection con = ConnectionToDB.getConnection(DB_NAME);Statement stmt = con.createStatement();) {
			String query = "DROP TABLE " + TABLE_NAME;
			int result = stmt.executeUpdate(query);
			
			System.out.println("Delete from table " + TABLE_NAME + " executed successfully");
			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}