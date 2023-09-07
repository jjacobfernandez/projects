package com.techelevator;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseMenuTest
{
    @Test
    public void feedMoney_updates_balance_to_amount_given()
    {
        PurchaseMenu myPurchaseMenu = new PurchaseMenu();
        myPurchaseMenu.feedMoney(BigDecimal.valueOf(5.00));
        Assert.assertEquals(5, myPurchaseMenu.getCurrentMoney().intValue());
    }

    @Test
    public void dispensingAnItem_decreases_balance_by_item_price()
    {
        PurchaseMenu myPurchaseMenu = new PurchaseMenu();
        Inventory myInventory = new Inventory();
        myPurchaseMenu.feedMoney(BigDecimal.valueOf(5.00));
        myPurchaseMenu.dispensingAnItem("C1", myInventory);
        Assert.assertEquals(2.75, myPurchaseMenu.getCurrentMoney().doubleValue(), 0.00);
    }

    @Test
    public void dispensingAnItem_updates_inventory_stock_after_purchase()
    {
        PurchaseMenu myPurchaseMenu = new PurchaseMenu();
        Inventory myInventory = new Inventory();
        myPurchaseMenu.feedMoney(BigDecimal.valueOf(4.00));
        myPurchaseMenu.dispensingAnItem("A4", myInventory);
        Assert.assertEquals(4, myInventory.products.get("A4").getStock());
    }

    @Test
    public void getProductPrice_adjusts_purchase_price_correctly_for_sale()
    {
        PurchaseMenu myPurchaseMenu = new PurchaseMenu();
        Inventory myInventory = new Inventory();
        myPurchaseMenu.feedMoney(BigDecimal.valueOf(6.00));
        myPurchaseMenu.dispensingAnItem("A3", myInventory);
        myPurchaseMenu.dispensingAnItem("C4", myInventory);
        Assert.assertEquals(0.75, myPurchaseMenu.getProductPrice(myInventory.products.get("C4")).doubleValue(), 0.00);
    }

    @Test
    public void finishingTransaction_updates_balance_to_zero()
    {
        PurchaseMenu myPurchaseMenu = new PurchaseMenu();
        myPurchaseMenu.feedMoney(BigDecimal.valueOf(3.00));
        myPurchaseMenu.finishingTransaction();
        Assert.assertEquals(0.00, myPurchaseMenu.getCurrentMoney().doubleValue(), 0.00);
    }
}