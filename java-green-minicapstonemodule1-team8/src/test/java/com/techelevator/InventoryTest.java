package com.techelevator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest
{
    @Test
    public void inventory_contains_correct_amount_of_products()
    {
        Inventory myInventory = new Inventory();
        Assert.assertEquals(16, myInventory.products.size());
    }
}