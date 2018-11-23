package Model.Exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class informationAlert extends Exception{
	private String errMsg;
	
	public informationAlert(String errMsg) {
//		super(errMsg);

		showAlertWithHeaderText("flexiPropertyException", errMsg);
		
	}
	
	
	public static void showAlertWithHeaderText(String title,String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Results:");
        alert.setContentText(msg);
 
        alert.showAndWait();
    }
}
