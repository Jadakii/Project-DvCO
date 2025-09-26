package main;

import Database.DatabaseConnection;
import controller.UserController;
import javafx.application.Application;
import javafx.stage.Stage;
import views.AdminPage;
import views.CartPage;
import views.HomePage;
import views.LoginPage;
import views.RegisterPage;

public class Main extends Application {

	UserController userController = new UserController();
	public Main() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneManager sceneManager = new SceneManager(primaryStage);
		// TODO Auto-generated method stub
		LoginPage loginPage = new LoginPage(primaryStage, sceneManager);
		RegisterPage registerPage = new RegisterPage(primaryStage, sceneManager);
		HomePage homePage = new HomePage(primaryStage, sceneManager);
		CartPage cartPage = new CartPage(primaryStage, sceneManager);
		AdminPage adminPage = new AdminPage(primaryStage, sceneManager);
		
		 
	    sceneManager.addScene("Login Page", loginPage.getScene(), loginPage);
        sceneManager.addScene("Register Page", registerPage.getScene(), registerPage);
        sceneManager.addScene("Home Page", homePage.getScene(), homePage);
		sceneManager.addScene("Cart Page", cartPage.getScene(), cartPage);
		sceneManager.addScene("Admin Page", adminPage.getScene(), adminPage);
		
		sceneManager.changeScene("Login Page");
//		boolean isLogin = UserController.loginUser("admin@gmail.com", "admin123");
//		System.out.println(isLogin)
		
		primaryStage.show();
	}

}
