package View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import Controller.Main;
import Controller.ButtonEvent.comboBoxBtn;
import Controller.ButtonEvent.exportBtn;
import Controller.ButtonEvent.filterBtn;
import Controller.ButtonEvent.getDetailsBtn;
import Controller.ButtonEvent.gobackToMainWindow;
import Controller.ButtonEvent.importMenuItemBtn;
import Controller.ButtonEvent.menuEffect;
import Controller.ButtonEvent.menuNullEffect;
import Controller.ButtonEvent.quitSystem;
import Controller.ButtonEvent.saveToDBBtn;
import Model.RentalProperty;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainWindow {
	
	private static int vBoxWidth = 900;
	private static int scrollHeight = 580;
	public static VBox vBox;
	public static HashMap<String, Pane> imgHashMap = new HashMap<>();
	public static ArrayList<HBox> imgArrayList = new ArrayList<HBox>();
	
	public static Parent createMainWindow(Stage primaryStage){
		
//		use borderPane to design
//		top is menu bar; left and right is a blank area; central area shows the images and property simple info
//		i write a method for each area
		
		primaryStage.setTitle("FlexiRentSystem");			
		BorderPane pane = new BorderPane();
		pane.setPrefHeight(700);
		 
		createTopMenu(pane,primaryStage,false);
		createRight(pane,200);
		createLeft(pane,200);
		createCentral(pane, primaryStage);
		
		primaryStage.setResizable(false);
		return pane;
		
	}
	
	
	public static void createTopMenu(BorderPane pane, Stage primaryStage, boolean isGoBack) {
		
//		menu method, left method, right method are public because i need to use it in other scene
//		in the mainPage, there are File which include 'import, export, quit' and current time
//		in the property detail page, there are File, current time and go back to mainPage.
//		i add extra function (current time), as i think it is a good way to inform staff of current time, don't waste time to check. 
		
		
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		fileMenu.setStyle("-fx-font-size:18");
		MenuItem importMenuItem = new MenuItem("Import..",new ImageView(new Image("file:images/import1.png")));
		MenuItem exportMenuItem = new MenuItem("Export..",new ImageView(new Image("file:images/export1.png")));
		MenuItem saveToDBMenuItem = new MenuItem("SaveToDB..",new ImageView(new Image("file:images/saveToDB.png",24, 24, true, true)));
		SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
		MenuItem quitProgram = new MenuItem("Quit",new ImageView(new Image("file:images/shutdown2.png", 17, 17, true, true)));
		importMenuItem.setStyle("-fx-font-size:17");
		exportMenuItem.setStyle("-fx-font-size:17");
		saveToDBMenuItem.setStyle("-fx-font-size:17");
		quitProgram.setStyle("-fx-font-size:17");
		

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = simpleDateFormat.format(new Date());
		today = simpleDateFormat.format(new Date());
		Menu currentTime = new Menu("Current Time: " + today);
		currentTime.setStyle("-fx-font-size:18");
		
		fileMenu.getItems().addAll(importMenuItem, exportMenuItem,saveToDBMenuItem,separatorMenuItem,quitProgram);
		if(isGoBack) {
			Menu goBack = new Menu("Go back");
			goBack.setStyle("-fx-font-size:18");
			MenuItem goBackItem = new MenuItem("Go Back To Main Window");
			goBack.getItems().add(goBackItem);
			menuBar.getMenus().addAll(fileMenu,goBack,currentTime);		
			goBackItem.setOnAction(new gobackToMainWindow(goBackItem, primaryStage));	
		}
		else {
			menuBar.getMenus().addAll(fileMenu,currentTime);
		}	
		
		pane.setTop(menuBar);		
		menuBar.setOnMouseEntered(new menuEffect(menuBar));
		menuBar.setOnMouseExited(new menuNullEffect(menuBar));
		importMenuItem.setOnAction(new importMenuItemBtn(primaryStage, importMenuItem));
		exportMenuItem.setOnAction(new exportBtn(primaryStage));
		saveToDBMenuItem.setOnAction(new saveToDBBtn());
		quitProgram.setOnAction(new quitSystem(quitProgram, primaryStage));		
	}
	
	
	public static void createRight(BorderPane pane,int width) {
//		leave a blank area for right hand side
		
		StackPane right = new StackPane();
		right.setPrefWidth(width);
		pane.setRight(right);
	}
	
	public static void createLeft(BorderPane pane,int width) {
//		leave a blank area for left hand side
		
		StackPane left = new StackPane();
		left.setPrefWidth(width);
		pane.setLeft(left);
	}
	
	
	private static void createCentral(BorderPane pane,Stage primaryStage) {
//		there are two sub area in the central area
//		the first area is filter area; i use four comboBox to filter 'type, bedrooms, status, suburb', i use method to create each comboBox
//		one button is filter and search, other button is reset;
//		the second area is the image and its simple details
//		i also use a method call 'createImg()' to show images and information
//		the method 'createImg()' is public, because i need to use it to refresh when some data change or the requirement of filter function
			
		DropShadow shadow = new DropShadow();
		Group sc = new Group();
		VBox centerVBox = new VBox();
		centerVBox.setPrefWidth(vBoxWidth);
	
//		filter area
		Pane filterArea = new Pane();
		filterArea.setPrefSize(vBoxWidth, 60);
		Pane tyoesComboBox = new Pane();
		createComboBox(tyoesComboBox, "type",3);
		Pane bedroomsComboBox = new Pane();
		bedroomsComboBox.setLayoutX(135);
		createComboBox(bedroomsComboBox, "bedrooms",4);
		Pane statusComboBox = new Pane();
		statusComboBox.setLayoutX(270);
		createComboBox(statusComboBox, "status",4);
		Pane suburbComboBox = new Pane();
		suburbComboBox.setLayoutX(405);
		createComboBox(suburbComboBox, "suburb",numberOfSuburb().size()+1);
		Button filterBtn = new Button("Filter and Search");
		filterBtn.setPrefSize(150, 35);
		filterBtn.setLayoutX(560);
		filterBtn.setLayoutY(15);
		Button resetBtn = new Button("Reset");
		resetBtn.setPrefSize(150, 35);
		resetBtn.setLayoutX(725);
		resetBtn.setLayoutY(15);
		resetBtn.setOnAction(new Controller.ButtonEvent.resetBtn(primaryStage));
		filterBtn.setOnAction(new filterBtn(primaryStage));
		
//		image and its details area
		vBox = new VBox();
		vBox.setPrefWidth(vBoxWidth);
		createImg(vBox,primaryStage,Main.flexiRentSystem.getPropertyName());
		ScrollPane s1 = new ScrollPane();
		s1.setPannable(true);
		s1.setPrefHeight(scrollHeight);
		s1.setHbarPolicy(ScrollBarPolicy.NEVER);
		s1.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		s1.setContent(vBox);
		sc.getChildren().addAll(s1);
		sc.setEffect(shadow);
		filterArea.getChildren().addAll(tyoesComboBox,bedroomsComboBox,statusComboBox,suburbComboBox,filterBtn,resetBtn);
		centerVBox.getChildren().addAll(filterArea,sc);
		pane.setCenter(centerVBox);
	}
	
	
	
	private static void createComboBox(Pane combobox, String option, int number) {
//		as for suburb, i will collect suburb name from flexiProperty(hashMap)
//		however, as for others, i use string as i think this way may save a little time
		
		combobox.setPrefWidth(135);
		String[] selections = new String[number];
		if(option.equals("type")) {
			selections[0] = "Type";
			selections[1] = "Apartment";
			selections[2] = "Premium Suite";	
		}else if(option.equals("bedrooms")) {
			selections[0] = "Bedrooms";
			selections[1] = "1 bedroom";
			selections[2] = "2 bedrooms";
			selections[3] = "3 bedrooms";	
		}else if(option.equals("status")) {
			selections[0] = "Status";
			selections[1] = "Available";
			selections[2] = "Rented";	
			selections[3] = "Maintenance";
		}else if(option.equals("suburb")) {
			selections[0] = "Suburb";
			Set<String> tempSuburb = numberOfSuburb();
			Iterator<String> iterator = tempSuburb.iterator();
			int i = 1;
			while (iterator.hasNext()) {
				selections[i] = iterator.next();
				i++;
			}
		}

		ComboBox<String> cbo = new ComboBox<>();
		cbo.getItems().addAll(selections);
		cbo.setValue(selections[0]);
		cbo.setOnAction(new comboBoxBtn(cbo, option));		
		cbo.setPrefWidth(135);
		cbo.setPrefHeight(35);
		cbo.setLayoutY(15);
		combobox.getChildren().add(cbo);
	}
	
	private static Set<String> numberOfSuburb() {
//		using set collection, avoid duplicate value appear; tree set can list by alphabet
		
		Set<String> suburbList = new TreeSet<>();
		Iterator<Entry<String, RentalProperty>> iter = Main.flexiRentSystem.getFlexiProperty().entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, RentalProperty> entry = (Map.Entry<String, RentalProperty>) iter.next();
			suburbList.add(entry.getValue().getSuburb());
		}
		return suburbList;
	}
	
	
	
	
	public static void createImg(VBox vBox, Stage primaryStage, ArrayList<String> propertyName) {
//		get the image name according property instance getImg() method, concat image path name with image name, then show it
//		there are five label to show details which include 'type, status, property id, address, bedroom number'
//		there is a button, click it can redirect to other corresponding page (propertyDetailWindow)
		
//		design: 
//				scrollpane contains a vbox
//				vbox contains many hbox
//				each hbox contains two images and its details
		
		
		HBox hBox = new HBox();
		for(int i=0;i<propertyName.size();i++) {
		
			if(i%2==0) {
				hBox = new HBox();
				vBox.getChildren().addAll(hBox);
			}
				
			Pane imgPane = new Pane();
			imgPane.setPrefSize(vBoxWidth/2, 300);
			
			String apt = Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getImg();
			ImageView imageView2 = new ImageView();
			imageView2.setImage(new Image("file:images/apt/"+apt));
			imageView2.setFitWidth(vBoxWidth/2-20);
			imageView2.setFitHeight(190);
			
			
			String typeString = Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getPropertyType();
			String propertyIdString = Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getPropertyId();
			Pane tPane = new Pane();
			tPane.setLayoutX(10);
			tPane.setLayoutY(200);
			Label type = new Label();
			type.setText(typeString);
			Label propertyID = new Label();
			propertyID.setText(propertyIdString);
			propertyID.setLayoutX(120);
			tPane.getChildren().addAll(type,propertyID);
			
			
			String statusString = Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getStatusString();
			String addressString = Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getCompleteAddress();
			Pane sPane = new Pane();
			sPane.setLayoutX(10);
			sPane.setLayoutY(225);
			Label status = new Label();
			status.setText(statusString);
			Label address = new Label();
			address.setText(addressString);
			address.setLayoutX(120);
			sPane.getChildren().addAll(status,address);
			
			
			String bedroomString = String.valueOf(Main.flexiRentSystem.getFlexiProperty().get(propertyName.get(i)).getNumOfBedRoom());
			Pane bedPane = new Pane();
			bedPane.setLayoutX(10);
			bedPane.setLayoutY(250);
			ImageView bedIcon = new ImageView();
			bedIcon.setImage(new Image("file:images/bed.png"));
			bedIcon.setFitHeight(20);
			bedIcon.setFitWidth(20);
			bedIcon.setLayoutY(4);
			Label bedrooms = new Label();
			bedrooms.setText(bedroomString);
			bedrooms.setLayoutX(33);
			bedrooms.setLayoutY(5);
			Button getDetailsBtn = new Button("Details");
			getDetailsBtn.setLayoutX(120);
			getDetailsBtn.setId(apt);
			bedPane.getChildren().addAll(bedIcon,bedrooms,getDetailsBtn);
			imgPane.getChildren().addAll(imageView2,tPane,sPane,bedPane);
					
			getDetailsBtn.setOnAction(new getDetailsBtn(primaryStage, getDetailsBtn.getId(), getDetailsBtn,propertyName.get(i) ));		
			hBox.getChildren().add(imgPane);		
			imgArrayList.add(hBox);
		}
	}
	
}
