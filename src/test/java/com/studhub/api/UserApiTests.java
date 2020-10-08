package com.studhub.api;

import com.studhub.StudhubApplication;
import com.studhub.entity.User;
import com.studhub.entity.UserStatus;
import com.studhub.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StudhubApplication.class)
@AutoConfigureMockMvc
public class UserApiTests {
    @Autowired
    private MockMvc mockMvc;

    private UserService userServiceMock = Mockito.mock(UserService.class);

    User testUser;

    @Before
    @Test
    public void contextLoads() {
        assertThat(userServiceMock).isNotNull();
        assertThat(mockMvc).isNotNull();
    }

    @Before
    public void setUp() throws Exception {
        testUser = new User();
        testUser.setId(1);
        testUser.setCreated(new Date());
//        testUser.setRoles();
        testUser.setStatus(UserStatus.ENABLED);
        testUser.setFirstName("Ivan");
        testUser.setLastName("Ivanov");
        testUser.setFollowing(new ArrayList<>());
        testUser.setFollowers(new ArrayList<>());
        testUser.setPassword("123");

        when(userServiceMock.getById(1L)).thenReturn(testUser);
    }

    //test for /api/user/{userId}
    @Test
    public void testGetById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
