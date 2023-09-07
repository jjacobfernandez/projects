package com.techelevator;
import java.util.Scanner;

/*
 * This class is provided to you as a *suggested* class to start
 * your project. Feel free to refactor this code as you see fit.
 */
public class VendingMachineCLI
{
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	static final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args)
	{
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();
	}

	public void run()
	{
		Inventory inventory = new Inventory();  //Contains products info
		PurchaseMenu purchaseMenu = new PurchaseMenu();  //Contains purchase menu info

		while (true)
		{
			String choice = getMainMenuChoice();

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS))
			{
				//Display items in vending machine
				inventory.displayInventory();

			}
			else if (choice.equals(MAIN_MENU_OPTION_PURCHASE))
			{
				//Runs through purchase menu
				purchaseMenu.runPurchaseMenu(inventory);

			}
			else if (choice.equals(MAIN_MENU_OPTION_EXIT))
			{
				System.exit(1);
			}
			else
			{
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}

	private String getMainMenuChoice()
	{
		System.out.println("Welcome, please select the number that corresponds with your desired action: ");

		//Print out options in menu array
		for (int i = 0; i < MAIN_MENU_OPTIONS.length; i++)
		{
			System.out.println("(" + (i + 1) + ") " + MAIN_MENU_OPTIONS[i]);
		}

		//What user puts in converted to int
		String choice = userInput.nextLine();
		int choiceNumber = Integer.parseInt(choice);

		//Verify choice number is within array bounds
		if (choiceNumber > MAIN_MENU_OPTIONS.length)
		{
			return "";
		}

		return MAIN_MENU_OPTIONS[choiceNumber - 1];
	}
}
