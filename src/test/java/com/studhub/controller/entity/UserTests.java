package com.studhub.controller.entity;

import com.studhub.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTests {

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setUsername("just_a_username");
    }

    @Test
    public void testToString() {
        Assert.assertEquals("User{username='just_a_username'}", user.toString());
    }
}
