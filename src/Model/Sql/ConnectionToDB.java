package Model.Sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionToDB {
	
    	public static void connectToDB() {
    		Statement stmt = null;
	    	int result = 0;
	    	final String DB_NAME = "flexiRentalSystemDB";
   	 
	    	try (Connection con = getConnection(DB_NAME)) {
	    		System.out.println("Connection to database " + DB_NAME + " created successfully");
	    		stmt = con.createStatement();
	    	} 
	    	catch (Exception e) {
	    		System.out.println(e.getMessage());
	    	}
    }
    
    public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
   	 //Registering the HSQLDB JDBC driver
    		Class.forName("org.hsqldb.jdbc.JDBCDriver");
    		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "SA", "");
    		return con;
    }
}
