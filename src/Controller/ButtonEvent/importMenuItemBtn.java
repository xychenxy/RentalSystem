package Controller.ButtonEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Controller.Main;
import Model.Exception.informationAlert;
import Model.Sql.CreateTable;
import Model.Sql.DropAllTables;
import Model.Sql.InsertRow;
import Model.Sql.SelectQuery;
import View.mainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class importMenuItemBtn implements EventHandler<ActionEvent>{
	
	private Stage primaryStage;
	private MenuItem button;
	
	public importMenuItemBtn(Stage primaryStage, MenuItem button) {
		this.primaryStage =primaryStage;
		this.button =button;
	}

	@Override
	public void handle(ActionEvent event) {

//		show open file dialog and choose txt file
//		and then write data into DB
//		if success, read data from DB, update content, such as show new images
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);
		File file = fileChooser.showOpenDialog(primaryStage);
		if(file != null) {
			System.out.println(file.getAbsolutePath());
			try {
				writeToDatabase(file.getAbsolutePath());
			} catch (informationAlert e1) {
//				e1.printStackTrace();
			}
		}
	}
	
	private void writeToDatabase(String absolutePath) throws informationAlert{
		File file = new File(absolutePath);
		boolean isImportSuccess = false;
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			String string;
			DropAllTables.dropAllTables();
			CreateTable.createAllTables();
			while((string = bReader.readLine()) != null) {
								
				isImportSuccess = InsertRow.insertRow(handleImportDATA(string)[0], handleImportDATA(string)[1]);
				if(!isImportSuccess) {
					throw new informationAlert("is import / The data Format is wrong.  Import unsuccessful！");
				}
			}
			if(isImportSuccess) {
				updateImgContent();
				throw new informationAlert("Import data to database successfully!");	
			}
		} catch (IOException e) {
			throw new informationAlert("Catch/ The data Format is wrong.  Import unsuccessful！");
		}
	}

	
	private String[] handleImportDATA(String string) {
//		this method will be called by writeToDatabase();
//		change data format which read from txt file into hsqldb format
		
		String returnString = null;
		String[] returnStringList = new String[2];
		String[] importDataList = string.split(":");
		ArrayList<String> importArrayList = new ArrayList<>();
		if(importDataList.length ==10) {
			for(String item:importDataList) importArrayList.add(item);
			returnString = " VALUES(" +"'" + importArrayList.get(0)+"'";
			for(int i=1; i<importArrayList.size();i++) {
				if(i==5) {returnString = returnString+"," + importArrayList.get(i);}
				else {returnString = returnString+"," +"'"+ importArrayList.get(i)+"'";}
			}	
			returnString = returnString + ")";
			returnStringList[0] = "RENTAL_PROPERTY";
			returnStringList[1] = returnString;
		}
		else if(importDataList.length==7) {
			for(String item:importDataList) importArrayList.add(item);
			returnString = " VALUES(" + "'"+importArrayList.get(0)+"'";
			for(int i=1; i<importArrayList.size();i++) returnString = returnString+","+"'" + importArrayList.get(i)+"'";
			returnString = returnString + ")";
			returnStringList[0] = "RENTAL_RECORD";
			returnStringList[1] = returnString;
		}

		return returnStringList;
	}
	
	
	private void updateImgContent() throws informationAlert {
		
//		vbox contain many hbox;
//		clear each hbox, as each hbox contain two images
//		after read data from DB and write to hashMap, show the images and relevant details again
		
		for(HBox hBox : mainWindow.imgArrayList) {
			mainWindow.vBox.getChildren().remove(hBox);
		}
		
		
		ArrayList<String> rentalProperties = SelectQuery.selectData("RENTAL_PROPERTY");
		if(!rentalProperties.isEmpty()) {
			String[] info = new String[10];
			for(String string:rentalProperties) {
				info = string.split(":");
				Main.flexiRentSystem.readFromDB(info[1], info[2], info[3], info[5], info[4], info[6], info[7], info[8],info[9]);
			}
			mainWindow.createImg(mainWindow.vBox, primaryStage, Main.flexiRentSystem.getPropertyName());
		}
		
		ArrayList<String> rentalRecords = SelectQuery.selectData("RENTAL_RECORD");
		if(!rentalRecords.isEmpty()) {
			String[] records = new String[7];
			for(String string:rentalRecords) {
				records = string.split(":");
				String tempRecords = records[0]+":"+records[1]+":"+records[2]+":"+records[3]+":"+records[4]+":"+records[5];
				for(int i=0;i<Main.flexiRentSystem.getFlexiProperty().get(records[6]).getRentalRecord().getRentalRecords().length;i++) {
					
					if(Main.flexiRentSystem.getFlexiProperty().get(records[6]).getRentalRecord().getRentalRecords()[i] ==null) {
						Main.flexiRentSystem.getFlexiProperty().get(records[6]).getRentalRecord().getRentalRecords()[i] = tempRecords;
						break;
					}
					if(Main.flexiRentSystem.getFlexiProperty().get(records[6]).getRentalRecord().getRentalRecords()[i].equals(tempRecords)) {
						break;
					}
				}
			}
		}
	}
	
}
