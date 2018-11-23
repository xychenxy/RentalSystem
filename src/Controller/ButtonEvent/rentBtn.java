package Controller.ButtonEvent;


import java.time.Period;

import Controller.Main;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class rentBtn implements EventHandler<ActionEvent>{
	
	private TextField customerName;
	private DatePicker rentDate;
	private DatePicker returnDate;
	private String propertyID;
	public rentBtn(TextField customerName, DatePicker rentDate, DatePicker returnDate, String id, String propertyID) {
		this.customerName = customerName;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.propertyID = propertyID;
	}

	@Override
	public void handle(ActionEvent event) {
		
		// no need to check propertyID;
		// get the customer id 
		// get rent date
		// get return date
		// check if all data is empty
		// if is apartment, at least 2 or 3 day; return date - rent date >=2 or 3 ; no more than 28 days
		// if is premium suite, at least 1 day, but if cannot over performance day
		
		try {
			isInputDataEmpty(customerName, rentDate, returnDate,propertyID);
		} catch (informationAlert e) {
//			e.printStackTrace();
		}

	}
	
	private void isInputDataEmpty(TextField customerName, DatePicker rentDate, DatePicker returnDate, String propertyID) throws informationAlert {
		if(customerName.getText() == null || rentDate.getValue() == null || returnDate.getValue() == null) {
			throw new informationAlert("Please enter information into each input box or choose date.");
		}
		else if(intervalPeriod(rentDate, returnDate)<1) {
			throw new informationAlert("Return date must be after Rent date");
		}
		else {
			Main.flexiRentSystem.getFlexiProperty().get(propertyID).rentDate(customerName.getText(), rentDate, returnDate);
		}
	}
	
	private int intervalPeriod(DatePicker rentDate, DatePicker returnDate) {
		Period intervalPeriod = Period.between(rentDate.getValue(), returnDate.getValue());
		return intervalPeriod.getDays();
	}
	
	
}
