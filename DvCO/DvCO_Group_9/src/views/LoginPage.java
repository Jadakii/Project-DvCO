package views;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.User;

public class LoginPage {
	protected BorderPane bp;
	protected GridPane gp;
	protected FlowPane fp;
	protected VBox loginForm;
	
	protected Stage stage;
	protected Scene scene;

	protected Label emailLb, passwordLb, judul, signUp;
	protected Hyperlink signUpLink;
	protected TextField emailTF;
	protected PasswordField passwordPF;
	protected Button lgnBtn;
	
	protected MenuBar menuBar;
	protected Menu menu;
	protected MenuItem loginMenuItem, registerMenuItem;
	
	


	private void initialize() {
		
		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		
		
		menu = new Menu("Menu");
		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		loginMenuItem = new MenuItem("Login");
		registerMenuItem = new MenuItem("Register");
		
		menu.getItems().addAll(loginMenuItem, registerMenuItem);
		
		
		emailLb = new Label("Email       : ");
		emailTF = new TextField();
		emailTF.setMinWidth(200);
		
		passwordLb = new Label("Password : ");
		passwordPF = new PasswordField();
		
		lgnBtn = new Button("Login");
		HBox lgnButton = new HBox(lgnBtn);
		lgnButton.setAlignment(Pos.CENTER_RIGHT);
		gp.add(lgnButton, 1, 3);
		
		
		gp.add(emailLb, 0, 1);
		gp.add(emailTF, 1, 1);
		
		
		gp.add(passwordLb, 0, 2);
		gp.add(passwordPF, 1, 2);
		
		signUp = new Label("Dont have an account? ");
		signUpLink = new Hyperlink("Sign up!");
		HBox signUpHyperLink = new HBox(5, signUp, signUpLink);
		signUpHyperLink.setAlignment(Pos.CENTER);
		
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		
		judul = new Label("LOGIN");
		judul.setStyle("-fx-font-size:24;-fx-font-weight:bold;");
		
		 VBox loginForm = new VBox(10, judul, gp, signUpHyperLink);
		 loginForm.setAlignment(Pos.CENTER);

	    bp.setCenter(loginForm);
	    bp.setTop(menuBar);
		scene = new Scene(bp,1000,500);
		loginMenuItem.setDisable(true);
	}
	
	public void handleAction(SceneManager sceneManager) {
		
		signUpLink.setOnMouseClicked(e -> {
			String email = emailTF.getText();
			String password = passwordPF.getText();			
			sceneManager.changeScene("Register Page");
			emailTF.clear();
			passwordPF.clear();
		});
		
		
		registerMenuItem.setOnAction(e -> {
			sceneManager.changeScene("Register Page");
		});
		try {
			lgnBtn.setOnMouseClicked(e -> {
				String email = emailTF.getText();
				String password = passwordPF.getText();
			
				if(email != "" || password != "") {
					
					User loggedUser = UserController.loginUser(email, password);
					sceneManager.setLoggedInUser(loggedUser);
					if(sceneManager.getLoggedInUser().getUsername() != null && sceneManager.getLoggedInUser().getRole().equals("Admin")) {
						System.out.print("LoggedUser: ");
						System.out.println(loggedUser.getUsername());
						sceneManager.changeScene("Admin Page");
						AdminPage adminPage = (AdminPage)(AdminPage)sceneManager.getScenesObject("Admin Page");
						adminPage.setAdminUser(loggedUser);
						adminPage.initializeDonut();
						emailTF.clear();
						passwordPF.clear();
					}
					else if(sceneManager.getLoggedInUser().getUsername() != null ) {
						System.out.print("LoggedUser: ");
						System.out.println(loggedUser.getUsername());
						HomePage homePage = (HomePage)(HomePage)sceneManager.getScenesObject("Home Page");
						homePage.setUser(loggedUser);
						homePage.initializeDonuts();
						sceneManager.changeScene("Home Page");
						
						emailTF.clear();
						passwordPF.clear();
					}
					else {
						System.out.print("LoggedUser: ");
						System.out.println(loggedUser.getUsername());
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Login Failed");
						alert.setHeaderText("Error");
						alert.setContentText("Wrong Credentials");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Login Failed");
					alert.setHeaderText("Error");
					alert.setContentText("Please fill the email and password");
					alert.showAndWait();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	
	public Scene getScene() {
		return this.scene;
	}
	
	public LoginPage(Stage stage, SceneManager sceneManager) {
		initialize();
		handleAction(sceneManager);
		this.stage = stage;
		this.stage.setTitle("Login Page");
	}
}
