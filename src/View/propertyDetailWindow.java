package View;

import java.time.LocalDate;

import javax.swing.text.TabExpander;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Controller.Main;
import Controller.ButtonEvent.addPropertyBtn;
import Controller.ButtonEvent.completePropertyBtn;
import Controller.ButtonEvent.disablePassDays;
import Controller.ButtonEvent.performMaintenanceBtn;
import Controller.ButtonEvent.rentBtn;
import Controller.ButtonEvent.returnBtn;
import Model.Apartment;
import Model.PremiumSuite;

public class propertyDetailWindow {
	private static int centralWidth = 700;
	private static Shadow shadow = new Shadow();
	public static Button rent;

	public static Parent createPropertyDetailWiondow(Stage primaryStage, String id, String propertyID) {
		primaryStage.setTitle("Property Details");
		
		BorderPane pane = new BorderPane();
		pane.setPrefWidth(1300);
		pane.setPrefHeight(700);
		
//		ImageView imageView = new ImageView();
//		imageView.setImage(new Image("file:images/background.jpg"));
//		imageView.setFitWidth(1300);
//		imageView.setFitHeight(1000);
//		
//		pane.getChildren().add(imageView);
		
		mainWindow.createTopMenu(pane, primaryStage,true);
		
		mainWindow.createLeft(pane, 135);
		
		mainWindow.createRight(pane,135);
				
		createCentral(pane,id, propertyID);
		
		return pane;
		
		
	}
	
	private static void createCentral(BorderPane pane,String id, String propertyID) {
		Pane centralArea = new Pane();
		centralArea.setPrefWidth(centralWidth);
//		left centralArea
		Pane leftCentral = new Pane();
		leftCentral.setPrefWidth(600);
		createLeftCentral(leftCentral, id, propertyID);
//		right centralArea
		Pane rightCentral = new Pane();
		rightCentral.setPrefWidth(380);
		rightCentral.setLayoutX(630);
		createRightCentral(rightCentral, propertyID);
		
		centralArea.getChildren().addAll(leftCentral,rightCentral);
		pane.setCenter(centralArea);
	}
	
	private static void createLeftCentral(Pane pane, String id, String propertyID) {
		Pane imgPane = new Pane();
		imgPane.setPrefSize(600, 260);
		ImageView imageView2 = new ImageView();
		imageView2.setImage(new Image("file:images/apt/"+id));
		imageView2.setFitWidth(600);
		imageView2.setFitHeight(200);
		imageView2.setLayoutY(30);
		imgPane.getChildren().add(imageView2);
		
		
		TabPane operationArea = new TabPane();
		operationArea.setLayoutY(260);
		operationArea.setPrefSize(600, 550);
		operationArea.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		Tab addTab = new Tab("Add Property");
		addTab.setContent(createaddTab());
		
		Tab rentalTab = new Tab("Rent Property");
		rentalTab.setContent(createRentalTab(id, propertyID));
		
		
		Tab returnTab = new Tab("Return Property");
		returnTab.setContent(createReturnTab(id, propertyID));
		
		Tab mainteanceTab = new Tab("Mainteance  ");
		mainteanceTab.setContent(createMainteanceTab( id,  propertyID));
		
		Tab completeTab = new Tab("Complete Mainteance");
		completeTab.setContent(createCompleteTab( id,  propertyID));
		
		operationArea.getTabs().addAll(rentalTab,returnTab,mainteanceTab,completeTab,addTab);
		
		pane.getChildren().addAll(imgPane,operationArea);
	}
	
	private static Pane createaddTab() {
		Pane addPane = new Pane();
		
		GridPane rentalGridPane = new GridPane();	
		rentalGridPane.setLayoutX(50);
		rentalGridPane.setLayoutY(10);
		rentalGridPane.setPadding(new Insets(10));
		rentalGridPane.setHgap(10);
		rentalGridPane.setVgap(10);
		
		Text streetNumber = new Text("Street Number:");
		TextField streetNumberText = new TextField();
		Text streetName = new Text("Street Name:");
		TextField streetNameText = new TextField();
		Text suburb = new Text("Suburb Name:");
		TextField suburbText = new TextField();
		Text numberOfBedrooms = new Text("Bedrooms Number:");
		TextField numberOfBedroomsText = new TextField();
		Text propertyType = new Text("Property Type:");
		TextField propertyTypeText = new TextField("Apartment or Premium Suite");
		Text lastMainteance = new Text("Last Mainteance Date:");
		DatePicker lastMainteanceText = new DatePicker();
		Text description = new Text("Description");
		TextArea descriptionText = new TextArea();
		descriptionText.setWrapText(true);
		descriptionText.setPrefSize(150, 100);
		Button addProperty = new Button("Add Property");
		addProperty.setPrefWidth(180);
		
		rentalGridPane.add(streetNumber, 0, 0);
		rentalGridPane.add(streetNumberText, 1, 0);
		rentalGridPane.add(streetName, 0, 1);
		rentalGridPane.add(streetNameText, 1, 1);
		rentalGridPane.add(suburb, 0, 2);
		rentalGridPane.add(suburbText, 1, 2);
		rentalGridPane.add(numberOfBedrooms, 0, 3);
		rentalGridPane.add(numberOfBedroomsText, 1, 3);
		
		rentalGridPane.add(propertyType, 0, 4);
		rentalGridPane.add(propertyTypeText, 1, 4);
		rentalGridPane.add(lastMainteance, 0, 5);
		rentalGridPane.add(lastMainteanceText, 1, 5);
		rentalGridPane.add(description, 0, 6);
		rentalGridPane.add(descriptionText, 1, 6);
		rentalGridPane.add(addProperty, 2, 6);
		addPane.getChildren().add(rentalGridPane);
		
		addProperty.setOnAction(new addPropertyBtn(addProperty, streetNumberText, streetNameText, suburbText, numberOfBedroomsText, propertyTypeText, lastMainteanceText,descriptionText));
		
		return addPane;
	}
	
	private static Pane createRentalTab(String id, String propertyID) {
		Pane rentalPane = new Pane();
		
		GridPane rentalGridPane = new GridPane();	
		rentalGridPane.setLayoutX(50);
		rentalGridPane.setLayoutY(30);
		rentalGridPane.setPadding(new Insets(10));
		rentalGridPane.setHgap(10);
		rentalGridPane.setVgap(10);
		Text propertyId = new Text(propertyID);
		Text customerId = new Text("Customer Name:");
		TextField customerIdText = new TextField();
		Text rentDate = new Text("Rent Date:");
		DatePicker rentDateText = new DatePicker();
		Text howManyDays = new Text("Return Date");
		DatePicker howManyDaysText = new DatePicker();
		Button rent = new Button("RENT");
		rent.setPrefWidth(200);
		
		rentalGridPane.add(propertyId, 0, 0);
		rentalGridPane.add(customerId, 0, 1);
		rentalGridPane.add(customerIdText, 1, 1);
		rentalGridPane.add(rentDate, 0, 2);
		rentalGridPane.add(rentDateText, 1, 2);
		rentalGridPane.add(howManyDays, 0, 3);
		rentalGridPane.add(howManyDaysText, 1, 3);
		rentalGridPane.add(rent, 2, 3);
		rentalPane.getChildren().add(rentalGridPane);
		
		
		disablePassDays(rentDateText);
		disablePassDays(howManyDaysText);
		
		rent.setOnAction(new rentBtn(customerIdText, rentDateText, howManyDaysText, id, propertyID));
		
		
		
		return rentalPane;
	}
	
	private static Pane createReturnTab(String id, String propertyID) {
		Pane returnPane = new Pane();
		GridPane returnGridPane = new GridPane();
		returnGridPane.setLayoutX(50);
		returnGridPane.setLayoutY(30);
		returnGridPane.setPadding(new Insets(10));
		returnGridPane.setHgap(10);
		returnGridPane.setVgap(10);
		Text propertyId = new Text(propertyID);
		Text returnDate = new Text("Return Date:");
		DatePicker returnDateText = new DatePicker();
		Button returnProperty = new Button("RETURN");
		returnProperty.setPrefWidth(200);
		returnGridPane.add(propertyId, 0, 0);
		returnGridPane.add(returnDate, 0, 1);
		returnGridPane.add(returnDateText, 1, 1);
		returnGridPane.add(returnProperty, 2, 1);
		returnPane.getChildren().add(returnGridPane);
		
		disablePassDays(returnDateText);
		
		returnProperty.setOnAction(new returnBtn(propertyID, returnDateText));
		
		return returnPane;
	}
	
	private static Pane createMainteanceTab(String id, String propertyID) {
		Pane maitenancePane = new Pane();
		GridPane maitenanceGridPane = new GridPane();
		maitenanceGridPane.setLayoutX(50);
		maitenanceGridPane.setLayoutY(30);
		maitenanceGridPane.setPadding(new Insets(10));
		maitenanceGridPane.setHgap(10);
		maitenanceGridPane.setVgap(10);
		Text propertyId = new Text(propertyID);
		Text maintenanceDate = new Text("Mainteance Date:");
		DatePicker maintenanceDateText = new DatePicker();
		Button maintenance = new Button("MAINTENANCE");
		maintenance.setPrefWidth(200);
		maitenanceGridPane.add(propertyId, 0, 0);
		maitenanceGridPane.add(maintenanceDate, 0, 1);
		maitenanceGridPane.add(maintenanceDateText, 1, 1);
		maitenanceGridPane.add(maintenance, 2, 1);
		maitenancePane.getChildren().add(maitenanceGridPane);
		
		disablePassDays(maintenanceDateText);
		maintenance.setOnAction(new performMaintenanceBtn(propertyID, maintenanceDateText));
		
		return maitenancePane;
	}
	
	private static Pane createCompleteTab(String id, String propertyID) {
		Pane completePane = new Pane();
		GridPane completeGridPane = new GridPane();
		completeGridPane.setLayoutX(50);
		completeGridPane.setLayoutY(30);
		completeGridPane.setPadding(new Insets(10));
		completeGridPane.setHgap(10);
		completeGridPane.setVgap(10);
		Text propertyId = new Text(propertyID);
		Text completeDate = new Text("Return Date:");
		DatePicker completeDateText = new DatePicker();
		Button completeMaintenance = new Button("COMPLETE MAINTENANCE");
		completeGridPane.add(propertyId, 0, 0);
		completeGridPane.add(completeDate, 0, 1);
		completeGridPane.add(completeDateText, 1, 1);
		completeGridPane.add(completeMaintenance, 2, 1);
		completePane.getChildren().add(completeGridPane);
		
		disablePassDays(completeDateText);
		
		completeMaintenance.setOnAction(new completePropertyBtn(propertyID, completeDateText));
		
		return completePane;
	}
	
	
	private static void createRightCentral(Pane pane, String propertyID) {
		
		String description = Main.flexiRentSystem.getFlexiProperty().get(propertyID).getDescription();
		String rentalRecordDetails = null;
		Apartment aReocrds;
		PremiumSuite pRecords;
		
		if(Main.flexiRentSystem.getFlexiProperty().get(propertyID) instanceof Apartment) {
			aReocrds = (Apartment)(Main.flexiRentSystem.getFlexiProperty().get(propertyID));
			rentalRecordDetails = aReocrds.getDetails();
		}
		
		if(Main.flexiRentSystem.getFlexiProperty().get(propertyID) instanceof PremiumSuite) {
			pRecords = (PremiumSuite)(Main.flexiRentSystem.getFlexiProperty().get(propertyID));
			rentalRecordDetails = pRecords.getDetails();
		}
		
		
		Pane descriptionPane = new Pane();
		descriptionPane.setPrefSize(400, 410);
		descriptionPane.setLayoutY(30);
		Label descriptionLabel = new Label();
//		descriptionLabel.setStyle("-fx-font-family: monospace");
		descriptionLabel.setPrefSize(400, 410);
		descriptionLabel.setText(description);
		descriptionLabel.setWrapText(true);
		descriptionLabel.setAlignment(Pos.TOP_LEFT);
		descriptionPane.getChildren().add(descriptionLabel);
		
		
		
		Pane rentalRecordGroup = new Pane();
		rentalRecordGroup.setLayoutY(410);
		
		Label rentalRecordLabel = new Label();
		rentalRecordLabel.setPrefWidth(400);
		
		rentalRecordLabel.setWrapText(true);
		rentalRecordLabel.setStyle("-fx-font-family: monospace");
		rentalRecordLabel.setText(rentalRecordDetails);
		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefHeight(200);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scrollPane.setContent(rentalRecordLabel);
		rentalRecordGroup.getChildren().add(scrollPane);
		
		pane.getChildren().addAll(descriptionPane,rentalRecordGroup);
		
	}
	
	private static void disablePassDays(DatePicker dp) {
		
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
