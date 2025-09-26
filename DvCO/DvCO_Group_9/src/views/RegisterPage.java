package views;

import java.util.Random;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.SceneManager;
import model.User;

public class RegisterPage {
	private Stage stage;
    private Scene scene;
    
    private BorderPane bp;
    private GridPane gp;
    private FlowPane fp;
    private VBox registerForm;

    private Label signIn, judul, usernameLb, emailLb, passwordLb, confirmPassowrdLb, ageLb, genderLb, countryLb, phoneNumberLb;
    private RadioButton maleGender, femaleGender;
    private TextField usernameTF, emailTF, phoneNumberTF;
    private Spinner<Integer> ageSpinner;
    private ComboBox<String> countrySelection;
    private PasswordField passwordPF, confirmPasswordPF;
    private Button registerBtn;
    private ToggleGroup gender;
    private CheckBox agree;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem loginMenuItem, registerMenuItem;
    private Hyperlink signInLink;
    private Random rand = new Random();
	
	public void initialize() {
		
		bp = new BorderPane();
		gp = new GridPane();
		fp = new FlowPane();
		
		
		
		judul = new Label("REGISTER");
		judul.setStyle("-fx-font-size:24;-fx-font-weight:bold;");
		usernameLb = new Label("Username 					:");
		emailLb = new Label("Email 						:");
		passwordLb = new Label("Password 					:");
		confirmPassowrdLb = new Label("Confirm Password 				:");
		ageLb = new Label("Age 							:");
		genderLb = new Label("Gender 						:");
		countryLb = new Label("Country 						:");
		phoneNumberLb = new Label("Phone Number 				:");
		
		//TextField
		usernameTF = new TextField();
	
		emailTF = new TextField();
		phoneNumberTF = new TextField();
		
		passwordPF = new PasswordField();
		confirmPasswordPF = new PasswordField();
		//Age Spinner
		ageSpinner = new Spinner<Integer>(1,90,1);
		
		//Toggle Gender
		gender = new ToggleGroup();
		
		maleGender = new RadioButton("Male");
		femaleGender = new RadioButton("Female");
		
		maleGender.setToggleGroup(gender);
		femaleGender.setToggleGroup(gender);
		
		countrySelection = new ComboBox<String>();
		countrySelection.getItems().add("Indonesia");
		countrySelection.getItems().add("Malaysia");
		countrySelection.getItems().add("Singapore");
		countrySelection.getSelectionModel().selectFirst();
		
		agree = new CheckBox("Agree to terms and condition");
		
		registerBtn = new Button("Register");
		
		
	}
	
	public void styleForm() {
		
		usernameTF.setMaxWidth(200);
		emailTF.setMaxWidth(200);
		passwordPF.setMaxWidth(200);
		confirmPasswordPF.setMaxWidth(200);
		ageSpinner.setMaxWidth(200);
		countrySelection.setMaxWidth(200);
		phoneNumberTF.setMaxWidth(200);
		
		gp.add(usernameLb, 0, 0);
		gp.add(emailLb, 0, 1);
		gp.add(passwordLb, 0, 2);
		gp.add(confirmPassowrdLb, 0, 3);
		gp.add(ageLb, 0, 4);
		gp.add(genderLb, 0, 5);
		gp.add(countryLb, 0, 6);
		gp.add(phoneNumberLb, 0, 7);
		
		gp.add(usernameTF, 1, 0);
		gp.add(emailTF, 1, 1);
		gp.add(passwordPF, 1, 2);
		gp.add(confirmPasswordPF, 1, 3);	
		gp.add(ageSpinner,1, 4);
		gp.add(new HBox(10,maleGender,femaleGender),1,5);
		
		gp.add(countrySelection, 1, 6);
		gp.add(phoneNumberTF, 1, 7);
		gp.add(agree, 0, 8);
	
		HBox registerButton = new HBox(registerBtn);
		registerButton.setAlignment(Pos.BOTTOM_RIGHT);
		gp.add(registerButton, 1, 9);
		
		gp.setHgap(10);
		gp.setVgap(10);
		gp.setAlignment(Pos.CENTER);
		
		
		menu = new Menu("Menu");
		menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		
		loginMenuItem = new MenuItem("Login");
		registerMenuItem = new MenuItem("Register");
		
		menu.getItems().addAll(loginMenuItem, registerMenuItem);
		signIn = new Label("Already have an acount? ");
		signInLink = new Hyperlink("Sign In!");
		HBox signInHyperLink = new HBox(5, signIn, signInLink);
		signInHyperLink.setAlignment(Pos.CENTER);
		
		VBox registerForm = new VBox(5,judul,gp, signInHyperLink);
		registerForm.setAlignment(Pos.CENTER);
		
		
		bp.setTop(menuBar);
		bp.setCenter(registerForm);
		scene = new Scene(bp,1000,500);
		
		registerMenuItem.setDisable(true);
	}
	
	public void handleAction(SceneManager sceneManager) {
		signInLink.setOnMouseClicked(e -> {
			sceneManager.changeScene("Login Page");
		});
		loginMenuItem.setOnAction(e -> {
			if(sceneManager.scenes.containsKey("Login Page")) {
				usernameTF.clear();
				emailTF.clear();
				phoneNumberTF.clear();
				passwordPF.clear();
				confirmPasswordPF.clear();
				ageSpinner.getValueFactory().setValue(1);
				gender.selectToggle(null);
				countrySelection.getSelectionModel().selectFirst();
				agree.setSelected(false);
				sceneManager.changeScene("Login Page");
			} else {
				System.out.println("Login Page Not Found");
			}
		});
		
		registerBtn.setOnMouseClicked(e -> {
			String username = usernameTF.getText();
			String email = emailTF.getText();
			String phoneNumber = phoneNumberTF.getText();
			String password = passwordPF.getText();
			String confirmPassword = confirmPasswordPF.getText();
			int age = ageSpinner.getValue();
			RadioButton selectedGender = (RadioButton) gender.getSelectedToggle();
			String genderValue = (selectedGender != null) ? selectedGender.getText() : "";
			String country = countrySelection.getValue();
			boolean isAgreed = agree.isSelected(); 
			
			String UserId = "US" + rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
			User newUser = new User(UserId, username, email, password, age, genderValue, country, phoneNumber, "User");

			if(newUser.getUsername().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Username cannot be empty");
			} else if(newUser.getUsername().length() < 3 || newUser.getUsername().length() > 15) {
				showErrorAlert("Register Failed", "Error", "Username must be between 3-15 characters (inclusive).");
			} else if(newUser.getEmail().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Email cannot be empty");
			} else if(!newUser.getEmail().endsWith("@gmail.com")) {
				showErrorAlert("Register Failed", "Error", "Email must end with @gmail.com");
			} else if(newUser.getPassword().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Password cannot be empty");
			} else if(!isAlphanumeric(newUser.getPassword())) {
				showErrorAlert("Register Failed", "Error", "Password must be alphanumeric");
			} else if(confirmPassword.isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Confirm Password cannot be empty");
			} else if(!confirmPassword.equals(newUser.getPassword())) {
				showErrorAlert("Register Failed", "Error", "Confirm Password must same with password");
			} else if(newUser.getAge() == 0) {
				showErrorAlert("Register Failed", "Error", "Age cannot be empty");
			} else if(newUser.getAge() > 13) {
				showErrorAlert("Register Failed", "Error", "Age must be older than 13 years.");
			} else if(newUser.getGender().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Gender Cannot be empty");
			} else if(newUser.getCountry().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "Gender Cannot be empty");
			} else if(newUser.getPhoneNumber().isEmpty()) {
				showErrorAlert("Register Failed", "Error", "PhoneNumber cannot be empty");
			} else if(newUser.getPhoneNumber().length() > 15) {
				showErrorAlert("Register Failed", "Error", "Phone Number must be less than 15 characters long");
			} else if(!isAgreed) {
				showErrorAlert("Register Failed", "Error", "Checkbox must be checked");
			} else {
				boolean isUserExist = UserController.isUserExist(username, email);
				if(isUserExist) {
					showErrorAlert("Register Failed", "Error", "User already exist, please change your username or email");
				} else {
					usernameTF.clear();
					emailTF.clear();
					phoneNumberTF.clear();
					passwordPF.clear();
					confirmPasswordPF.clear();
					ageSpinner.getValueFactory().setValue(1);
					gender.selectToggle(null);
					countrySelection.getSelectionModel().selectFirst();
					agree.setSelected(false);
					UserController.registerUser(newUser);
					sceneManager.changeScene("Login Page");
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setTitle("Register Success");
					alert.setHeaderText("Message");
					alert.setContentText("Register Success");
					alert.showAndWait();
				}
			}
			
			System.out.println(genderValue);
		});
	}
	
	public void showErrorAlert(String title, String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public boolean isAlphanumeric(String str) {
	 
	    for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if (!Character.isLetterOrDigit(c)) {
	            return false;
	        }
	    }
	    return true;
	}
    
    public RegisterPage(Stage stage, SceneManager sceneManager) {
       initialize();
       styleForm();
       handleAction(sceneManager);
       this.stage = stage;
       this.stage.setTitle("Register Page");
    }

    public Scene getScene() {
        return this.scene;
    }
}
