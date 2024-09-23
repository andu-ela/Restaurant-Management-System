package org.makerminds.jcoaching.restaurantapp.controller.order;

import org.makerminds.jcoaching.restaurantapp.controller.exceptions.InvalidOrderItemSizeException;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItemSize;

public class OrderCalculatorGER extends AbstractOrderCalculator {
	
	private final double VAT_RATE = 0.19;
	
	@Override
	public double getSizeRateAmount(OrderItemSize orderItemSize) {
		
		switch(orderItemSize) {
		case SMALL:
			return 0.7;
		case MEDIUM:
			return 1;
		case LARGE:
			return 1.2;
		case XXL:
			return 1.35;
			default:
				throw new InvalidOrderItemSizeException("Invalid order item size ");
		}
	}

	@Override
	public double getVATRate() {
		return VAT_RATE;
	}

}
