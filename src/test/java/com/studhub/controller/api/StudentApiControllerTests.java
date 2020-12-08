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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
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
public class StudentApiControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    @Test
    public void testGetStudentById200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(9)))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.lastModified").exists())
                .andExpect(jsonPath("$.firstName").value("Petr"))
                .andExpect(jsonPath("$.lastName").value("Petrov"))
                .andExpect(jsonPath("$.status").value("ENABLED"))
                .andExpect(jsonPath("$.username").value("petr"))
                .andExpect(jsonPath("$.roles").exists());
    }

    @Test
    public void testGetStudentById400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/string"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.detail").value("Bad Request"))
                .andReturn();
    }

    @Test
    public void testGetStudentById404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.detail").value("Not Found"))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/4"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.detail").value("Not Found"))
                .andReturn();
    }

    @Test
    public void testGetCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/2/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(9)))
                .andExpect(jsonPath("$.studentId").value(2))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllHomeworkInCourse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/2/course/1/homework"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(5)))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    public void testGetHomeworkById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/2/course/1/homework/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(10)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.lastModified").exists())
                .andExpect(jsonPath("$.deadline").exists())
                .andExpect(jsonPath("$.remainingSeconds").exists())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.lessonId").value(1))
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.totalProblemsCount").exists())
                .andExpect(jsonPath("$.solvedProblemsCount").exists());
    }

}
