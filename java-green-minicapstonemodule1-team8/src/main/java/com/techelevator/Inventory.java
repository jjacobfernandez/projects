package com.techelevator;
import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Inventory
{
    Map<String, Product> products = new HashMap<>();
    private static final int STARTING_STOCK = 5;

    public Inventory()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("main.csv"));

            String currentLine;
            while ((currentLine = reader.readLine()) != null)
            {
                String[] item = currentLine.split(",");
                //Make sure line has appropriate amount of items
                if (item.length != 4)
                {
                    continue;
                }
                //Make sure price is numeric
                if (!isNumeric(item[2]))
                {
                    continue;
                }

                String slotLocation = item[0];
                String productName = item[1];
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(item[2]));
                String productType = item[3];
                Product currentProduct = new Product(slotLocation, productName, price, productType, STARTING_STOCK);
                products.put(slotLocation, currentProduct);
            }
            reader.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found.");
        }
    }

    public void displayInventory()
    {
        for (Map.Entry<String, Product> currentProduct : products.entrySet())
        {
            System.out.print("Slot: " + currentProduct.getValue().getSlotIdentifier()
                    + "\tItem: " + currentProduct.getValue().getName()
                    + "\tPrice: $" + currentProduct.getValue().getPrice()
                    + "\tQuantity Remaining: ");

            if (currentProduct.getValue().getStock() == 0)
            {
                System.out.println("SOLD OUT");
            }
            else
            {
                System.out.println(currentProduct.getValue().getStock());
            }
        }
        System.out.println();
    }

    private boolean isNumeric(String value)
    {
        try
        {
            double number = Double.parseDouble(value);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
