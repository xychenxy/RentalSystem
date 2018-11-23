package Model;

public class Message {
	
	private static String type = "Type";
	private static String bedrooms = "Bedrooms";
	private static String status = "Status";
	private static String suburb = "Suburb";
	
	public static void resetToAny() {
		type = "Type";
		bedrooms = "Bedrooms";
		status = "Status";
		suburb = "Suburb";
	}
	
	public static String getType() {
		return type;
	}
	public static void setType(String type) {
		Message.type = type;
	}
	public static String getBedrooms() {
		return bedrooms;
	}
	public static void setBedrooms(String bedrooms) {
		Message.bedrooms = bedrooms;
	}
	public static String getStatus() {
		return status;
	}
	public static void setStatus(String status) {
		Message.status = status;
	}
	public static String getSuburb() {
		return suburb;
	}
	public static void setSuburb(String suburb) {
		Message.suburb = suburb;
	}
	
	
}
