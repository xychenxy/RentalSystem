package Controller;
	

import java.util.ArrayList;

import Model.FlexiRentSystem;
import Model.Exception.informationAlert;
import Model.Sql.ConnectionToDB;
import Model.Sql.SelectQuery;
import View.mainWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	public static Scene mainWindowScene;
	public static FlexiRentSystem flexiRentSystem = new FlexiRentSystem();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			initialConfiguration();
			
			mainWindowScene = new Scene(mainWindow.createMainWindow(primaryStage));
			primaryStage.setScene(mainWindowScene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialConfiguration() throws informationAlert {
		
//		connect to database
//		read the data from database and put them into flexiProperty hashMap; hashMap(propertyID, property Instance)
//		rental_property data will be put into 'flexiProperty'
//		rental_record data will be put into 'rentalRecords' in RentalRecord class
		
		ConnectionToDB.connectToDB();

		Main.flexiRentSystem.getFlexiProperty().clear();
		Main.flexiRentSystem.getPropertyName().clear();
		
		ArrayList<String> rentalProperties = SelectQuery.selectData("RENTAL_PROPERTY");
		if(!rentalProperties.isEmpty()) {
			String[] info = new String[10];
			for(String string:rentalProperties) {
				info = string.split(":");
				flexiRentSystem.readFromDB(info[1], info[2], info[3], info[5], info[4], info[6], info[7], info[8],info[9]);
			}
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
				}
				
			}
		}
		
	}
	
	
}
