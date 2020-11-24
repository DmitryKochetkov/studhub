package com.studhub.controller.api;

import com.studhub.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Transactional
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRelationsApiControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    public void testGetFollowers() throws Exception {
//        List<UserDto> expectedContent = userService.getById(3L).getFollowers().stream().map(UserDto::new).collect(Collectors.toList());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/2/followers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.content").value(hasSize(10)))
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.hasPrevious").value(false))
                .andExpect(jsonPath("$.totalPages").value(2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/2/followers?page=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$.number").value(2))
                .andExpect(jsonPath("$.content").value(hasSize(2)))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(true))
                .andExpect(jsonPath("$.totalPages").value(2));
    }

    @Test
    public void testGetFollowing() throws Exception {
        //List<UserDto> expectedContent = userService.getById(3L).getFollowing().stream().map(UserDto::new).collect(Collectors.toList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/3/following").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$.number").value(1))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false))
                .andExpect(jsonPath("$.totalPages").value(1));
    }
}
