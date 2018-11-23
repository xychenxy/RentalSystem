package Model.Sql;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SelectQuery {
	
	public static ArrayList<String> selectData(String tableName) {
		final String DB_NAME = "flexiRentalSystemDB";
		String TABLE_NAME = tableName;
		ArrayList<String> rentalProperty = new ArrayList<>();
		ArrayList<String> rentalReocrd = new ArrayList<>();
		
		
		try (Connection con = ConnectionToDB.getConnection(DB_NAME);Statement stmt = con.createStatement();) {
			String query = "SELECT * FROM " + TABLE_NAME;
			
			try (ResultSet resultSet = stmt.executeQuery(query)) {
				
				switch (TABLE_NAME) {
				case "RENTAL_PROPERTY":
					while(resultSet.next()) {
						String temp = resultSet.getString("property_id")+":"+resultSet.getString("number")+":"+resultSet.getString("address")+":"+
								resultSet.getString("suburb")+":"+resultSet.getString("type")+":"+resultSet.getInt("bedroom")+":"+
								resultSet.getString("status")+":"+resultSet.getString("last_maintenance")+":"+resultSet.getString("image_name")+":"+
								resultSet.getString("description");
//						System.out.println(temp);
						rentalProperty.add(temp);
					}
					break;
				case "RENTAL_RECORD":
					while(resultSet.next()) {
						String temp = resultSet.getString("record_id")+":"+resultSet.getString("rent_date")+":"+resultSet.getString("estimated_return_date")+":"+
								resultSet.getString("Actual_return_date")+":"+resultSet.getString("rental_fee")+":"+
								resultSet.getString("late_fee")+":"+resultSet.getString("property_id");
//						System.out.println(temp);
						rentalProperty.add(temp);
					}
					break;
				default:
					break;
				}
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return rentalProperty;
	}
}
