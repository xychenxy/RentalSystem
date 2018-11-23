package Controller.ButtonEvent;

import View.propertyDetailWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class getDetailsBtn implements EventHandler<ActionEvent> {
	
	private Stage primaryStage;
	private String id;
	private Button getGetDetailsBtn;
	private String proertyID;
	
	public getDetailsBtn(Stage primaryStage, String id, Button getGetDetailsBtn ,String propertyID) {
		this.primaryStage = primaryStage;
		this.id = id;
		this.getGetDetailsBtn = getGetDetailsBtn;
		this.proertyID = propertyID;
	}

	@Override
	public void handle(ActionEvent event) {
		primaryStage.close();
		Scene propertyDetailScene = new Scene(propertyDetailWindow.createPropertyDetailWiondow(primaryStage , id, proertyID));
		
		primaryStage.setScene(propertyDetailScene);
		primaryStage.show();
		
	}

}
