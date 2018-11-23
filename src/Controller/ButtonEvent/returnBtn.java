package Controller.ButtonEvent;

import Controller.Main;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

public class returnBtn implements EventHandler<ActionEvent>{
	
	private String propertyID;
	private DatePicker returndate;
	
	public returnBtn(String propertyID , DatePicker returndate) {
		this.propertyID = propertyID;
		this.returndate = returndate;
	}

	@Override
	public void handle(ActionEvent event) {
		
//		check if all the input data is empty
//		if no empty, call the return method
		
		try {
			isInputDataEmpty(propertyID, returndate);
		} catch (informationAlert e) {
//			e.printStackTrace();
		}
	}
	
	private void isInputDataEmpty(String propertyID , DatePicker returndate) throws informationAlert {
		if(returndate.getValue() == null) {
			throw new informationAlert("Please enter information into each input box or choose date.");
		}
		else {
			Main.flexiRentSystem.getFlexiProperty().get(propertyID).returnDate(returndate);
		}
	}

}
