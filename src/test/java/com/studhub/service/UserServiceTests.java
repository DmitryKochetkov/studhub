package com.studhub.service;

import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import com.studhub.payload.SignupRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(userService);
        Assert.assertNotNull(passwordEncoder);
    }

    @Test
    public void testRegistrationSuccessful() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("Testov");
        signupRequest.setRole("ROLE_USER");
        signupRequest.setUsername("test_user");
        signupRequest.setPassword("123");

        User user = userService.register(signupRequest);
        Assert.assertNotNull(user);
        Assert.assertEquals("Test", user.getFirstName());
        Assert.assertEquals("Testov", user.getLastName());
        Assert.assertEquals("test_user", user.getUsername());
        Assert.assertTrue(passwordEncoder.matches("123", user.getPassword()));
        Assert.assertEquals(UserStatus.ENABLED, user.getStatus());
        Assert.assertNotNull(user.getFollowing());
        Assert.assertNotNull(user.getFollowers());
    }

    @Test
    public void testRegistrationFailed() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setFirstName("Test");
        signupRequest.setLastName("Testov");
        signupRequest.setRole("ROLE_USER");
        signupRequest.setUsername(null);
        signupRequest.setPassword("123");

        User user = userService.register(signupRequest);
        Assert.assertNull(user);

        signupRequest.setUsername("");
        user = userService.register(signupRequest);
        Assert.assertNull(user);
    }

    @Test
    public void testGetById() {
        User user = userService.getById(1L);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId().longValue());
        Assert.assertEquals("admin", user.getUsername());
        Assert.assertEquals("Ivan", user.getFirstName());
        Assert.assertEquals("Ivanov", user.getLastName());
        Assert.assertEquals(UserStatus.ENABLED, user.getStatus());
        Assert.assertNotNull(user.getFollowing());
        Assert.assertNotNull(user.getFollowers());

        for (long i = 2; i < 14; i++) {
            user = userService.getById(i);
            Assert.assertNotNull(user);
        }
    }
}
