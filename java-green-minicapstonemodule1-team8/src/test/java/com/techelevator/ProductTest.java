package com.techelevator;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest
{
    @Test
    public void dispensingMessage_returns_correct_message_for_dispensed_item()
    {
        Product myProduct = new Product("A4", "Papsi", BigDecimal.valueOf(1.25), "Drink", 5);
        Assert.assertEquals("Glug Glug, Yum!", myProduct.dispensingMessage());
    }
}