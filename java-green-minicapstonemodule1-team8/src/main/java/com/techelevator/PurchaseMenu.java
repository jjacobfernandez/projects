package com.techelevator;
import java.math.BigDecimal;

public class PurchaseMenu extends VendingMachineCLI
{
    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
    private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
    private BigDecimal currentMoney = BigDecimal.valueOf(0.00);
    private int numberOfItemsSold = 0;
    private Transaction transactionLog = new Transaction();

    public PurchaseMenu()
    {

    }

    public BigDecimal getCurrentMoney()
    {
        return currentMoney;
    }

    public void runPurchaseMenu(Inventory inventory)
    {
        while (true)
        {
            System.out.print("Current Money Provided: $");
            System.out.printf("%.2f\n", currentMoney);
            System.out.println();
            String choice = getPurchaseMenuChoice();

            if (choice.equals(PURCHASE_MENU_OPTION_FEED_MONEY))
            {
                handleFeedMoney();
            }
            else if (choice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT))
            {
                //Display available items
                inventory.displayInventory();
                System.out.println("Enter product code to select item: ");
                String itemCodeInput = userInput.nextLine();

                dispensingAnItem(itemCodeInput, inventory);
            }
            else if (choice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION))
            {
                finishingTransaction();

                //Returns to main menu
                break;
            }
            else
            {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleFeedMoney()
    {
        System.out.println("How much money would you like to add to your balance?");
        String amountToAddInput = userInput.nextLine();
        double moneyAdded = Double.parseDouble(amountToAddInput); //Convert input String to double
        BigDecimal amountToAdd = BigDecimal.valueOf(moneyAdded);  //Convert double to BigDecimal

        feedMoney(amountToAdd);
    }

    public void feedMoney(BigDecimal amountToAdd)
    {
        //Adds given money to current amount
        currentMoney = currentMoney.add(amountToAdd);
        transactionLog.setBalance(currentMoney);
        transactionLog.deposit(amountToAdd);
    }

    private String getPurchaseMenuChoice()
    {
        //Prints out options in purchase array
        for (int i = 0; i < PURCHASE_MENU_OPTIONS.length; i++)
        {
            System.out.println("(" + (i + 1) + ") " + PURCHASE_MENU_OPTIONS[i]);
        }

        //What user puts in converted to int
        String choice = userInput.nextLine();
        int choiceNumber = Integer.parseInt(choice);

        //Verify choice number is within array bounds
        if (choiceNumber > PURCHASE_MENU_OPTIONS.length)
        {
            return "";
        }

        return PURCHASE_MENU_OPTIONS[choiceNumber - 1];
    }

    public void dispensingAnItem(String itemCodeInput, Inventory inventory)
    {
        if (inventory.products.containsKey(itemCodeInput))
        {
            Product currentProduct = inventory.products.get(itemCodeInput);
            if (currentProduct.getStock() > 0)
            {
                numberOfItemsSold++;
                BigDecimal price = getProductPrice(currentProduct);

                currentMoney = currentMoney.subtract(price);
                transactionLog.setBalance(currentMoney);
                transactionLog.purchase(currentProduct, price);
                System.out.println("Item: " + currentProduct.getName()
                        + "\tPrice: $" + price
                        + "\tMoney Remaining: $" + currentMoney);

                System.out.println(currentProduct.dispensingMessage());

                //Update stock
                currentProduct.setStock(currentProduct.getStock() - 1);
            }
            else
            {
                System.out.println("SOLD OUT. Please select different option.");
            }
        }
        else
        {
            System.out.println("Invalid product code. Please try again.");
        }

    }

    public BigDecimal getProductPrice(Product product)
    {
        BigDecimal price = product.getPrice();
        if (numberOfItemsSold % 2 == 0)
        {
            price = price.subtract(BigDecimal.valueOf(1.00));
        }

        return price;
    }

    public void finishingTransaction()
    {
        //Return change
        int[] coins = calculateChange(currentMoney);
        System.out.println("Change Returned: ");
        System.out.println("Quarters: " + coins[0]);
        System.out.println("Dimes: " + coins[1]);
        System.out.println("Nickels: " + coins[2]);
        System.out.println();

        //Update current balance to 0
        BigDecimal originalBalance = BigDecimal.valueOf(currentMoney.doubleValue());
        currentMoney = BigDecimal.valueOf(0.00);
        transactionLog.setBalance(currentMoney);
        transactionLog.withdraw(originalBalance);

        //Update number of items sold to 0
        numberOfItemsSold = 0;
    }

    private int[] calculateChange(BigDecimal amount)
    {
        int[] coins = new int[3]; //Index 0 for quarters, 1 for dimes, 2 for nickels
        BigDecimal quarterValue = new BigDecimal("0.25");
        BigDecimal dimeValue = new BigDecimal("0.10");
        BigDecimal nickelValue = new BigDecimal("0.05");

        //Convert the BigDecimal amount to the smallest unit (cents)
        BigDecimal remainingCents = BigDecimal.valueOf(amount.doubleValue());

        //Calculate the number of quarters
        coins[0] = remainingCents.divideToIntegralValue(quarterValue).intValue();
        remainingCents = remainingCents.subtract(quarterValue.multiply(new BigDecimal(coins[0])));

        //Calculate the number of dimes
        coins[1] = remainingCents.divideToIntegralValue(dimeValue).intValue();
        remainingCents = remainingCents.subtract(dimeValue.multiply(new BigDecimal(coins[1])));

        //Calculate the number of nickels
        coins[2] = remainingCents.divideToIntegralValue(nickelValue).intValue();

        return coins;
    }
}
