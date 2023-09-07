package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping ("/account")
public class AccountController
{
    private AccountDao accountDao;

    public AccountController(AccountDao accountDao)
    {
        this.accountDao = accountDao;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path="/{id}", method=RequestMethod.GET)
    public Account showMyAccount(@PathVariable int id)
    {
        return accountDao.getAccountById(id);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
    public AccountDTO getBalance(@PathVariable int userId)
    {
        return accountDao.getBalanceByUserId(userId);
    }
}
