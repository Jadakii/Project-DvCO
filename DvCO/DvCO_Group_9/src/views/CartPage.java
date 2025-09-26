package views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import controller.CartController;
import controller.TransactionController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.SceneManager;
import model.Item;
import model.Transaction;
import model.User;
import javafx.scene.layout.VBox;

public class CartPage {
	private Stage stage;
	private Scene scene;
	private BorderPane borderContainer;
	private GridPane gridContainer;

	private Label yourCart, totalPrice;
	private TableView<Item> tableItem = new TableView<>();
	private Button checkoutBtn;
	private MenuBar navigationBar;
	private Menu dashboardMenu, navigationMenu;
	private MenuItem homeScene, cartScene, logout;
	private List<Transaction> transactions = new ArrayList<>();
	private int transactionIndex;
	private ObservableList<Item> cartItems = FXCollections.observableArrayList();
	private Random random = new Random();
	private User userData;
	
	public void initialize() {
		borderContainer = new BorderPane();
		gridContainer = new GridPane();

		yourCart = new Label("Your Cart");
		yourCart.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		totalPrice = new Label("Rp. 0");
		totalPrice.setStyle("-fx-font-weight: bold;");
		checkoutBtn = new Button("Checkout");

		navigationBar = new MenuBar();
		dashboardMenu = new Menu("Dashboard");
		homeScene = new MenuItem("Home");
		cartScene = new MenuItem("Cart");
		dashboardMenu.getItems().addAll(homeScene, cartScene);

		navigationMenu = new Menu("Logout");
		logout = new MenuItem("Logout");
		navigationMenu.getItems().add(logout);

		navigationBar.getMenus().addAll(dashboardMenu, navigationMenu);
		scene = new Scene(borderContainer, 700, 500);
	}

	public void setLayout() {
		gridContainer.setAlignment(Pos.CENTER);
		gridContainer.setVgap(10);
		gridContainer.setHgap(10);
		gridContainer.add(yourCart, 0, 0);
		gridContainer.add(tableItem, 0, 1);
		gridContainer.add(totalPrice, 0, 2);
		gridContainer.add(checkoutBtn, 0, 3);

		BorderPane.setMargin(gridContainer, new Insets(10));

		// Add everything to the BorderPane
		borderContainer.setTop(navigationBar);
		VBox cart = new VBox(5, yourCart, gridContainer, totalPrice, checkoutBtn);
		cart.setAlignment(Pos.CENTER);
		borderContainer.setCenter(cart);
	}

	public void setUpTable(SceneManager sceneManager) {
		TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
		nameColumn.setPrefWidth(150);

		TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getPrice()).asObject());
		priceColumn.setPrefWidth(100);

		TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantity()).asObject());
		quantityColumn.setPrefWidth(100);

		TableColumn<Item, Integer> totalColumn = new TableColumn<>("Total");
		totalColumn.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTotal()).asObject());
		totalColumn.setPrefWidth(120);

		tableItem.getColumns().addAll(nameColumn, priceColumn, quantityColumn, totalColumn);
		tableItem.setItems(cartItems);
		
		
	}
	
	public void getTotalSum(TableView<Item> table) {
		int totalSum = 0;
		for(Item item : table.getItems()) {
			totalSum += item.getTotal();
		}
		totalPrice.setText("Rp. " + totalSum);
	}
	
	public void setUserData(User user) {
		this.userData = user;
	}

//	
	public void initializeCartItems(String UserID) {
		tableItem.setItems(CartController.getCartItem(UserID));
		getTotalSum(tableItem);
	}

	public void testCartPage() {
		System.out.println("This is cart page");
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return this.scene;
	}

	public void handleAction(SceneManager sceneManager) {
		checkoutBtn.setOnMouseClicked(e -> {
			Item selectedItem = tableItem.getSelectionModel().getSelectedItem();

			if (selectedItem == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Checkout Failed");
				alert.setHeaderText("Error");
				alert.setContentText("No item selected for checkout.");
				alert.showAndWait();
			} else {
				String transactionID = "TR" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
			
				boolean insertTrHeader = TransactionController.insertTransactionHeader(transactionID, userData.getUserID());
				boolean insertTrDetail = TransactionController.insertTransactionDetail(transactionID, selectedItem);
				
				if(insertTrHeader && insertTrDetail) {
					CartController.deleteCart(userData.getUserID(), selectedItem.getDonutId());
					initializeCartItems(userData.getUserID());
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Checkout Success");
					alert.setHeaderText("Message");
					alert.setContentText("Checkout Success");
					alert.showAndWait();
	
				} else {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Checkout Fail");
					alert.setHeaderText("Message");
					alert.setContentText("Transaction Failed");
					alert.showAndWait();
				}
				
			
			}
			System.out.println("Checkout");
		});
		
		logout.setOnAction(e -> {
			sceneManager.changeScene("Login Page");
		});
		
		homeScene.setOnAction(e -> {
			sceneManager.changeScene("Home Page");
		});
		cartScene.setOnAction(e -> {
			sceneManager.changeScene("Cart Page");
		});
	}

	public CartPage(Stage stage, SceneManager sceneManager) {
		// TODO Auto-generated constructor stub
		initialize();
		setLayout();
		setUpTable(sceneManager);
		handleAction(sceneManager);
		
		this.stage = stage;
		this.stage.setTitle("Dv.CO | Cart");
	}

}
