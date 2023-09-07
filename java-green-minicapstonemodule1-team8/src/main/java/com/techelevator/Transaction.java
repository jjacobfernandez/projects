package com.techelevator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction
{
    private BigDecimal balance = BigDecimal.valueOf(0.00);

    public void deposit(BigDecimal depositAmount)
    {
        commitChangeToLog("FEED MONEY:", depositAmount);
    }

    public void purchase(Product product, BigDecimal purchasePrice)
    {
        String description = product.getName() + " " + product.getSlotIdentifier();
        commitChangeToLog(description, purchasePrice);
    }

    public void withdraw(BigDecimal withdrawAmount)
    {
        commitChangeToLog("GIVE CHANGE:", withdrawAmount);
    }

    public BigDecimal getBalance()
    {
        return balance;
    }

    public void setBalance(BigDecimal value)
    {
        this.balance = value;
    }

    private void writeToLog(String action)
    {
        File purchaseLog = new File("log.txt");
        try
        {
            FileWriter writer = new FileWriter(purchaseLog, true);
            writer.write(action + "\n");
            writer.close();
        }
        catch (IOException e)
        {
            System.out.println("File not found");
        }
    }

    private void commitChangeToLog(String description, BigDecimal amountToChange)
    {
        String transactionAmount = formatCurrency(amountToChange);
        //BigDecimal updatedBalance = balance.add(amountToChange);
        String newBalance = formatCurrency(balance);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String currentDate = dateFormat.format(new Date());
        String completedLog = currentDate + " " + description + " " +
                transactionAmount + " " + newBalance;
        writeToLog(completedLog);
    }

    private String formatCurrency(BigDecimal value)
    {
        //Automatically format currency in log
        return NumberFormat.getCurrencyInstance().format(value);
    }
}