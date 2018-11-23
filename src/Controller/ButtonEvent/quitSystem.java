package Controller.ButtonEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


import Controller.Main;
import Model.Apartment;
import Model.PremiumSuite;
import Model.RentalProperty;
import Model.saveCurrentDataToDB;
import Model.Sql.CreateTable;
import Model.Sql.DropAllTables;
import Model.Sql.InsertRow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class quitSystem implements EventHandler<ActionEvent>{

	private MenuItem quitProgram;
	private Stage primaryStage;
	public quitSystem(MenuItem quitProgram,Stage primaryStage) {
		this.primaryStage =primaryStage;
		this.quitProgram = quitProgram;
	}
	@Override
	public void handle(ActionEvent event) {
//		when quit or export data
//		save the data to database and then close database
		
//		saveToDB();
		
		saveCurrentDataToDB.saveToDB();
		
		primaryStage.close();
		System.exit(0);
		
	}
	
//	private void saveToDB() {
//		DropAllTables.dropAllTables();
//		CreateTable.createAllTables();
//		
//		String propertyInfo = null;
//		Iterator<Entry<String, RentalProperty>> iter = Main.flexiRentSystem.getFlexiProperty().entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry<String, RentalProperty> entry = (Map.Entry<String, RentalProperty>) iter.next();
//			if(entry.getValue() instanceof Apartment) {
//				propertyInfo = ((Apartment) entry.getValue()).getPropertyInfo();
//			}
//			if(entry.getValue() instanceof PremiumSuite) {
//				propertyInfo = ((PremiumSuite) entry.getValue()).getPropertyInfo();
//			} 
//			
//			InsertRow.insertRow("RENTAL_PROPERTY", handlePropertyInfo(propertyInfo));
//			
//			
//			ArrayList<String> propertyRecord = addPropertyIDToRecord(entry.getValue(),entry.getKey());
//			for(String item:propertyRecord) {
//				InsertRow.insertRow("RENTAL_RECORD", handleRecordInfo(item));
//			}		
//		}
//	}
//	
//	private String handlePropertyInfo(String propertyInfo) {
//		String info = null;
//		String[] infoList = propertyInfo.split(":");
//		info = " VALUES(" +"'" + infoList[0]+"'";
//		for(int i=1;i<infoList.length;i++) {
//			if(i==5) {info = info+"," + infoList[i];}
//			else {info = info+"," +"'"+ infoList[i]+"'";}
//		}
//		info = info + ")";
//		return info;
//	}
//
//	private String handleRecordInfo(String recordInfo) {
//		String info = null;
//		String[] infoList = recordInfo.split(":");
//		info = " VALUES(" +"'" + infoList[0]+"'";
//		for(int i=1;i<infoList.length;i++) {
//			info = info+"," +"'"+ infoList[i]+"'";
//		}
//		info = info + ")";
//		return info;
//	}
//	
//	
//	private ArrayList<String> addPropertyIDToRecord(RentalProperty property, String name) {
//		ArrayList<String> records = new ArrayList<>();
//		String recordInfo;
//		for(int i=0;i<property.getRentalRecord().getRentalRecords().length;i++) {
//			if(property.getRentalRecord().getRentalRecords()[i] == null) {
//				return records;
//			}
//			recordInfo = property.getRentalRecord().getRentalRecords()[i]+":"+name;
//			records.add(recordInfo);
//			
//		}
//		return records;
//	}
}
