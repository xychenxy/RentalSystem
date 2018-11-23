package Controller.ButtonEvent;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;

public class disablePassDays implements EventHandler<ActionEvent>{

	private DatePicker dp;
	
	public disablePassDays(DatePicker dp) {
		this.dp = dp;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		dp.setDayCellFactory(picker -> new DateCell() {
			
			@Override
			public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();
	            
	            	setDisable(empty || date.compareTo(today) < 0 );
 
	        }
	    });
		
	}
	
	
}
