package com.studhub.front;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.studhub.StudhubApplicationTests.TEXT_HTML_UTF8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UsersPageTests {
    @Autowired
    MockMvc mockMvc;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void usersPagesLoad() throws Exception {
        String url = "/users?page=";
        mockMvc.perform(MockMvcRequestBuilders.get(url + 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8));

        mockMvc.perform(MockMvcRequestBuilders.get(url + 2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_HTML_UTF8));

        mockMvc.perform(MockMvcRequestBuilders.get(url + 3))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(TEXT_HTML_UTF8));

        mockMvc.perform(MockMvcRequestBuilders.get(url + "string"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TEXT_HTML_UTF8));

        mockMvc.perform(MockMvcRequestBuilders.get(url + 0))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TEXT_HTML_UTF8));

        mockMvc.perform(MockMvcRequestBuilders.get(url + "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(TEXT_HTML_UTF8));
    }
}
