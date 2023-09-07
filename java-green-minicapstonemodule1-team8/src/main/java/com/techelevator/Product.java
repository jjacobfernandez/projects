package com.techelevator;
import java.math.BigDecimal;

public class Product
{
    private String name;
    private String slotIdentifier;
    private BigDecimal price;
    private String productType;
    private int stock;

    public Product(String slotIdentifier, String name, BigDecimal price, String productType, int stock)
    {
        this.slotIdentifier = slotIdentifier;
        this.name = name;
        this.price = price;
        this.productType = productType;
        this.stock = stock;
    }

    public String getSlotIdentifier()
    {
        return slotIdentifier;
    }

    public String getName()
    {
        return name;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public String getProductType()
    {
        return productType;
    }

    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public String dispensingMessage()
    {
        switch (productType)
        {
            case "Munchy":
                return "Crunch Crunch, Yum!";

            case "Candy":
                return "Yummy Yummy, So Sweet!";

            case "Drink":
                return "Glug Glug, Yum!";

            case "Gum":
                return "Chew Chew, Yum!";

            default:
                return "";
        }
    }
}
