import java.util.ArrayList;
import java.util.Scanner;

public class ComputerMaintApp {
	private static ComputerDAO cDAO = null;
	private static Scanner console = null;
	
	public static void main(String[] args) {
		System.out.println("Welcome to the computer maintenance application\n");
		
		cDAO = DAOFactory.getComputerDAO();
		console = new Scanner(System.in);
		
		displayMenu();
		
		String command = "";
		while (!command.equalsIgnoreCase("exit")) {
			command = Validator.getString(console, "Enter a command: ");
			System.out.println();
			if (command.equalsIgnoreCase("list")) {
				displayAllComputers();
			} else if (command.equalsIgnoreCase("add")) {
				addComputer();
			} else if (command.equalsIgnoreCase("del") || command.equalsIgnoreCase("delete")) {
				deleteComputer();
			} else if (command.equalsIgnoreCase("help") || command.equalsIgnoreCase("menu")) {
				displayMenu();
			} else if (command.equalsIgnoreCase("exit")) {
				System.out.println("Bye. \n");
			} else {
				System.out.println("Error. Invalid command. Supported commands are:");
				displayMenu();
			}
		}
	}
	
	public static void displayMenu() {
		System.out.println("COMMAND MENU");
		System.out.println("list    - List all computers");
		System.out.println("add     - Add a computer");
		System.out.println("del     - Delete a computer");
		System.out.println("help    - Show this menu");
		System.out.println("exit    - Exit this application\n");
	}
	
	//displays a limited version of the computers in the "database", because the computers 
	//object has a lot of fields, and that would be a lot of typing
	public static void displayAllComputers() {
		System.out.println("COMPUTER LIST");
		ArrayList<Computer> computers = cDAO.getComputers();
		if (computers == null) {
			System.out.println("Error: Unable to get computers \n");
		} else {
			Computer c = null;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < computers.size(); i++) {
				c = computers.get(i);
				sb.append(StringUtils.padWithSpaces(c.getName(), 10) + 
						  c.getFormattedPrice() + "           " + StringUtils.padWithSpaces(Integer.toString(c.getHDSize()), 10)
						  + StringUtils.padWithSpaces(Integer.toString(c.getRAM()), 10)
						  + "\n");
			}
			System.out.println(sb.toString());
		}
	}
	
	public static void addComputer() {
		String name = Validator.getString(
			console, "Enter computer name: ");
		double price = Validator.getDouble(
			console, "Enter price: ");
		int size = Validator.getInt(console, "Enter HD Size: ");
		int RAM = Validator.getInt(console, "Enter RAM: ");
		double screenSize = Validator.getDouble(console, "Enter screen size: ");
		int screenRes = Validator.getInt(console, "Enter screen resolution: ");
		int hasTouchScreen = Validator.getInt(console, "Enter 1 if computer has a touchscreen, 0 if it does not");
		
		Computer c = new Computer(price, size, RAM, name, screenSize, screenRes, hasTouchScreen);
		boolean success = cDAO.addComputer(c);
		System.out.println();
		if (success) {
			System.out.println(name + " was added to the database\n");
		} else {
			System.out.println("Error: Unable to add computer\n");
		}
	}
	
	public static void deleteComputer() {
		String name = Validator.getString(console, "Enter computer name to delete: ");
		Computer c = cDAO.getComputer(name);
		
		System.out.println();
		
		if (c != null) {
			boolean success = cDAO.deleteComputer(c);
			if (success) {
				System.out.println(c.getName() + " was deleted from the database\n");
			} else {
				System.out.println("Error: Unable to delete computer");
			}
		} else {
			System.out.println("No computer with that name in the database");
		}
	}
}