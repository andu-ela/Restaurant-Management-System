package org.makerminds.jcoaching.restaurantapp.model.product;

//created new meal class which extends the Product class
public class Meal extends Product {

	// created new attribute which is meal specific
	String description;

	// provided one constructor for basic attributes as parameters
	public Meal(int productId, String name, double price) {
		super(productId, name, price);
	}

	// provided one constructor for basic attributes as parameters
	public Meal(int productId, String name, double price, String description) {
		this(productId, name, price);
		this.description = description;
	}

	// Getter and Setter
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
