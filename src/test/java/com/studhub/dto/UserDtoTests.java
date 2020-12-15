package com.studhub.dto;

import com.studhub.entity.User;
import com.studhub.exception.NotFoundException;
import com.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@WebAppConfiguration
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserDtoTests {
    @Autowired
    private UserService userService;

    @Before
    @Test
    public void contextLoads() {
        Assert.assertNotNull(userService);
    }

    @Test
    @Transactional
    public void testUserDtoConstructor() {
        User user = userService.getById(2L).orElseThrow(NotFoundException::new);
        UserDto userDto = new UserDto(user);
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getStatus(), userDto.getStatus());
        Assert.assertEquals(user.getUsername(), userDto.getUsername());
    }
}
