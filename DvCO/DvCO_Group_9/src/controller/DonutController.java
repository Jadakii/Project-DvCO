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
import model.Donut;

public class DonutController {
	public static DatabaseConnection db = DatabaseSingleton.getInstance();
	
	public DonutController() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<Donut> getDonuts() {
		String query = "SELECT * FROM msdonut";
		List<Donut> donutList = new ArrayList<Donut>();
		String donutId;
		String donutName;
		String donutDescription;
		int donutPrice;
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				donutId = rs.getString("DonutID");
				donutName = rs.getString("DonutName");
				donutDescription = rs.getString("DonutDescription");
				donutPrice = rs.getInt("DonutPrice");
				donutList.add(new Donut(donutId, donutName, donutDescription, donutPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return donutList;
	}
	public static ObservableList<String> getDonutName(String donutId) {
		String query = "SELECT DonutName FROM msdonut WHERE DonutID = ?";
		ObservableList<String> donutList = FXCollections.observableArrayList();
		String donutName;
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, donutId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				donutName = rs.getString("DonutName");
				donutList.add(donutName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return donutList;
	}
	
	public static ObservableList<Donut> getDonutsAdmin() {
		String query = "SELECT * FROM msdonut";
		ObservableList<Donut> donutList = FXCollections.observableArrayList();
		String donutId;
		String donutName;
		String donutDescription;
		int donutPrice;
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				donutId = rs.getString("DonutID");
				donutName = rs.getString("DonutName");
				donutDescription = rs.getString("DonutDescription");
				donutPrice = rs.getInt("DonutPrice");
				donutList.add(new Donut(donutId, donutName, donutDescription, donutPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return donutList;
	}
	
	public static void addDonuts(Donut donut) {
		String query = "INSERT INTO msdonut (DonutID, DonutName, DonutDescription, "
				+ "DonutPrice) VALUES (?, ?, ?, ?)";
		
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, donut.getDonutId());
			stmt.setString(2, donut.getDonutName());
			stmt.setString(3, donut.getDonutDescription());
			stmt.setInt(4, donut.getDonutPrice());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateDonuts(Donut donut) {
		String query = "UPDATE msdonut SET DonutName = ?, "
				+ "DonutDescription = ?, DonutPrice = ? "
				+ "WHERE DonutID = ?";
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, donut.getDonutName());
			stmt.setString(2, donut.getDonutDescription());
			stmt.setInt(3, donut.getDonutPrice());
			stmt.setString(4, donut.getDonutId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteDonuts(String donutId) {
		String query = "DELETE FROM msdonut WHERE DonutID = ?";
		
		try {
			PreparedStatement stmt = db.getConnection().prepareStatement(query);
			stmt.setString(1, donutId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
