package Controller.ButtonEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import Controller.Main;
import Model.Apartment;
import Model.Message;
import Model.PremiumSuite;
import Model.RentalProperty;
import Model.Exception.informationAlert;
import Model.Sql.InsertRow;
import View.mainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class filterBtn implements EventHandler<ActionEvent>{
	
	private Stage primaryStage;
	private ArrayList<RentalProperty> fiList = new ArrayList<>();
	private ArrayList<String> fStrings = new ArrayList<>();
	public filterBtn(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void handle(ActionEvent event) {
		// vbox contain hbox; hbox contain img and details
		// 1. remove hbox also mean remove img
		// 2. according to filter requirement, find the property object
		// 3. show the property object
		
		for(HBox hBox : mainWindow.imgArrayList) {
			mainWindow.vBox.getChildren().remove(hBox);
		}
		
		fiList.clear();
		fStrings.clear();
		
		Iterator<Entry<String, RentalProperty>> iter = Main.flexiRentSystem.getFlexiProperty().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, RentalProperty> entry = (Map.Entry<String, RentalProperty>) iter.next();
			fiList.add(entry.getValue());					
		}
		
		typeFilter();
		bedroomFilter();
		statusFilter();
		suburbFilter();
		
		
		if(fiList.isEmpty()) {
			try {
				throw new informationAlert("There is not property that matching your filter.");
			} catch (informationAlert e) {
//				e.printStackTrace();
			}
		}
		else {
			for(RentalProperty rentalProperty:fiList) {
				fStrings.add(rentalProperty.getPropertyId());
			}
			mainWindow.createImg(mainWindow.vBox, primaryStage, fStrings);
			try {
				throw new informationAlert("Filter has completed.\nThere are totally "+fStrings.size()+" properties.");
			} catch (informationAlert e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		
			
	}
	

	
	
	
	private void typeFilter(){
		if(!Message.getType().equals("Type")){
			String tempType = Message.getType();
			
			Iterator<RentalProperty> iterator = fiList.iterator();
			while (iterator.hasNext()) {
				RentalProperty rProperty = (RentalProperty) iterator.next();
				if(!rProperty.getPropertyType().equals(tempType)) {
					iterator.remove();
				}
			}
			
		}
	}
	
	private void bedroomFilter() {
		if(!Message.getBedrooms().equals("Bedrooms")){
			int tempBedroom = Integer.valueOf(Message.getBedrooms().split(" ")[0]);
			
			Iterator<RentalProperty> iterator = fiList.iterator();
			while (iterator.hasNext()) {
				RentalProperty rProperty = (RentalProperty) iterator.next();
				if(rProperty.getNumOfBedRoom() != tempBedroom) {
					iterator.remove();
				}
			}
		}
	}
	
	private void statusFilter() {
		
		if(!Message.getStatus().equals("Status")) {
			String tempStatus = Message.getStatus();
			
			Iterator<RentalProperty> iterator = fiList.iterator();
			while (iterator.hasNext()) {
				RentalProperty rProperty = (RentalProperty) iterator.next();
				if(!rProperty.getStatusString().equals(tempStatus)) {
					iterator.remove();
				}
			}
		}
	}

	private void suburbFilter() {
		if(!Message.getSuburb().equals("Suburb")) {
			String tempSuburb = Message.getSuburb();
			
			Iterator<RentalProperty> iterator = fiList.iterator();
			while (iterator.hasNext()) {
				RentalProperty rProperty = (RentalProperty) iterator.next();
				if(!rProperty.getSuburb().equals(tempSuburb)) {
					iterator.remove();
				}
			}
		}
	}
	
	
}
