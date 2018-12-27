package productManagement;

public class Product {
	
	private int prcode;
	private String prname;
	private int price;
	private String manufacture;
	
	/* get method */
	public int getPrcode() {
		return prcode;
	}
	
	public String getPrname() {
		return prname;
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getManufacture() {
		return manufacture;
	}
	
	/* set method */
	public void setPrcode(int prcode) {
		this.prcode = prcode;
	}
	
	public void setPrname(String prname) {
		this.prname = prname;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public void setManufacture(String manufacture) {
		this.manufacture  = manufacture;
	}

}
