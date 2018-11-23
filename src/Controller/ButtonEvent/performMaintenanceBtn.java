package Controller.ButtonEvent;

import Controller.Main;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

public class performMaintenanceBtn implements EventHandler<ActionEvent>{
	private String propertyId;
	private DatePicker maintenance;
	public performMaintenanceBtn(String propertyId, DatePicker maintenance) {
		this.propertyId = propertyId;
		this.maintenance = maintenance;
	}

	@Override
	public void handle(ActionEvent event) {
//		perform maintenance means perform date is today
		
		try {
			Main.flexiRentSystem.getFlexiProperty().get(propertyId).performMaintenance();
		} catch (informationAlert e) {
//			e.printStackTrace();
		}
		
	}


}
