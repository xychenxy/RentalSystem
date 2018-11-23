package Controller.ButtonEvent;

import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class menuNullEffect implements EventHandler<MouseEvent>{

	private MenuBar menuBar;
		
	public menuNullEffect(MenuBar menuBar) {
		this.menuBar = menuBar;
	}
	
	
	// when mouse leave menu bar, menu bar will don't have shadow effect
	
	@Override
	public void handle(MouseEvent event) {
		Object source = event.getSource();
		if(source instanceof MenuBar) {
			menuBar.setEffect(null);
		}
	}

}
