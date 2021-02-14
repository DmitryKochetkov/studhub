package com.studhub.controller.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестирование эндпоинта API /api/student/{studentId}
*/
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/before-each-test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
public class SubmissionApiControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testGetSubmissionsFromHomework() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/course/1/homework/1/submissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].answer").exists())
                .andExpect(jsonPath("$.content[0].verdict").exists())
                .andExpect(jsonPath("$.content[0].homeworkId").exists())
                .andExpect(jsonPath("$.content[0].problemId").exists());
    }

//    @Test
//    public void testPostSubmitProblemFromHomework() throws Exception {
//        JSONObject requestBody = new JSONObject();
//
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                    .post("/api/course/1/homework/1/problem/1")
//                    .contentType(MediaType.APPLICATION_JSON_VALUE)
//        )
//                .andExpect(status().isCreated());
//    }

}
