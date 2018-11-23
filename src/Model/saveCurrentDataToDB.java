package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Controller.Main;
import Model.Sql.CreateTable;
import Model.Sql.DropAllTables;
import Model.Sql.InsertRow;

public class saveCurrentDataToDB {

	
	public static void saveToDB() {
		DropAllTables.dropAllTables();
		CreateTable.createAllTables();
		
		String propertyInfo = null;
		Iterator<Entry<String, RentalProperty>> iter = Main.flexiRentSystem.getFlexiProperty().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, RentalProperty> entry = (Map.Entry<String, RentalProperty>) iter.next();
			if(entry.getValue() instanceof Apartment) {
				propertyInfo = ((Apartment) entry.getValue()).getPropertyInfo();
			}
			if(entry.getValue() instanceof PremiumSuite) {
				propertyInfo = ((PremiumSuite) entry.getValue()).getPropertyInfo();
			} 
			
			InsertRow.insertRow("RENTAL_PROPERTY", handlePropertyInfo(propertyInfo));
			
			
			ArrayList<String> propertyRecord = addPropertyIDToRecord(entry.getValue(),entry.getKey());
			for(String item:propertyRecord) {
				InsertRow.insertRow("RENTAL_RECORD", handleRecordInfo(item));
			}		
		}
	}
	
	private static String handlePropertyInfo(String propertyInfo) {
		String info = null;
		String[] infoList = propertyInfo.split(":");
		info = " VALUES(" +"'" + infoList[0]+"'";
		for(int i=1;i<infoList.length;i++) {
			if(i==5) {info = info+"," + infoList[i];}
			else {info = info+"," +"'"+ infoList[i]+"'";}
		}
		info = info + ")";
		return info;
	}

	private static String handleRecordInfo(String recordInfo) {
		String info = null;
		String[] infoList = recordInfo.split(":");
		info = " VALUES(" +"'" + infoList[0]+"'";
		for(int i=1;i<infoList.length;i++) {
			info = info+"," +"'"+ infoList[i]+"'";
		}
		info = info + ")";
		return info;
	}
	
	
	private static ArrayList<String> addPropertyIDToRecord(RentalProperty property, String name) {
		ArrayList<String> records = new ArrayList<>();
		String recordInfo;
		for(int i=0;i<property.getRentalRecord().getRentalRecords().length;i++) {
			if(property.getRentalRecord().getRentalRecords()[i] == null) {
				return records;
			}
			recordInfo = property.getRentalRecord().getRentalRecords()[i]+":"+name;
			records.add(recordInfo);
			
		}
		return records;
	}
}
