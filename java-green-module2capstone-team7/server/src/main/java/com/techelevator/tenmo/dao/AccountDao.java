package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;

import java.util.List;

public interface AccountDao
{
    Account getAccountById(int accountId);

    AccountDTO getBalanceByUserId(int userId);

    List<Account> getAccounts();
}

