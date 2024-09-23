package org.makerminds.jcoaching.restaurantapp.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.makerminds.jcoaching.restaurantapp.controller.MenuImporter;
import org.makerminds.jcoaching.restaurantapp.controller.order.OrderManager;
import org.makerminds.jcoaching.restaurantapp.model.Client;
import org.makerminds.jcoaching.restaurantapp.model.Menu;
import org.makerminds.jcoaching.restaurantapp.model.Order;
import org.makerminds.jcoaching.restaurantapp.model.Restaurant;
import org.makerminds.jcoaching.restaurantapp.model.enums.Location;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItem;
import org.makerminds.jcoaching.restaurantapp.model.order.OrderItemSize;
import org.makerminds.jcoaching.restaurantapp.model.product.Product;

public class RestaurantAppGui {


	private static final String MENU_FILE_PATH = "/menu-file.txt";
	private JFrame frame;
	private JTable menuTable;
	private DefaultTableModel menuTableDefaultModel;
	private JTable orderItemTable;
	private DefaultTableModel orderItemTableModel;
	private JComboBox<String> orderItemSizeSelector;
	private JTextField quantityTextField;
	private JTextField inputNameTextField;
	private JTextField inputPhoneNumberTextField;
	private JRadioButton kosovoRadioButton;
	private JRadioButton germanyRadioButton;
	private JLabel invoiceLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurantAppGui window = new RestaurantAppGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RestaurantAppGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		//frame.getContentPane().add(createMenuTableScrollPane());
		frame.getContentPane().add(createOrderItemInputPanel());
		//frame.getContentPane().add(createOrderItemTableScrollPane());
		//frame.getContentPane().add(createAddButton());
		frame.getContentPane().add(createOrderPanel());
		frame.getContentPane().add(createOrderButton());
		frame.getContentPane().add(createDeleteButton());
		frame.getContentPane().add(createIncoicePanel());
		
	}
	
	private JPanel createOrderItemInputPanel() {
		JPanel inputOrderItemPanel = new JPanel();
		inputOrderItemPanel.setLayout(null);
		inputOrderItemPanel.setBounds(10, 10, 320, 350);
		inputOrderItemPanel.add(createOrderItemSizeSelector());
		inputOrderItemPanel.add(createQuantityInputLabel());
		inputOrderItemPanel.add(createQuantityInputTextField());
		inputOrderItemPanel.add(createAddButton());
		inputOrderItemPanel.add(createMenuTableScrollPane());
		return inputOrderItemPanel;
	}
	
	//--------OrderItemPanel---------
	private JLabel createQuantityInputLabel() {
		JLabel quantityLabel = new JLabel("Quantity: ");
		quantityLabel.setBounds(10,60,100,30);
		return quantityLabel;
	}
	
	private JTextField createQuantityInputTextField() {
		quantityTextField = new JTextField();
		quantityTextField.setBounds(70,60, 100, 30);
		return quantityTextField;
	}
	
	private JComboBox<String> createOrderItemSizeSelector(){
		orderItemSizeSelector = new JComboBox<>();
		orderItemSizeSelector.setBounds(10,10, 200, 30);
		for(OrderItemSize orderIterSize : OrderItemSize.values())
		{
			orderItemSizeSelector.addItem(orderIterSize.name());
		}
		return orderItemSizeSelector;
	}
	
	private JScrollPane createMenuTableScrollPane() {
		menuTableDefaultModel = prepareMenuDataForTable();
		menuTable = new JTable(menuTableDefaultModel);
		
		//create a JScrollPane to be able to see the header within JTable
		JScrollPane scrollPane = new JScrollPane(menuTable);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Restaurant Menu"));
		scrollPane.setBounds(10, 100, 300, 250);
		return scrollPane;
	}

	private DefaultTableModel prepareMenuDataForTable() {
		
		DefaultTableModel menuTableModel = new DefaultTableModel();
		
		String[][] menuArray = createMenuArray();
		String[] tableHeader = {"ID", "Name", "Price"};
		menuTableModel.setDataVector(menuArray, tableHeader);
		
		return menuTableModel;
	}

	private String[][] createMenuArray() {
		MenuImporter menuImporter = new MenuImporter();
		Menu menu = menuImporter.importMenu(MENU_FILE_PATH);
		menu.initializeMenuProducts();
		
		HashMap<Integer,Product> menuItems = menu.getMenuItems();
		String[][] menuArray = new String[menuItems.size()][3];
		
		int i = 0;
		for(Entry<Integer,Product> menuItem : menuItems.entrySet()) {
			Product product = menuItem.getValue();
			
			menuArray[i][0] = Integer.toString(product.getId());
			menuArray[i][1] = product.getName();
			menuArray[i][2] = Double.toString(product.getPrice());
			i++;
			
			
		}
		
		return menuArray;
	}
	
	private JScrollPane createOrderItemTableScrollPane() {
		
		//definon strukturen e default table model per orderItemTable
		createOrderItemDataForTable();
		orderItemTable = new JTable(orderItemTableModel);
		JScrollPane jScrollPane = new JScrollPane(orderItemTable);
		jScrollPane.setBorder(BorderFactory.createTitledBorder("Order items"));
		jScrollPane.setBounds(10, 100, 400, 250);
		return jScrollPane;
	}
	
	private JPanel createOrderPanel() {
		JPanel orderPanel = new JPanel();
		orderPanel.setLayout(null);
		orderPanel.setBounds(370, 10, 450, 350);
		orderPanel.add(createInputNameLabel());
		orderPanel.add(createNameInputTextField());
		orderPanel.add(createPhoneNumberInputTextField());
		orderPanel.add(creatImputPhoneNrLabel());
		orderPanel.add(createOrderItemTableScrollPane());
		createRadioButtons(orderPanel);
		return orderPanel;
	}
	
	private JLabel createInputNameLabel() {
		JLabel inputNameLabel = new JLabel("Name: ");
		inputNameLabel.setBounds(10, 10, 100, 30);
		return inputNameLabel;
	}
	
	private JLabel creatImputPhoneNrLabel() {
		JLabel impuntPhoneNrLabel = new JLabel("PhoneNr: ");
		impuntPhoneNrLabel.setBounds(10, 60, 100, 30);
		return impuntPhoneNrLabel;
	}
	
	private JTextField createNameInputTextField() {
		inputNameTextField = new JTextField();
		inputNameTextField.setBounds(100, 10, 150, 30);
		return inputNameTextField;
	}
	
	private JTextField createPhoneNumberInputTextField() {
		inputPhoneNumberTextField = new JTextField();
		inputPhoneNumberTextField.setBounds(100, 60, 150, 30);
		return inputPhoneNumberTextField;
	}
	
	private void createOrderItemDataForTable() {
		orderItemTableModel = new DefaultTableModel();
		String[] columnNames = {"Id", "Name", "Price", "Quantity", "Item Size"};
		orderItemTableModel.setColumnIdentifiers(columnNames);
	}
	
	private void createRadioButtons(JPanel contentPanel) {
		
		ButtonGroup locationButtonGroup = new ButtonGroup();
		
		kosovoRadioButton = new JRadioButton("Kosovo");
		germanyRadioButton = new JRadioButton("Germany");
		kosovoRadioButton.setBounds(300,10,100,30);
		germanyRadioButton.setBounds(300, 50, 100,30);
		
		kosovoRadioButton.setSelected(true);
		locationButtonGroup.add(kosovoRadioButton);;
		locationButtonGroup.add(germanyRadioButton);
		
		contentPanel.add(kosovoRadioButton);
		contentPanel.add(germanyRadioButton);
		
	}
	
	private JButton createAddButton() {
		JButton addButton = new JButton("Add");
		addButton.setBounds(190, 60,  60, 30);
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = menuTable.getSelectedRow();
				
				//   AND --> &&
				// true and true = true
				// true and false = false
				// false and true = false
				// false and false = false
				
				//   OR --> ||
				// true and true = true
				// true and false = true
				// false and true = true
				// false and false = false
				
				if(selectedRow != -1 && isQuantityValid() ) {
					String[] orderItemData = new String[5];
					orderItemData[0]= menuTable.getValueAt(selectedRow, 0).toString();
					orderItemData[1]= menuTable.getValueAt(selectedRow, 1).toString();
					orderItemData[2]= menuTable.getValueAt(selectedRow, 2).toString();
					orderItemData[3] = quantityTextField.getText();
					orderItemData[4] = (String)orderItemSizeSelector.getSelectedItem();
					orderItemTableModel.addRow(orderItemData);
					quantityTextField.setText("");
					orderItemSizeSelector.setSelectedIndex(0);
					menuTable.clearSelection();
				}
				else {
					JOptionPane.showMessageDialog(frame, "Select any row to add");
				}
				
			}
		});
		return addButton;
	}
	
	private JButton createDeleteButton() {
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(510, 400,80,40);
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRowIndex = orderItemTable.getSelectedRow();
				if(selectedRowIndex != -1) {
					orderItemTableModel.removeRow(selectedRowIndex);
				}
				else {
					JOptionPane.showMessageDialog(frame, "Select any row to remove");
				}
			}
		});
		return deleteButton;
	}
	
	private JButton createOrderButton() {
		JButton order = new JButton("Order");
		order.setBounds( 380, 400,80,40);
		order.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Restaurant restaurant = new Restaurant("Route 66", "Te Heroinat, Prishtine");
				Client client = new Client(inputNameTextField.getText(), inputPhoneNumberTextField.getText());
				OrderManager orderManager = new OrderManager(getLocation());
				Order order = new Order();
				for(int i = 0; i<orderItemTableModel.getRowCount(); i++) {
					int id = Integer.valueOf(orderItemTableModel.getValueAt(i, 0).toString());
					String productName = orderItemTableModel.getValueAt(i,1).toString();
					double productPrice = Double.valueOf(orderItemTableModel.getValueAt(i,2).toString());
					Product Product = new Product(id, productName, productPrice);
					int quantity = Integer.valueOf(orderItemTableModel.getValueAt(i,3).toString());
					OrderItemSize orderItemSize = getOrderItemSize(orderItemTableModel.getValueAt(i,4).toString());
					OrderItem orderItem = new OrderItem(Product, quantity, orderItemSize);
					order.getOrderItems().add(orderItem);
				}
				orderManager.getOrders().add(order);
				
				invoiceLabel.setText(orderManager.calculateAndPrintOrderDetails(restaurant, client, order));
			}
		});
		return order;
	}

	private OrderItemSize getOrderItemSize(String string) {
		switch (string) {
		case "SMALL": 
			return OrderItemSize.SMALL;
		case "MEDIUM": 
			return OrderItemSize.MEDIUM;
		case "LARGE": 
			return OrderItemSize.LARGE;
		case "XXL": 
			return OrderItemSize.XXL;
		default:
			throw new IllegalArgumentException("Unexpected value: " + string);
		}
	}
	
	private Location getLocation() {
		if(kosovoRadioButton.isSelected()) {
			return Location.KOSOVO;
		}
		else {
			return Location.GERMANY;
		}
	}
	private boolean isQuantityValid() {
		if(quantityTextField.getText().isBlank()) {
			JOptionPane.showMessageDialog(frame, "Please provide the quantity.");
			return false;
		}
		else {
			return true;
		}
	}
	
	private JPanel createIncoicePanel() {
		JPanel invoicePanel = new JPanel();
		invoicePanel.setLayout(null);
		invoicePanel.setBounds(830,10,300,350);
		invoicePanel.add(createInvoisScrollPane());
		return invoicePanel;
	}
	
	private JScrollPane createInvoisScrollPane() {
		invoiceLabel = new JLabel("The invoice will be printed here.");
		JScrollPane labelScrollPane = new JScrollPane(invoiceLabel);
		invoiceLabel.setBounds(20,40,250,280);
		labelScrollPane.setBounds(10,20,280,320);
		return labelScrollPane;
	}
	
}