package Controller.ButtonEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.WriteAbortedException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

import Controller.Main;
import Model.Apartment;
import Model.PremiumSuite;
import Model.RentalProperty;
import Model.saveCurrentDataToDB;
import Model.Exception.informationAlert;
import Model.Sql.InsertRow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class exportBtn implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	public exportBtn(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	@Override
	public void handle(ActionEvent event) {

//		save data to DB
//		export data as a txt file format
//		choose a location to save
		
		saveCurrentDataToDB.saveToDB();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file...");
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);
		File file = fileChooser.showSaveDialog(primaryStage);
		
		if(file != null) {
			

			try {
				saveToSpecifyLocation(file.getAbsolutePath());
			} catch (IOException | informationAlert e) {
//				e.printStackTrace();
			}
		}	
	}
	
	private void saveToSpecifyLocation(String absolutePath) throws IOException, informationAlert {
		File dbFile = new File(absolutePath);
		if(!dbFile.exists()) {
			dbFile.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter(dbFile,false);
		BufferedWriter bufferWrite = new BufferedWriter(fileWriter);
		
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
			
			
			bufferWrite.write(propertyInfo + "\n");
			
			ArrayList<String> propertyRecord = addPropertyIDToRecord(entry.getValue(),entry.getKey());
			for(String item:propertyRecord) {
				bufferWrite.write(item + "\n");
			}		
		}
	
		bufferWrite.close();
		fileWriter.close();
		
		throw new informationAlert("Save Successfully!");
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
