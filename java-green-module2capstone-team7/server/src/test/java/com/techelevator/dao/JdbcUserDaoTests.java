package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }
@Test
    public void findIdByUsername_returns_correct_id(){
    boolean userCreated = sut.create("jacob","test_password");
    Assert.assertTrue(userCreated);
    int id = sut.findIdByUsername("jacob");
    Assert.assertEquals(1004, id);
}
@Test
    public void findAllUsers_returns_all_users() {
    List<UserDTO> users = sut.findAllUsers();
    Assert.assertEquals(2, users.size());
}

    @Test
    public void findByUsername_returns_correct_user(){
        boolean userCreated = sut.create("jakey","test");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("jakey");

        Assert.assertEquals(user, user);
    }

//    public void getParksByState_returns_all_parks_for_state() {
//        List<Park> parks = sut.getParksByState("AA");
//        Assert.assertEquals(2, parks.size());
//        assertParksMatch(PARK_1, parks.get(0));
//        assertParksMatch(PARK_3, parks.get(1));
//
//        parks = sut.getParksByState("BB");
//        Assert.assertEquals(1, parks.size());
//        assertParksMatch(PARK_2, parks.get(0));
//    }

}
