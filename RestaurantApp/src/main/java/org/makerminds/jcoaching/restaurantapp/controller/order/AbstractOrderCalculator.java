package org.makerminds.jcoaching.restaurantapp.controller.order;

import java.util.List;

import org.makerminds.jcoaching.restaurantapp.controller.exceptions.InvalidOrderItemSizeException;
import org.makerminds.jcoaching.restaurantapp.model.Order;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItem;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItemSize;
import org.makerminds.jcoaching.restaurantapp.model.product.Product;

public abstract class AbstractOrderCalculator implements IOrderCalculator {
	
	public double calculateTotalOrderAmount(Order order) {
		
		List<OrderItem> orderItems = order.getOrderItems();
		//int 1,2,3,....
		// double, float 0.2,3.4,8.5,11.8
		double totalOrderAmount = orderItems.stream()
				
				.mapToDouble(this::calculateOrderItemPrice)
				.sum();
		
		return totalOrderAmount;
	}
	
	public double calculateOrderItemPrice(OrderItem orderItem) {
		double sizeRateAmount = 1;
		try {
		 sizeRateAmount = getSizeRateAmount(orderItem.getOrderItemSize());
		}
		catch(InvalidOrderItemSizeException e) {
			System.out.println("No valid order item size for " + orderItem.getProduct().getName()+ ". Default size rate amout (100%) will vbe applied." );
		}
		Product product = orderItem.getProduct();
		double totalOrderItemPriceSingle = product.getPrice() * sizeRateAmount;
		orderItem.setOrderItemPrice(totalOrderItemPriceSingle);
		
		return totalOrderItemPriceSingle * orderItem.getQuantity();
	}
	
	public abstract double getSizeRateAmount(OrderItemSize orderItemSize);

	public double calculateTotalOrderAmountVAT (double totalOrderAmount) {
		
		return totalOrderAmount * getVATRate();
	}
	
	public double getVATRate(boolean decimal) {
		if(decimal) {
			return getVATRate();
		}
		else
		{
			return getVATRate()*100;
		}
	}
	
	public abstract double getVATRate();
	
	public OrderAmount calculateOrderAmount(Order order) {
		
		double totalOrderAmount = calculateTotalOrderAmount(order);
		
		double totalOrderAmountVAT = calculateTotalOrderAmountVAT(totalOrderAmount);
		
		double totalOrderAmountWithVAT = totalOrderAmount + totalOrderAmountVAT;
		
		OrderAmount orderAmount = new OrderAmount(totalOrderAmount, totalOrderAmountVAT, totalOrderAmountWithVAT);
		
		return orderAmount;
	}

}