package views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.CartController;
import controller.DonutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.Cart;
import model.Donut;
import model.Item;
import model.User;

public class HomePage {
	private Scene scene;
	private Stage stage;
	private MenuBar navigationBar;
	private Menu dashboardMenu;
	private Menu navigationMenu;
	private MenuItem cartScene;
	private MenuItem homeScene;
	private MenuItem logout;
	private Label welcomeLabel, activeDonutLabel;
//	private ListView<String> donutListView;
	private ListView<String> donutListView;
	private VBox detailsBox, root;
	private HBox mainLayout;
	private Label donutNameLabel;
	private Label donutDescLabel;
	private Label donutPriceLabel;
	private Button addToCartButton;
	private Spinner<Integer> quantitySpinner;
	private HashMap<String, String> donutIdMap = new HashMap<>();
	private HashMap<String, Donut> donutMap = new HashMap<>();
	private String donutId;
	private String donutName;
	private String description;
	private int price;
	private User currentUser = new User();
	private String username;
	

	public void setUser(User user) {
        this.currentUser = user;
        System.out.print("CurrentUser: ");
        System.out.println(currentUser.getUsername());

        welcomeLabel.setText("Hello, " + currentUser.getUsername());
         
    }
	
	public void initialize() {
		// Menu
		navigationBar = new MenuBar();
		dashboardMenu = new Menu("Dashboard");
		homeScene = new MenuItem("Home");
		cartScene = new MenuItem("Cart");
		dashboardMenu.getItems().addAll(homeScene, cartScene);

		navigationMenu = new Menu("Logout");
		logout = new MenuItem("Logout");
		navigationMenu.getItems().add(logout);

		navigationBar.getMenus().addAll(dashboardMenu, navigationMenu);
		// Label
		welcomeLabel = new Label("Hello, ");
		welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		activeDonutLabel = new Label("Active Donut:");
		activeDonutLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
		donutNameLabel = new Label();
		donutNameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
		donutDescLabel = new Label();
		donutDescLabel.setStyle("-fx-font-size: 12px;");
		donutPriceLabel = new Label();
		donutPriceLabel.setStyle("-fx-font-size: 12px;");

		addToCartButton = new Button("Add to cart");
		quantitySpinner = new Spinner<>(1, 999, 1);

	}
	
	public void initializeDonuts() {
		List<Donut> donutList = DonutController.getDonuts();
		ObservableList<String> donutName = FXCollections.observableArrayList();
		for(Donut donut : donutList) {
			donutName.addAll(DonutController.getDonutName(donut.getDonutId()));
			donutListView.setItems(donutName);
			donutIdMap.put(donut.getDonutName(), donut.getDonutId());
			donutMap.put(donut.getDonutName(), donut);
		}
		
	}
	public void createDetailsBox() {
		donutListView = new ListView<>();
		detailsBox = new VBox(10);
		detailsBox.setPadding(new Insets(10));
		detailsBox.setAlignment(Pos.TOP_LEFT);
		List<Donut> donutList = DonutController.getDonuts();
		for(Donut donut : donutList) {
			donutListView.getItems().add(donut.getDonutName());
			donutIdMap.put(donut.getDonutName(), donut.getDonutId());
			donutMap.put(donut.getDonutName(), donut);
		}
		
		donutListView.setPrefWidth(200);

		donutListView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null) {
				// Clear the detailsBox before adding new components
				detailsBox.getChildren().clear();
				Donut selectedDonut = donutMap.get(newValue);
				donutName = selectedDonut.getDonutName();
				donutId = selectedDonut.getDonutId();
				description = selectedDonut.getDonutDescription();
				price = selectedDonut.getDonutPrice();
				donutNameLabel.setText(donutName);
				donutDescLabel.setText(description);
				donutPriceLabel.setText("Rp. " + String.valueOf(price));
				
				
				detailsBox.getChildren().addAll(donutNameLabel, donutDescLabel, donutPriceLabel, quantitySpinner,
						addToCartButton);
				
				addToCartButton.setDisable(false);
				System.out.println(donutId);
			} else {
				detailsBox.getChildren().clear();
				addToCartButton.setDisable(true);
			}
		});
	
		addToCartButton.setDisable(true);
		
	}

	public void setLayout() {
	
		mainLayout = new HBox(20);
		mainLayout.setPadding(new Insets(10));
		mainLayout.getChildren().addAll(donutListView, detailsBox);
		if (welcomeLabel == null) {
	        welcomeLabel = new Label("Hello, ");
	        welcomeLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
	    }
		root = new VBox(10);
		root.getChildren().addAll(navigationBar, welcomeLabel, activeDonutLabel, mainLayout);

		scene = new Scene(root, 1000, 500);
	}

	private void handleAction(SceneManager sceneManager) {

		cartScene.setOnAction(e -> {
			CartPage cartPage = (CartPage) (CartPage) sceneManager.getScenesObject("Cart Page");
			cartPage.initializeCartItems(currentUser.getUserID());
			cartPage.setUserData(currentUser);
			sceneManager.changeScene("Cart Page");
		});

		homeScene.setOnAction(e -> {
			if (sceneManager.scenes.containsKey("Home Page")) {
				sceneManager.changeScene("Home Page");
			} else {
				System.out.println("Home Page Not Found");
			}
		});
		logout.setOnAction(e -> {
			sceneManager.changeScene("Login Page");
		});
		addToCartButton.setOnAction(event -> {
			int quantity = quantitySpinner.getValue();
			int total = (int) (price * quantity);
			Item newItem = new Item(donutId, donutName, price, quantity, total);
			boolean itemExist = false;
			CartPage cartPage = (CartPage) (CartPage) sceneManager.getScenesObject("Cart Page");

			if (cartPage != null) {

				itemExist = CartController.isCartExist(newItem.getDonutId(), currentUser.getUserID());
				System.out.println(itemExist);
				if(!itemExist) {
					Cart newCart = new Cart(currentUser.getUserID(), newItem.getDonutId(), quantity);
					System.out.println(newCart.getUserID());
					System.out.println(newCart.getDonutID());
					System.out.println(newCart.getQuantity());
					CartController.insertCart(newCart);	
				} else {
					Item item = CartController.getSingleCartItem(newItem.getDonutId());
					int updatedQuantity = item.getQuantity() + newItem.getQuantity();
					CartController.updateCart(updatedQuantity, newItem.getDonutId());
				}
				
			} else {
				System.err.println("CartPage is not initialized.");
			}
		});
	}

	public HomePage(Stage stage, SceneManager sceneManager) {
		if(currentUser.getUsername() != null) {
			System.out.print("CurrentUser: ");
	        System.out.println(currentUser.getUsername());
		}
		initialize();
		createDetailsBox();
		setLayout();
		handleAction(sceneManager);

    	this.stage = stage;
        this.stage.setTitle("DvCO | Home (User)");
	}

	public Scene getScene() {
		// TODO Auto-generated method stub
		return this.scene;
	}

}

