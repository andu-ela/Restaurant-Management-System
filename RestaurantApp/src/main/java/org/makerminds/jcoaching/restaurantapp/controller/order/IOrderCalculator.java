package org.makerminds.jcoaching.restaurantapp.controller.order;

import org.makerminds.jcoaching.restaurantapp.model.Order;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItem;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItemSize;

public interface IOrderCalculator {

	public double calculateTotalOrderAmount(Order order); 
	public double calculateOrderItemPrice(OrderItem orderItem);
	public double getSizeRateAmount(OrderItemSize orderItemSize) ; //abstract
	public double calculateTotalOrderAmountVAT (double totalOrderAmount);
	public double getVATRate(boolean decimal ); //abstract
	public OrderAmount calculateOrderAmount(Order order);
	
}
