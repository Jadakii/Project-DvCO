package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.DatabaseConnection;
import Database.DatabaseSingleton;
import model.User;

public class UserController {
	public static DatabaseConnection db = DatabaseSingleton.getInstance();
	
	public UserController() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean isUserExist(String username, String email) {
		String query = "SELECT COUNT(*) FROM msuser  "
				+ "WHERE Username = ?  OR Email = ?";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, email);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean registerUser(User user) {
		String query = "INSERT INTO msuser (UserID, Username, Email, Password, Age, "
				+ "Gender, Country, PhoneNumber, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, user.getUserID());
			stmt.setString(2, user.getUsername());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setInt(5, user.getAge());
			stmt.setString(6, user.getGender());
			stmt.setString(7, user.getCountry());
			stmt.setString(8, user.getPhoneNumber());
			stmt.setString(9, user.getRole());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static User loginUser(String email, String password) {
		String query = "SELECT * FROM msuser WHERE Email = ? AND Password = ?";
		User loggedInUser = new User();
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String UserID = rs.getString("UserID");
				String Username = rs.getString("Username");
				String Email = rs.getString("Email");
				String Password = rs.getString("Password");
				int Age = rs.getInt("Age");
				String Gender = rs.getString("Gender");
				String Country = rs.getString("Country");
				String PhoneNumber = rs.getString("PhoneNumber");
				String Role = rs.getString("Role");
				loggedInUser = new User(UserID, Username, Email, Password, 0, Gender, Country, PhoneNumber, Role);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loggedInUser;
	}

}
