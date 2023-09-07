package com.techelevator.tenmo.model;

public class Account
{
    private double balance;
    private int userId;
    private int accountId;

    //Constructor
    public Account(double balance, int userId, int accountId)
    {
        this.balance = balance;
        this.userId = userId;
        this.accountId = accountId;
    }
    public Account(){}

    //Getters
    public double getBalance()
    {
        return balance;
    }
    public int getUserId()
    {
        return userId;
    }
    public int getAccountId()
    {
        return accountId;
    }



    //Setters
    public void setBalance(double balance)
    {
        this.balance = balance;
    }
    public void setUserId(int userId)
    {
        this.userId = userId;
    }
    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", userId=" + userId +
                ", accountId=" + accountId +
                '}';
    }
}
