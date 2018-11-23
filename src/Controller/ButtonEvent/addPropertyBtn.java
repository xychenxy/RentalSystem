
package Controller.ButtonEvent;


import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import Controller.Main;
import Model.Apartment;
import Model.DateTime;
import Model.PremiumSuite;
import Model.RentalProperty;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;

public class addPropertyBtn implements EventHandler<ActionEvent> {
	private Button addProperty = new Button();
	private TextField streetNumberText = new TextField();
	private TextField streetNameText = new TextField();
	private TextField suburbText = new TextField();
	private TextField numberOfBedroomsText = new TextField();
	private TextField propertyTypeText = new TextField();
	private DatePicker lastMainteanceText = new DatePicker();
	private TextArea descriptionText = new TextArea(); 
	
	public addPropertyBtn(Button addProperty,TextField streetNumberText,TextField streetNameText,TextField suburbText,
			TextField numberOfBedroomsText, TextField propertyTypeText,DatePicker lastMainteanceText, TextArea descriptionText)  {
		this.addProperty = addProperty;
		this.streetNumberText = streetNumberText;
		this.suburbText = suburbText;
		this.numberOfBedroomsText = numberOfBedroomsText;
		this.streetNameText = streetNameText;
		this.propertyTypeText = propertyTypeText;
		this.lastMainteanceText = lastMainteanceText;
		this.descriptionText = descriptionText;
	}

	@Override
	public void handle(ActionEvent event){
		
//		check if all the data that user has input
		
		
		try {
			isInputDataEmpty(streetNumberText, streetNameText, suburbText, numberOfBedroomsText, propertyTypeText, lastMainteanceText);
		} catch (informationAlert e) {
//			e.printStackTrace();
		}
		
			

	}
	
	
	private void isInputDataEmpty(TextField streetNumberText,TextField streetNameText,TextField suburbText,
			TextField numberOfBedroomsText, TextField propertyTypeText,DatePicker lastMainteanceText) throws informationAlert {
		
//		check if all the data is empty
//		if no, check the illegal of street number, name, suburb
//		if no, check the illegal of apartment and its bedrooms number
//		if no, check the illegal of premium suite and its bedrooms number
		
		
		if(streetNameText.getText() == null || streetNumberText.getText() == null || suburbText.getText() == null || numberOfBedroomsText.getText() == null ||
				propertyTypeText.getText() == null || lastMainteanceText.getValue() == null || descriptionText.getText() == null) {
			throw new informationAlert("Please enter information into each input box or choose date.");
		}
		else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String selectDate = formatter.format(lastMainteanceText.getValue());
			
			if(streetNumberText.getText().length() >10 || streetNameText.getText().length() >20 || suburbText.getText().length()>20) {
				throw new informationAlert("Street Number or Street Name or Suburb is too long, please enter legal address.");
			}
			else if(checkNumbderFormat(numberOfBedroomsText.getText()) >=1 && checkNumbderFormat(numberOfBedroomsText.getText()) <=3 && propertyTypeText.getText().equals("Apartment")) {
				addProperty(streetNumberText.getText(), streetNameText.getText(), suburbText.getText(), 
							numberOfBedroomsText.getText(), propertyTypeText.getText(),"Available", "null","No Image Available",descriptionText.getText());
			}
			else if(checkNumbderFormat(numberOfBedroomsText.getText()) == 3 && propertyTypeText.getText().equals("Premium Suite") && isSelectDate(lastMainteanceText.getValue())) {
				addProperty(streetNumberText.getText(), streetNameText.getText(), suburbText.getText(), 
							numberOfBedroomsText.getText(), propertyTypeText.getText(), "Available",selectDate,"No Image Available",descriptionText.getText());
			}
			else {
				throw new informationAlert("Property Type with its bedrooms number is not match.\n"
						+ "The bedrooms number of Apartment is range from 1 to 3.\n"
						+ "The bedrooms number of Premium Suite is only 3.\n"
						+ "Please notice that Apartment and Premium Suite is case sensitive.");
			}
		}
	}
	
	private void addProperty(String streetNumberText,String streetNameText,String suburbText, String numberOfBedroomsText,
			String propertyTypeText,String status,String lastMainteanceText,String img, String deString) throws informationAlert{	
		
		// 1. check all the input data are right
		// 2. check this property is already exit
		// 3. create a property
		// 4. save this property
			
		String propertyId = Main.flexiRentSystem.createPropertyId(streetNumberText,streetNameText,numberOfBedroomsText,propertyTypeText);
		
		if(propertyTypeText.equals("Apartment")) {
			Apartment A = new Apartment(propertyId, streetNameText, streetNumberText, suburbText, Integer.valueOf(numberOfBedroomsText), 
					status,propertyTypeText,img,deString);
			storeProperty(A,propertyId);
		}
		
		if (propertyTypeText.equals("Premium Suite")) {
			PremiumSuite P = new PremiumSuite(propertyId, streetNameText, streetNumberText, suburbText,Integer.valueOf(numberOfBedroomsText),
					status,propertyTypeText,Main.flexiRentSystem.changeToDateTime(lastMainteanceText),img,deString);
			storeProperty(P,propertyId);
		}	
		
	}
	
	private void storeProperty(RentalProperty property, String propertyId) throws informationAlert{
//		this method is used to store property into hashMap
//		check if there are 50 properties
//		if no, check if empty, if empty, put it into hashMap
//		if no, check if repeat, if no repeat, put it into hashMap
		
		if(Main.flexiRentSystem.getFlexiProperty().size()>=50) {
			throw new informationAlert("There have already 50 properties, please do not add anymore. Add Property unsuccussfully!");
		}
		else if(Main.flexiRentSystem.getFlexiProperty().isEmpty()) {
			Main.flexiRentSystem.getFlexiProperty().put(propertyId, property);
			Main.flexiRentSystem.getPropertyName().add(propertyId);
			throw new informationAlert(propertyId + " has been added succussfully!\n"+property);
		}
		else if(Main.flexiRentSystem.getFlexiProperty().containsKey(propertyId)) {
			throw new informationAlert("This property is exist. Add Property unsuccussfully!");
		}
		else {
			Main.flexiRentSystem.getFlexiProperty().put(propertyId, property);
			Main.flexiRentSystem.getPropertyName().add(propertyId);
			throw new informationAlert(propertyId + " has been added succussfully!\n"+property);
		}
	}
	
	
	private int checkNumbderFormat(String numberOfBedroomsText) throws informationAlert  {
		int bedrooms = -1;
		
		try {
			bedrooms = Integer.parseInt(numberOfBedroomsText);
		} catch (NumberFormatException e) {
			throw new informationAlert("The number format is wrong.");
		}
		
		return bedrooms;
	}
	
	
	
	

	private boolean isSelectDate(LocalDate lastMainteanceText) {
//		this method is the same with below method; computerDiffDays()
		
		boolean check = false;
		LocalDate tenDayAgoDate = LocalDate.now().minusDays(10); 
		LocalDate today = LocalDate.now();
		if(lastMainteanceText.compareTo(tenDayAgoDate) >= 0 && today.compareTo(lastMainteanceText) >=0) {
			check = true;
		}
		return check;	
	}
	
	private boolean computerDiffDays(DatePicker datePicker) {
//		mainteanceDate - days
//		if result > 0; no 
//		if result < -10; no
		
		boolean check = true;
		LocalDate today = LocalDate.now();
		LocalDate mainteanceDate = datePicker.getValue();
		long days = today.until(mainteanceDate, ChronoUnit.DAYS);
		
		if(days > 0) {
			check = false;
		}
		if(days < -10) {
			check = false;
		}
		
		return check;
	}
}
