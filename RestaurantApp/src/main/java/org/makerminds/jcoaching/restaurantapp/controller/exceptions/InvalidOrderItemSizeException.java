package org.makerminds.jcoaching.restaurantapp.controller.exceptions;

@SuppressWarnings("serial")
public class InvalidOrderItemSizeException extends RuntimeException{

	public InvalidOrderItemSizeException(String message) {
		super(message);
	}

}
