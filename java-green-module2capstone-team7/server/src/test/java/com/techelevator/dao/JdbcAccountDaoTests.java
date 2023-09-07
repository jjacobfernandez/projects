package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDaoTests extends BaseDaoTests{

private static final Account ACCOUNT_1 = new Account(100, 1, 1);
private static final Account Account2 = new Account(1000, 2, 2);
private static final Account Account3 = new Account(200, 3, 3);
private static final AccountDTO ACCOUNT_DTO = new AccountDTO("user", 100);
private JdbcAccountDao sut;
private Account testAccount;
    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
        testAccount = new Account(0, 0, 0);
    }

@Test
    public void getAccountById_returns_correct_account(){
        Account retrievedAccount = sut.getAccountById(1);
        assertAccountsMatch(ACCOUNT_1, retrievedAccount);
}
@Test public void getAccountById_returns_null_when_id_not_found(){
        Account account = sut.getAccountById(99);
        Assert.assertNull(account);
}

private void assertAccountsMatch(Account expected, Account actual){
        Assert.assertEquals(expected.getBalance(), actual.getBalance());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
}

}
