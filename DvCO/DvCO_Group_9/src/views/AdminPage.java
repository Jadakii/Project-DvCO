package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.DonutController;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.Donut;
import model.User;

public class AdminPage implements EventHandler<ActionEvent> {

	private List<Donut> donutList = new ArrayList<Donut>();
	private Random rand = new Random();

	private Scene scene;

	private GridPane gp;
	private BorderPane bp;
	private FlowPane fp;

	private Label welcomeMessage, activeDonut, donutName, donutDescription, donutPrice;
	private TextField donutNameTF, donutPriceTF;
	private TextArea donutDescriptionTA;

	private Button addBtn, updateBtn, deleteBtn;
	private MenuBar menuBar;
	private Menu menu;
	private MenuItem logoutMenuItem;
	private TableView<Donut> donutTable;
	private User adminUser;
	private SceneManager sceneManager;

	public void setAdminUser(User adminUser) {
		this.adminUser = adminUser;
		System.out.print("Admin Login: ");
		System.out.println(adminUser.getUsername());
	}

	public void initialize() {

		gp = new GridPane();
		bp = new BorderPane();
		fp = new FlowPane();

		welcomeMessage = new Label("Hello, admin");
		welcomeMessage.setStyle("-fx-font-size:24;-fx-font-weight:bold;");

		activeDonut = new Label("Active Donut: ");
		donutName = new Label("Donut Name");
		donutName.setStyle("-fx-font-weight:bold;");
		donutDescription = new Label("Donut Description");
		donutDescription.setStyle("-fx-font-weight:bold;");
		donutPrice = new Label("Donut Price");
		donutPrice.setStyle("-fx-font-weight:bold;");

		donutNameTF = new TextField();
		donutDescriptionTA = new TextArea();
		donutPriceTF = new TextField();

		donutDescriptionTA.setMaxHeight(2);

		addBtn = new Button("Add Donut");
		updateBtn = new Button("Update Donut");
		deleteBtn = new Button("Delete Donut");

		donutTable = new TableView<Donut>();

		menu = new Menu("Logout");
		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);

		logoutMenuItem = new MenuItem("Logout");
		menu.getItems().addAll(logoutMenuItem);

	}

	public void setStyle() {

		gp.add(donutName, 0, 0);
		gp.add(donutNameTF, 0, 1);
		gp.add(donutDescription, 0, 2);
		gp.add(donutDescriptionTA, 0, 3);
		gp.add(donutPrice, 0, 4);
		gp.add(donutPriceTF, 0, 5);

		gp.setHgap(5);
		gp.setVgap(5);
		gp.setPadding(new Insets(10));

		HBox buttonBox = new HBox(10, addBtn, updateBtn, deleteBtn);
		buttonBox.setAlignment(Pos.BOTTOM_LEFT);

		VBox content = new VBox(10, welcomeMessage, activeDonut, donutTable, gp);
		content.setPadding(new Insets(20));

		bp.setTop(menuBar);
		bp.setCenter(content);
		bp.setBottom(buttonBox);
		scene = new Scene(bp, 1000, 500);

	}

	public void setTable() {

		donutTable.setEditable(false);
		donutTable.setMaxWidth(750);

		TableColumn<Donut, String> idColumn = new TableColumn<>("Donut ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("donutId"));
		idColumn.setPrefWidth(750 / 4);

		TableColumn<Donut, String> nameColumn = new TableColumn<>("Donut Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("donutName"));
		nameColumn.setPrefWidth(750 / 4);

		TableColumn<Donut, String> descriptionColumn = new TableColumn<>("Donut Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("donutDescription"));
		descriptionColumn.setPrefWidth(750 / 4);

//		        TableColumn<Donut, Integer> priceColumn = new TableColumn<>("Donut Price");
//		        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//		        priceColumn.setPrefWidth(250);

		TableColumn<Donut, Integer> priceColumn = new TableColumn<>("Donut Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("donutPrice"));
		priceColumn.setPrefWidth(750 / 4);

		donutTable.getColumns().addAll(idColumn, nameColumn, descriptionColumn, priceColumn);
		

	}
	
	public void initializeDonut() {
		donutTable.setItems(DonutController.getDonutsAdmin());
	
	}

	public void setEventHandler() {
		addBtn.setOnAction(this);
		updateBtn.setOnAction(this);
		deleteBtn.setOnAction(this);
		logoutMenuItem.setOnAction(this);
		
		donutTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
	            donutNameTF.setText(newSelection.getDonutName());
	            donutDescriptionTA.setText(newSelection.getDonutDescription());
	            donutPriceTF.setText(String.valueOf(newSelection.getDonutPrice()));
	        } else {
	            donutNameTF.clear();
	            donutDescriptionTA.clear();
	            donutPriceTF.clear();
	        }
	       
	    });
		
	}

	public AdminPage(Stage stage, SceneManager sceneManager) {
		this.sceneManager = sceneManager;
		initialize();
		setStyle();
		setTable();
		setEventHandler();
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == addBtn) {
			String id = "DN" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			String name = donutNameTF.getText();
			String description = donutDescriptionTA.getText();
			int price =  donutPriceTF.getText() == "" ? 0 : Integer.parseInt(donutPriceTF.getText());
	
			if (name == ""|| description == "" || price == 0) {
				showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
				return;
			}
			Donut newDonut = new Donut(id, name, description, price);
			DonutController.addDonuts(newDonut);
			initializeDonut();
			showAlert(Alert.AlertType.INFORMATION, "Success", "Donut added successfully.");				
			

		} else if (event.getSource() == updateBtn) {
			Donut selectedDonut = donutTable.getSelectionModel().getSelectedItem();
			if (selectedDonut == null) {
				showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a donut to update.");
				return;
			}
			String donutId = selectedDonut.getDonutId();
			String donutName = donutNameTF.getText();
			String description = donutDescriptionTA.getText();
			int price = Integer.parseInt(donutPriceTF.getText());
			
			Donut updatedDonut = new Donut(donutId, donutName, description, price);
			if (donutName.isEmpty() || description.isEmpty() || price == 0) {
				showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled.");
				return;
			}
			DonutController.updateDonuts(updatedDonut);
			initializeDonut();

			showAlert(Alert.AlertType.INFORMATION, "Success", "Donut updated successfully.");

		} else if (event.getSource() == deleteBtn) {
			Donut selectedDonut = donutTable.getSelectionModel().getSelectedItem();
			if (selectedDonut == null) {
				showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a donut to delete.");
				return;
			}
			System.out.println("Selected donut id: ");
			System.out.println(selectedDonut.getDonutId());
			DonutController.deleteDonuts(selectedDonut.getDonutId());
			initializeDonut();
			showAlert(Alert.AlertType.INFORMATION, "Success", "Donut deleted successfully.");
		} else if (event.getSource() == logoutMenuItem) {
			sceneManager.changeScene("Login Page");
		}
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return scene;
	}

}
