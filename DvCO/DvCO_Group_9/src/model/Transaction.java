package model;

public class Transaction {
	  private String transactionID;
      private String itemName;
      private double totalPrice;

      public Transaction(String transactionID, String itemName, double totalPrice) {
          this.transactionID = transactionID;
          this.itemName = itemName;
          this.totalPrice = totalPrice;
      }

      public String getTransactionID() {
          return transactionID;
      }

      public String getItemName() {
          return itemName;
      }

      public double getTotalPrice() {
          return totalPrice;
      }
	public Transaction() {
		// TODO Auto-generated constructor stub
	}

}
