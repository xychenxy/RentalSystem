package Model.Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Statement;

public class InsertRow {
	
	public static boolean insertRow(String tableName, String insertValues) {
		final String DB_NAME = "flexiRentalSystemDB";
		String TABLE_NAME = tableName;
		String INSERT_VALUES = insertValues;
		boolean isInsertRow = false;
		
//		String teString = " VALUES ('A_108CRSB', '108 City Road Southback', 'Apartment', 3,'Available','23/07/3018','flexiRentalSystemDB flexiRentalSystemDB flexiRentalSystemDB')";
		
		//use try-with-resources Statement
		try (Connection con = ConnectionToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();
		) {
			String query = "INSERT INTO " + TABLE_NAME + INSERT_VALUES;
			System.out.println(query);
			int result = stmt.executeUpdate(query);
			
			con.commit();
			
			isInsertRow = true;
			
//			System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
//			System.out.println(result + " row(s) affected");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return isInsertRow;
	}
}
