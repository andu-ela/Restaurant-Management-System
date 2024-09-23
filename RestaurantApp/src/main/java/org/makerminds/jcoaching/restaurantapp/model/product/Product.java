package org.makerminds.jcoaching.restaurantapp.model.product;

public class Product {
	
	private int productId;
	private String name;
	private double price;
	
	// removed the parametrized constructor and added a protected constructor
	public  Product(int productId, String name, double price) {
		this.productId = productId;
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return productId;
	}

	public void setId(int productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}
