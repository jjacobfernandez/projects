package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class JdbcAccountDao implements AccountDao
{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Account getAccountById(int accountId)
    {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ?;";
            SqlRowSet accountResult = jdbcTemplate.queryForRowSet(sql, accountId);

            if (accountResult.next())
            {
                account = mapRowToAccount(accountResult);
            }
        return account;
    }



    @Override
    public AccountDTO getBalanceByUserId(int userId) {
        AccountDTO account = null;
        String sql ="SELECT balance, username " +
                    "FROM account " +
                    "JOIN tenmo_user ON tenmo_user.user_id = account.user_id " +
                    "WHERE tenmo_user.user_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);

        if (result.next())
        {
            account = mapRowToAccount2(result);
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        if (result.next()){
            accounts.add(mapRowToAccount(result));
        }
        return accounts;
    }

    private Account mapRowToAccount(SqlRowSet accountResult)
    {
        Account account = new Account();
        account.setAccountId(accountResult.getInt("account_id"));
        account.setUserId(accountResult.getInt("user_id"));
        account.setBalance(accountResult.getDouble("balance"));
        return account;
    }


    private AccountDTO mapRowToAccount2(SqlRowSet accountResult)
    {
        AccountDTO account = new AccountDTO();
        account.setUsername(accountResult.getString("username"));
        account.setBalance(accountResult.getDouble("balance"));
        return account;
    }


}
