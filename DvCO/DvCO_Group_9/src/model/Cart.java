package model;

public class Cart {
	private String UserID;
	private String DonutID;
	private int Quantity;
	
	public Cart(String userID, String donutID, int quantity) {
		super();
		UserID = userID;
		DonutID = donutID;
		Quantity = quantity;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getDonutID() {
		return DonutID;
	}

	public void setDonutID(String donutID) {
		DonutID = donutID;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public Cart() {
		// TODO Auto-generated constructor stub
	}

}
