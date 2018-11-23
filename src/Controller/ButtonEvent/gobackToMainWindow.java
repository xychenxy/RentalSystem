package Controller.ButtonEvent;

import Controller.Main;
import View.mainWindow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class gobackToMainWindow implements EventHandler<ActionEvent> {

	private MenuItem menu;
	private Stage primaryStage;
	public gobackToMainWindow(MenuItem menu,Stage primaryStage) {
		this.menu = menu;
		this.primaryStage = primaryStage;
	}

	@Override
	public void handle(ActionEvent event) {
		primaryStage.close();
		primaryStage.setScene(Controller.Main.mainWindowScene);
		primaryStage.show();
		
		
		for(HBox hBox : mainWindow.imgArrayList) {
			mainWindow.vBox.getChildren().remove(hBox);
		}
		
		mainWindow.createImg(mainWindow.vBox, primaryStage, Main.flexiRentSystem.getPropertyName());
		
	}

}
