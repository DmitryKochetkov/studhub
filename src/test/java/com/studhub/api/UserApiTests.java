package com.studhub.api;

import com.studhub.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    //test for /api/user/{userId}
    @Test
    public void testGetById200() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1"))
                .andExpect(jsonPath("$[*]", hasSize(11)))
                .andExpect(jsonPath("$.id").value(1))
                //TODO: check created and lastModified
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.status").value("ENABLED"))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.followers").value(emptyCollectionOf(UserDto.class)))
                .andExpect(jsonPath("$.following").value(emptyCollectionOf(UserDto.class)))
                //TODO: check password is encrypted
                //TODO: check order
                .andReturn();

        //TODO: test /api/user/2 via comparing with JSON string

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetById400() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/user/string"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.detail").value("Bad Request"))
                .andReturn();
    }

    @Test
    public void testGetById404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.detail").value("Not Found"))
                .andReturn();
    }
}
