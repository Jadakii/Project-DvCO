package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Database.DatabaseConnection;
import Database.DatabaseSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cart;
import model.Item;

public class CartController {
	public static DatabaseConnection db = DatabaseSingleton.getInstance();
	
	public CartController() {
		// TODO Auto-generated constructor stub
	}
	
	public static void insertCart(Cart cart) {
		String query = "INSERT INTO cart (UserID, DonutID, Quantity) VALUES (?, ?, ?)";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, cart.getUserID());
			stmt.setString(2, cart.getDonutID());
			stmt.setInt(3, cart.getQuantity());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateCart(int quantity, String donutId) {
		String query = "UPDATE cart SET Quantity = ? WHERE DonutID = ?";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setInt(1, quantity);
			stmt.setString(2, donutId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteCart(String UserId, String donutId) {
		String query = "DELETE FROM cart WHERE UserID = ? AND DonutID = ?";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, UserId);
			stmt.setString(2, donutId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean isCartExist(String donutId, String userId) {
		String query = "SELECT COUNT(*) FROM cart WHERE DonutID = ? AND UserID = ?";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, donutId);
			stmt.setString(2, userId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static ObservableList<Item> getCartItem(String UserID) {
		String query = "SELECT md.DonutID, md.DonutName, md.DonutPrice, c.Quantity \r\n"
				+ "FROM msdonut md\r\n"
				+ "JOIN cart c ON md.DonutID = c.DonutID\r\n"
				+ "WHERE UserID = ?";
		ObservableList<Item> cartItems = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, UserID);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				String donutID = rs.getString("DonutID");
				String donutName = rs.getString("DonutName");
				int donutPrice = rs.getInt("DonutPrice");
				int quantity = rs.getInt("Quantity");
				int subTotal = quantity*donutPrice;
				cartItems.add(new Item(donutID, donutName, donutPrice, quantity, subTotal));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cartItems;
	}
	
	public static Item getSingleCartItem(String DonutID) {
		String query = "SELECT md.DonutID, md.DonutName, md.DonutPrice, c.Quantity \r\n"
				+ "FROM msdonut md\r\n"
				+ "JOIN cart c ON md.DonutID = c.DonutID\r\n"
				+ "WHERE c.DonutID = ?";
		Item singleCartItem = new Item();
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, DonutID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String donutID = rs.getString("DonutID");
				String donutName = rs.getString("DonutName");
				int donutPrice = rs.getInt("DonutPrice");
				int quantity = rs.getInt("Quantity");
				int subTotal = quantity*donutPrice;
				singleCartItem = new Item(donutID, donutName, donutPrice, quantity, subTotal);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return singleCartItem;
	}

}
