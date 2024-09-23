package org.makerminds.jcoaching.restaurantapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.makerminds.jcoaching.restaurantapp.controller.LocationManager;
import org.makerminds.jcoaching.restaurantapp.controller.MenuPrinter;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderManager;
import org.makerminds.jcoaching.restaurantapp.controller.table.TablePrinter;
import org.makerminds.jcoaching.restaurantapp.controller.table.TableReservationManager;
import org.makerminds.jcoaching.restaurantapp.model.Client;
import org.makerminds.jcoaching.restaurantapp.model.Menu;
import org.makerminds.jcoaching.restaurantapp.model.Order;
import org.makerminds.jcoaching.restaurantapp.model.Restaurant;
import org.makerminds.jcoaching.restaurantapp.model.enums.ApplicationMode;
import org.makerminds.jcoaching.restaurantapp.model.enums.Location;
import org.makerminds.jcoaching.restaurantapp.model.table.Table;
import org.makerminds.jcoaching.restaurantapp.model.table.TableProvider;

public class RestaurantApp {

	private static Location currentLocation;
	private static int tableId;
	//nexInt -> input value should be Integer
	//nextLine -> Input value 	
	private static Scanner scanner = new Scanner(System.in);
	
	private static TableProvider tableProvider = new TableProvider();
	private static List<Table> tableList = tableProvider.getTableList();
	private static TableReservationManager tableReservationManager = new TableReservationManager(tableList);
	
	private static final String MENU_FILE_PATH = "/menu-file.txt";
	
	public static void main(String[] args) {
		ApplicationMode applicationMode;
		try {
			do {
				applicationMode = getApplicationMode();
				if (applicationMode == ApplicationMode.ORDER) {
					getCurrentLocation();
				}
				validateApplicationMode(applicationMode);
			} while (applicationMode != ApplicationMode.EXIT);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			scanner.close();
		}
	}
	
	
	private static void validateApplicationMode(ApplicationMode applicationMode) {
		switch (applicationMode) {
		case ORDER: 
			runOrderProcess();
			break;
		case TABLERESERVATION:
			tableReservation();
			break;
		case EXIT:{
			System.out.println("The application Mode has stopped runing, Thank you for using our app");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + applicationMode.name());
		}
	}
	
	
	 
	
	private static ApplicationMode getApplicationMode() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Please select an application mode(type number):")
		.append(System.lineSeparator())
		.append("1. " + ApplicationMode.ORDER.name())
		.append(System.lineSeparator())
		.append("2. " + ApplicationMode.TABLERESERVATION.name())
		.append(System.lineSeparator())
		.append("3. " + ApplicationMode.EXIT.name())
		.append(System.lineSeparator());
		
		System.out.print(stringBuilder);
		int selectedApplicationmodeId = scanner.nextInt();
		ApplicationMode selectedApplicationMode = getApplicationModeFromId(selectedApplicationmodeId);
		return selectedApplicationMode;
	}
	
	private static Location getCurrentLocation() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Please select your location(type number):")
		.append(System.lineSeparator())
		.append("1. " + Location.KOSOVO.name())
		.append(System.lineSeparator())
		.append("2. " + Location.GERMANY.name())
		.append(System.lineSeparator());
		System.out.print(stringBuilder);
		int selectedLocationId = scanner.nextInt();
		currentLocation = LocationManager.getLocationFromId(selectedLocationId);
		return currentLocation;
	}
	
	private static ApplicationMode getApplicationModeFromId(int selectedApplicationmodeId) {
		switch (selectedApplicationmodeId) {
		case 1: {
			return ApplicationMode.ORDER;
		}
		case 2: {
			return ApplicationMode.TABLERESERVATION;
		}
		case 3: {
			
			return ApplicationMode.EXIT;
            
		}
		
		default:
			throw new IllegalArgumentException("Invalid order application");
		}
	}

	private static void runOrderProcess() {
		
		Restaurant restaurant = new Restaurant("Route 66", "Te Heroinat, Prishtine");
		Client client = new Client("Filan Fisteku", "+38349123456");
		
		//Created a object of Menu class
		Menu menu = new Menu();
		menu.initializeMenuProducts();
		//Created a object of OrderPrinter class
		MenuPrinter menuPrinter = new MenuPrinter();
		menuPrinter.printMenu(menu);

		OrderManager orderManager = new OrderManager(currentLocation);
		Order order = orderManager.createOrder(menu);
		orderManager.getOrders().add(order);
		
		orderManager.calculateAndPrintOrderDetails(restaurant, client, order);
	}
	

	
	private static void tableReservation() {
		
		
		
		TablePrinter tablePrinter = new TablePrinter();
		tablePrinter.printTableList(tableList);
		boolean tableWasFound = false;
		
		//get table id 
		tableId = getTableIdFromUser();
		
		// get table reservations localDateTime
		List<LocalDateTime> tableReservations =  tableReservationManager.getTableReservationByTableId(tableId);
		tablePrinter.printTableReservations(tableId, tableReservations);
		
		LocalDateTime reservationLocalDataTime = getReservationDateTimeFromUser();
		
		boolean isTableFree = tableReservationManager.isTableFreeAt(tableId, reservationLocalDataTime);
		if(isTableFree) {
			tableReservationManager.addReservation(tableId, reservationLocalDataTime);
			System.out.println("Table with table id: " + tableId + "has been reserved for " + reservationLocalDataTime);
		}
		else
		{
			System.out.println(" the Table with id: " + tableId + "is already reserved at " + reservationLocalDataTime);
		}
		
	}
	
	private static LocalDateTime getReservationDateTimeFromUser() {
		System.out.println("Please enter the date and time (yyyy-MM-dd HH:mm): ");
		String input = scanner.nextLine();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return LocalDateTime.parse(input, dateTimeFormatter);
	}




	private static int getTableIdFromUser() {
		System.out.println("Please enter the table ID: ");
		int selectedId = scanner.nextInt();
		scanner.nextLine();
		return selectedId;
	}
	
}
