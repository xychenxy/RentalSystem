package Controller.ButtonEvent;

import Controller.Main;
import Model.Exception.informationAlert;
import View.mainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class resetBtn implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	public resetBtn(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		for(HBox hBox : mainWindow.imgArrayList) {
			mainWindow.vBox.getChildren().remove(hBox);
		}
		
		mainWindow.createImg(mainWindow.vBox,primaryStage,Main.flexiRentSystem.getPropertyName());
		
		try {
			throw new informationAlert("Reset successfully! \nThere are totally "+ Main.flexiRentSystem.getPropertyName().size()+" properties.");
		} catch (informationAlert e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}

	}

}
