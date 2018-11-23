package Model.Sql;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class CreateTable {
	
	public static void createAllTables(){
		
		final String DB_NAME = "flexiRentalSystemDB";
		String rentalProperty = "CREATE TABLE rental_property ("
				+ "property_id VARCHAR(30) NOT NULL,"
				+ "number VARCHAR(10) NOT NULL,"
				+ "address VARCHAR(100) NOT NULL," 
				+ "suburb VARCHAR(20) NOT NULL,"
				+ "type VARCHAR(20) NOT NULL,"
				+ "bedroom INT NOT NULL,"
				+ "status VARCHAR(20) NOT NULL,"
				+ "last_maintenance VARCHAR(20),"
				+ "image_name VARCHAR(20),"
				+ "description VARCHAR(1000),"
				+ "PRIMARY KEY (property_id))";
		
		String rentalRecord = "CREATE TABLE rental_record ("
				+ "record_id VARCHAR(30) NOT NULL,"
				+ "rent_date VARCHAR(30) NOT NULL," 
				+ "estimated_return_date VARCHAR(30) NOT NULL,"
				+ "Actual_return_date VARCHAR(30),"
				+ "rental_fee VARCHAR(20),"
				+ "late_fee VARCHAR(20) ,"
				+ "PRIMARY KEY (record_id),"
				+ "property_id VARCHAR(30) FOREIGN KEY REFERENCES rental_property(property_id))";
		
		String[] TABLE_NAME = {"RENTAL_PROPERTY",rentalProperty,"RENTAL_RECORD",rentalRecord};
		
		for(int i=0;i<TABLE_NAME.length;i=i+2) {
			executeCreateTable(DB_NAME, TABLE_NAME[i+1], TABLE_NAME[i]);
		}
	}
	
	private static void executeCreateTable(String DB_NAME, String query, String TABLE_NAME) {
		try (Connection con = ConnectionToDB.getConnection(DB_NAME);
				Statement stmt = con.createStatement();) {
			int result = stmt.executeUpdate(query);
			if(result == 0) {
				System.out.println("Table " + TABLE_NAME + " has been created successfully");
			} else {
				System.out.println("Table " + TABLE_NAME + " is not created");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}