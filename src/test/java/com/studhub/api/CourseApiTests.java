package com.studhub.api;

import com.studhub.dto.CourseDto;
import com.studhub.entity.Course;
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
public class CourseApiTests {
    @Autowired
    private MockMvc mockMvc;

    @Before
    @Test
    public void contextLoads() {
        assertThat(mockMvc).isNotNull();
    }

    //test for /api/course/{courseId}
    @Test
    public void testGetById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/course/1"))
                .andExpect(jsonPath("$[*]", hasSize(8)))
                .andExpect(jsonPath("$.id").value(1))
                //TODO: check created and lastModified
                .andExpect(jsonPath("$.studentId").value("2"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.comingLessons").value(emptyCollectionOf(CourseDto.class)))
                //TODO: check password is encrypted
                //TODO: check order
                .andReturn();

        //TODO: test /api/user/2 via comparing with JSON string

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/3"))
                .andExpect(status().isNotFound());
    }
}
