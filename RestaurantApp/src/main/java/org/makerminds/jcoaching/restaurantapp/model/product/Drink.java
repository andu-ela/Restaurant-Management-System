package org.makerminds.jcoaching.restaurantapp.model.product;

// created new drink class which extends the Product class
public class Drink extends Product {
	
	// created new attribute which is drink specific
	boolean sugarFree;
	
	// provided one constructor for basic attributes as parameters
	public Drink(int productId, String name, double price, boolean sugarFree) {
	super(productId, name, price);
		this.sugarFree = sugarFree;
	}

	// Getter and Setter
	public boolean isSugarFree() {
		return sugarFree;
	}

	public void setSugarFree(boolean sugarFree) {
		this.sugarFree = sugarFree;
	}

	
}
