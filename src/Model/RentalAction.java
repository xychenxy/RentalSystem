package Model;

import Model.Exception.informationAlert;
import javafx.scene.control.DatePicker;

public interface RentalAction {

	
	public abstract void rentDate(String customer, DatePicker rentdate, DatePicker retrundate) throws informationAlert;
	
	public abstract void returnDate(DatePicker actuRe) throws informationAlert; 
	
	public abstract void performMaintenance() throws informationAlert; 
	
	public abstract void completeMaintenance(DatePicker completionDate) throws informationAlert;
	
}
