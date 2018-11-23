package Controller.ButtonEvent;

import Controller.Main;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

public class completePropertyBtn implements EventHandler<ActionEvent>{
	
	private String propertyId;
	private DatePicker complete;
	public completePropertyBtn(String propertyId, DatePicker complete) {
		this.propertyId = propertyId;
		this.complete = complete;
	}

	@Override
	public void handle(ActionEvent event) {
//		check if all input data is empty
//		if no, call complete method
		
		try {
			isInputDataEmpty(propertyId, complete);
		} catch (informationAlert e) {
//			e.printStackTrace();
		}
		
	}
	
	
	private void isInputDataEmpty(String propertyId, DatePicker complete) throws informationAlert {
		if(complete.getValue() == null) {
			throw new informationAlert("Please enter information into each input box or choose date.");
		}
		else {
			Main.flexiRentSystem.getFlexiProperty().get(propertyId).completeMaintenance(complete);
		}
	}

}
