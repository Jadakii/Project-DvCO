package model;

public class Donut {

	private String donutId;
    private String donutName;
    private String donutDescription;
    private int donutPrice;
	public Donut(String donutId, String donutName, String donutDescription, int donutPrice) {
		super();
		this.donutId = donutId;
		this.donutName = donutName;
		this.donutDescription = donutDescription;
		this.donutPrice = donutPrice;
	}
	public String getDonutId() {
		return donutId;
	}
	public void setDonutId(String donutId) {
		this.donutId = donutId;
	}
	public String getDonutName() {
		return donutName;
	}
	public void setDonutName(String donutName) {
		this.donutName = donutName;
	}
	public String getDonutDescription() {
		return donutDescription;
	}
	public void setDonutDescription(String donutDescription) {
		this.donutDescription = donutDescription;
	}
	public int getDonutPrice() {
		return donutPrice;
	}
	public void setDonutPrice(int donutPrice) {
		this.donutPrice = donutPrice;
	}

}
