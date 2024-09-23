package org.makerminds.jcoaching.restaurantapp.controller.order;



import org.makerminds.jcoaching.restaurantapp.model.Client;
import org.makerminds.jcoaching.restaurantapp.model.Restaurant;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItem;
import org.makerminds.jcoaching.restaurantapp.model.product.Product;

public class OrderPrinter {
	// global
	private StringBuilder stringBuilder = new StringBuilder();

	public void printOrderInfoHeader(Client client) {
		// header
		//"------------" <hr>
		//"System.lineSeperator()" -- <br>
		stringBuilder.append("<html> Order from : ").append(client.getName())
				.append("<br>").append("Contact number: " + client.getPhoneNumber())
				.append("<br>").append("<hr>").append("<br>");

	}

	public void printOrderItemInfo(OrderItem orderItem) {

		Product product = orderItem.getProduct();
		double totalOrderItemPrice = orderItem.getOrderItemPrice() * orderItem.getQuantity();
		stringBuilder.append(orderItem.getQuantity()).append("x |" + product.getId()).append(". " + product.getName())
				.append(" | ").append(orderItem.getOrderItemPrice()).append(" | ").append(totalOrderItemPrice)
				.append(" Euro.").append("<br>");

	}

	public void printOrderInfoFooter(Restaurant restaurant, OrderAmount orderAmount, int vatRate) {
		// footer
		stringBuilder.append("<hr>").append("<br>").append("The price of order is").append("<br>").append("Sub TOTAL")
				.append(orderAmount.getTotalOrderAmount()).append("Euro").append("<br>");
		stringBuilder.append("VAT ").append(vatRate).append(": ").append(orderAmount.getTotalOrderAmountVAT())
				.append("Euro.").append("<br>")
				.append("TOTAL: " + orderAmount.getTotalOrderAmountWithVAT() + "Euro.").append("<br>")
				.append("<hr>").append("<br>")
				.append(restaurant.getName() + " in " + restaurant.getAddress()).append("<br>");
		//System.out.println(stringBuilder);
		

	}

	public String getInvoiceText() {
		return stringBuilder.toString();
	}
}
