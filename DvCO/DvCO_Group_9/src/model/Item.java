package model;

public class Item {

	public Item() {
		// TODO Auto-generated constructor stub
	}
	private String donutId;
	private String name;
    private int price;
    private int quantity;
    private int total;

    public Item(String donutId, String name, int price, int quantity, int total) {
        this.donutId = donutId;
    	this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }
    
    
    public String getDonutId() {
		return donutId;
	}

	public void setDonutId(String donutId) {
		this.donutId = donutId;
	}


	public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotal() {
        return total;
    }
    
    public void setQuantity(int quantity) {
    	this.quantity = quantity;
    }

	public void setTotal(int total) {
		this.total = total;
	}

}
