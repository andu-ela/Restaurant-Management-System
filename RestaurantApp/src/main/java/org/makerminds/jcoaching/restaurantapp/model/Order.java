package org.makerminds.jcoaching.restaurantapp.model;

import java.util.ArrayList;

import org.makerminds.jcoaching.restaurantapp.model.order.OrderItem;

public class Order {

	//switched from array to arraylist
	private ArrayList<OrderItem> orderItems = new ArrayList<>();
	
	
	public Order() {
		
	}

	public ArrayList<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(ArrayList<OrderItem> productArrayList) {
		this.orderItems = productArrayList;
		
	}
	
	
}
