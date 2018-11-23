package Controller.ButtonEvent;

import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class menuEffect implements EventHandler<MouseEvent>{

	private MenuBar menuBar;
	private DropShadow shadow = new DropShadow();
	
	
	public menuEffect(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	// when mouse enter menuBar, menuBar will have a shadow effect
	
	@Override
	public void handle(MouseEvent event) {
		Object source = event.getSource();
		if(source instanceof MenuBar) {
			menuBar.setEffect(shadow);
		}
	}

}
