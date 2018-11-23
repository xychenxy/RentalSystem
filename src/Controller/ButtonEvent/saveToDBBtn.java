package Controller.ButtonEvent;

import Model.saveCurrentDataToDB;
import Model.Exception.informationAlert;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class saveToDBBtn implements EventHandler<ActionEvent> {

	public saveToDBBtn() {
		
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		saveCurrentDataToDB.saveToDB();
		
		try {
			throw new informationAlert("Save to Database Successfully!");
		} catch (informationAlert e) {
//			e.printStackTrace();
		}
	}

}
