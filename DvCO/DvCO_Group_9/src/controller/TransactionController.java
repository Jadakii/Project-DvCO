package controller;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import Database.DatabaseConnection;
import Database.DatabaseSingleton;
import model.Item;

public class TransactionController {
	private static DatabaseConnection db = DatabaseSingleton.getInstance();
	
	public TransactionController() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean insertTransactionHeader(String transactionID, String userID) {
		String query = "INSERT INTO transactionheader (TransactionID, UserID) VALUES (?, ?)";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, transactionID);
			stmt.setString(2, userID);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean insertTransactionDetail(String transactionID, Item item) {
		String query = "INSERT INTO transactiondetail (TransactionID, DonutID, Quantity) VALUES (?, ?, ?)";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, transactionID);
			stmt.setString(2, item.getDonutId());
			stmt.setInt(3, item.getQuantity());
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
