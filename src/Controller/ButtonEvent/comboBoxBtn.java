package Controller.ButtonEvent;

import Model.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

public class comboBoxBtn implements EventHandler<ActionEvent>{

	private ComboBox<String> comboBox;
	private String option;
	public comboBoxBtn(ComboBox<String> comboBox,String option) {
		this.comboBox = comboBox;
		this.option = option;
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(option.equals("type")) {
			Message.setType(comboBox.getValue());
		}else if(option.equals("bedrooms")) {
			Message.setBedrooms(comboBox.getValue());
		}else if(option.equals("status")) {
			Message.setStatus(comboBox.getValue());
		}else if(option.equals("suburb")) {
			Message.setSuburb(comboBox.getValue());	
		}
	}

}
